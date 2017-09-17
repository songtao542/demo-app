package com.aperise.mapper.gen;

import com.aperise.bean.Product;
import com.aperise.bean.ProductCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenProductMapper {
    long countByCriteria(ProductCriteria example);

    int deleteByCriteria(ProductCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByCriteriaWithRowbounds(ProductCriteria example, RowBounds rowBounds);

    List<Product> selectByCriteria(ProductCriteria example);

    Product selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") Product record, @Param("example") ProductCriteria example);

    int updateByCriteria(@Param("record") Product record, @Param("example") ProductCriteria example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}
