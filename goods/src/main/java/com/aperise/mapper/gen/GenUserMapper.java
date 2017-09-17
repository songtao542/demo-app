package com.aperise.mapper.gen;

import com.aperise.bean.User;
import com.aperise.bean.UserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GenUserMapper {
    long countByCriteria(UserCriteria example);

    int deleteByCriteria(UserCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByCriteriaWithRowbounds(UserCriteria example, RowBounds rowBounds);

    List<User> selectByCriteria(UserCriteria example);

    User selectByPrimaryKey(Integer id);

    int updateByCriteriaSelective(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByCriteria(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
