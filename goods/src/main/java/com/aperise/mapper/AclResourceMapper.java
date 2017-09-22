package com.aperise.mapper;

import com.aperise.bean.AclResource;
import com.aperise.bean.AclResourceCriteria;
import com.aperise.bean.AclResourceGroup;
import com.aperise.mapper.gen.GenAclResourceMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AclResourceMapper extends GenAclResourceMapper {
    AclResource selectByResource(@Param("resource") String resource);

    List<AclResource> selectByType(@Param("type") String type);

    List<AclResourceGroup> selectGroup();

}
