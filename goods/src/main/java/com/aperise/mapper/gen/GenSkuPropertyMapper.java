package com.aperise.mapper.gen;

import com.aperise.bean.SkuProperty;
import com.aperise.bean.SkuPropertyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenSkuPropertyMapper {
    long countByExample(SkuPropertyCriteria example);

    int deleteByExample(SkuPropertyCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(SkuProperty record);

    int insertSelective(SkuProperty record);

    List<SkuProperty> selectByExampleWithRowbounds(SkuPropertyCriteria example, RowBounds rowBounds);

    List<SkuProperty> selectByExample(SkuPropertyCriteria example);

    SkuProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SkuProperty record, @Param("example") SkuPropertyCriteria example);

    int updateByExample(@Param("record") SkuProperty record, @Param("example") SkuPropertyCriteria example);

    int updateByPrimaryKeySelective(SkuProperty record);

    int updateByPrimaryKey(SkuProperty record);
}