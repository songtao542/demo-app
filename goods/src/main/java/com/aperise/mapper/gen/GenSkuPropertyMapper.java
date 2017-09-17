package com.aperise.mapper.gen;

import com.aperise.bean.SkuProperty;
import com.aperise.bean.SkuPropertyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenSkuPropertyMapper {
    long countByCriteria(SkuPropertyCriteria example);

    int deleteByCriteria(SkuPropertyCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(SkuProperty record);

    int insertSelective(SkuProperty record);

    List<SkuProperty> selectByCriteriaWithRowbounds(SkuPropertyCriteria example, RowBounds rowBounds);

    List<SkuProperty> selectByCriteria(SkuPropertyCriteria example);

    SkuProperty selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") SkuProperty record, @Param("example") SkuPropertyCriteria example);

    int updateByCriteria(@Param("record") SkuProperty record, @Param("example") SkuPropertyCriteria example);

    int updateByPrimaryKeySelective(SkuProperty record);

    int updateByPrimaryKey(SkuProperty record);
}
