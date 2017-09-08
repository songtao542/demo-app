package com.aperise.mapper.gen;

import com.aperise.bean.Category;
import com.aperise.bean.CategoryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenCategoryMapper {
    long countByExample(CategoryCriteria example);

    int deleteByExample(CategoryCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExampleWithRowbounds(CategoryCriteria example, RowBounds rowBounds);

    List<Category> selectByExample(CategoryCriteria example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryCriteria example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryCriteria example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}