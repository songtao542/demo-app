package com.aperise.bean;

import java.util.Date;

public class ProductProperty {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_property.id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_property.product_id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_property.property_id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    private Integer propertyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_property.gmt_create
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_property.gmt_modify
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    private Date gmtModify;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_property.is_deleted
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    private String isDeleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_property.id
     *
     * @return the value of product_property.id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_property.id
     *
     * @param id the value for product_property.id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_property.product_id
     *
     * @return the value of product_property.product_id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_property.product_id
     *
     * @param productId the value for product_property.product_id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_property.property_id
     *
     * @return the value of product_property.property_id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public Integer getPropertyId() {
        return propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_property.property_id
     *
     * @param propertyId the value for product_property.property_id
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_property.gmt_create
     *
     * @return the value of product_property.gmt_create
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_property.gmt_create
     *
     * @param gmtCreate the value for product_property.gmt_create
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_property.gmt_modify
     *
     * @return the value of product_property.gmt_modify
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_property.gmt_modify
     *
     * @param gmtModify the value for product_property.gmt_modify
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_property.is_deleted
     *
     * @return the value of product_property.is_deleted
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_property.is_deleted
     *
     * @param isDeleted the value for product_property.is_deleted
     *
     * @mbg.generated Sat Sep 02 01:19:39 CST 2017
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}