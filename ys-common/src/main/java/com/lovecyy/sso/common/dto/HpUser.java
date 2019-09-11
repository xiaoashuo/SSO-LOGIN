package com.lovecyy.sso.common.dto;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "hp_user")
public class HpUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 花名
     */
    @Column(name = "username")
    private String username;

    /**
     * 姓名
     */
    @Column(name = "realname")
    private String realname;

    /**
     * 手机号
     */
    @Column(name = "mobile_no")
    private String mobileNo;

    /**
     * 0男 1女 2未知
     */
    @Column(name = "gender")
    private Boolean gender;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 头像
     */
    @Column(name = "headimg")
    private String headimg;

    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 盐值
     */
    @Column(name = "salt")
    private String salt;

    /**
     * 推广码
     */
    @Column(name = "referer_code")
    private String refererCode;

    /**
     * 推广链接
     */
    @Column(name = "referer_url")
    private String refererUrl;

    /**
     * 账户余额
     */
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * h1余额
     */
    @Column(name = "h1_balance")
    private BigDecimal h1Balance;

    /**
     * 0启用 1禁用
     */
    @Column(name = "`status`")
    private Boolean status;

    /**
     * 0 正常 1删除
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

    /**
     * 微信号码
     */
    @Column(name = "wx_code")
    private String wxCode;

    /**
     * 支付宝号码
     */
    @Column(name = "alipay_code")
    private String alipayCode;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}