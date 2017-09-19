package com.aperise.mapper.gen;

import com.aperise.bean.AclResource;
import com.aperise.bean.AclResourceCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenAclResourceMapper {
    long countByCriteria(AclResourceCriteria example);

    int deleteByCriteria(AclResourceCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(AclResource record);

    int insertSelective(AclResource record);

    List<AclResource> selectByCriteriaWithRowbounds(AclResourceCriteria example, RowBounds rowBounds);

    List<AclResource> selectByCriteria(AclResourceCriteria example);

    AclResource selectByPrimaryKey(Long id);

    int updateByCriteriaSelective(@Param("record") AclResource record, @Param("example") AclResourceCriteria example);

    int updateByCriteria(@Param("record") AclResource record, @Param("example") AclResourceCriteria example);

    int updateByPrimaryKeySelective(AclResource record);

    int updateByPrimaryKey(AclResource record);
}