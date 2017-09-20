package com.aperise.acl;

import com.aperise.bean.AclResource;
import org.springframework.cache.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class AclResourceServiceImpl implements AclResourceService {

    private Cache cache;

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private String selectAclResource = "select id,resource,display from acl_resource where resource=?";

    public AclResourceServiceImpl(DataSource dataSource, Cache cache) {
        Assert.notNull(dataSource, "DataSource required");
        Assert.notNull(cache, "Cache required");
        this.dataSource = dataSource;
        this.cache = cache;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AclResource readBySource(final String source) {
        if (cache.get(source) != null && cache.get(source).get() != null) {
            return (AclResource) cache.get(source).get();
        }

        List<AclResource> result = jdbcTemplate.query(selectAclResource,
                (preparedStatement) -> preparedStatement.setString(1, source),
                (resultSet) -> {
                    List<AclResource> ids = new ArrayList<>(); // Set of parent_id Longs
                    while (resultSet.next()) {
                        AclResource resource = new AclResource();
                        Long id = resultSet.getLong("id");
                        String res = resultSet.getString("resource");
                        String display = resultSet.getString("display");
                        resource.setId(id);
                        resource.setResource(res);
                        resource.setDisplay(display);
                        ids.add(resource);
                    }
                    return ids;
                });
        if (result.size() > 0) {
            AclResource aclResource = result.get(0);
            cache.put(source, aclResource);
            return aclResource;
        }
        return null;
    }
}


//        List<Long> result = jdbcTemplate.query(selectAclResource, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setString(1, source);
//            }
//        }, new ResultSetExtractor<List<Long>>() {
//            @Override
//            public List<Long> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//                List<Long> ids = new ArrayList<>(); // Set of parent_id Longs
//                while (resultSet.next()) {
//                    Long id = resultSet.getLong("id");
//                    ids.add(id);
//                }
//                return ids;
//            }
//        });