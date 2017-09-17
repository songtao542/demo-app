package com.aperise.mapper.gen;

import com.aperise.bean.Role;
import com.aperise.bean.RoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenRoleMapper {
    long countByCriteria(RoleCriteria example);

    int deleteByCriteria(RoleCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByCriteriaWithRowbounds(RoleCriteria example, RowBounds rowBounds);

    List<Role> selectByCriteria(RoleCriteria example);

    Role selectByPrimaryKey(Long id);

    int updateByCriteriaSelective(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByCriteria(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}
