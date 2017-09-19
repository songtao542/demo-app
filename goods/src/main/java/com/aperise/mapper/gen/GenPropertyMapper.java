package com.aperise.mapper.gen;

import com.aperise.bean.Property;
import com.aperise.bean.PropertyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenPropertyMapper {
    long countByCriteria(PropertyCriteria example);

    int deleteByCriteria(PropertyCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByCriteriaWithRowbounds(PropertyCriteria example, RowBounds rowBounds);

    List<Property> selectByCriteria(PropertyCriteria example);

    Property selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") Property record, @Param("example") PropertyCriteria example);

    int updateByCriteria(@Param("record") Property record, @Param("example") PropertyCriteria example);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}