package com.aperise.mapper;

import com.aperise.bean.Property;
import com.aperise.bean.PropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PropertyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    long countByExample(PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int deleteByExample(PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int insert(Property record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int insertSelective(Property record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    List<Property> selectByExampleWithRowbounds(PropertyExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    List<Property> selectByExample(PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    Property selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int updateByExampleSelective(@Param("record") Property record, @Param("example") PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int updateByExample(@Param("record") Property record, @Param("example") PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int updateByPrimaryKeySelective(Property record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    int updateByPrimaryKey(Property record);
}