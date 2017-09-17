package com.aperise.mapper.gen;

import com.aperise.bean.Sku;
import com.aperise.bean.SkuCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenSkuMapper {
    long countByCriteria(SkuCriteria example);

    int deleteByCriteria(SkuCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    List<Sku> selectByCriteriaWithRowbounds(SkuCriteria example, RowBounds rowBounds);

    List<Sku> selectByCriteria(SkuCriteria example);

    Sku selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") Sku record, @Param("example") SkuCriteria example);

    int updateByCriteria(@Param("record") Sku record, @Param("example") SkuCriteria example);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
}
