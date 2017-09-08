package com.aperise.mapper.gen;

import com.aperise.bean.Property;
import com.aperise.bean.PropertyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenPropertyMapper {
    long countByExample(PropertyCriteria example);

    int deleteByExample(PropertyCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExampleWithRowbounds(PropertyCriteria example, RowBounds rowBounds);

    List<Property> selectByExample(PropertyCriteria example);

    Property selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Property record, @Param("example") PropertyCriteria example);

    int updateByExample(@Param("record") Property record, @Param("example") PropertyCriteria example);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}