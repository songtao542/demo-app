package com.aperise.mapper.gen;

import com.aperise.bean.Merchant;
import com.aperise.bean.MerchantCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenMerchantMapper {
    long countByExample(MerchantCriteria example);

    int deleteByExample(MerchantCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    List<Merchant> selectByExampleWithRowbounds(MerchantCriteria example, RowBounds rowBounds);

    List<Merchant> selectByExample(MerchantCriteria example);

    Merchant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Merchant record, @Param("example") MerchantCriteria example);

    int updateByExample(@Param("record") Merchant record, @Param("example") MerchantCriteria example);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
}