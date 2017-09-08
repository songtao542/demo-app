package com.aperise.mapper.gen;

import com.aperise.bean.PropertyValue;
import com.aperise.bean.PropertyValueCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenPropertyValueMapper {
    long countByExample(PropertyValueCriteria example);

    int deleteByExample(PropertyValueCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExampleWithRowbounds(PropertyValueCriteria example, RowBounds rowBounds);

    List<PropertyValue> selectByExample(PropertyValueCriteria example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PropertyValue record, @Param("example") PropertyValueCriteria example);

    int updateByExample(@Param("record") PropertyValue record, @Param("example") PropertyValueCriteria example);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}