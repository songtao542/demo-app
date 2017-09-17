package com.aperise.mapper.gen;

import com.aperise.bean.ProductProperty;
import com.aperise.bean.ProductPropertyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenProductPropertyMapper {
    long countByCriteria(ProductPropertyCriteria example);

    int deleteByCriteria(ProductPropertyCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductProperty record);

    int insertSelective(ProductProperty record);

    List<ProductProperty> selectByCriteriaWithRowbounds(ProductPropertyCriteria example, RowBounds rowBounds);

    List<ProductProperty> selectByCriteria(ProductPropertyCriteria example);

    ProductProperty selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") ProductProperty record, @Param("example") ProductPropertyCriteria example);

    int updateByCriteria(@Param("record") ProductProperty record, @Param("example") ProductPropertyCriteria example);

    int updateByPrimaryKeySelective(ProductProperty record);

    int updateByPrimaryKey(ProductProperty record);
}
