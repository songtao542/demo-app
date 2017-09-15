package com.aperise.mapper.gen;

import com.aperise.bean.Merchant;
import com.aperise.bean.MerchantCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenMerchantMapper {
    long countByCriteria(MerchantCriteria example);

    int deleteByCriteria(MerchantCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    List<Merchant> selectByCriteriaWithRowbounds(MerchantCriteria example, RowBounds rowBounds);

    List<Merchant> selectByCriteria(MerchantCriteria example);

    Merchant selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") Merchant record, @Param("example") MerchantCriteria example);

    int updateByCriteria(@Param("record") Merchant record, @Param("example") MerchantCriteria example);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
}