package com.example.clouddisk.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author R.Q.
 */
@Data
@Table(name="user")
@Entity
public class User {
    /**
     * 主键GeneratedValue:数据库生成，自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint(20) comment '用户id'")
    private Long userId;

    @Column(columnDefinition = "varchar(30) comment '用户名'")
    private String userName;

    @Column(columnDefinition = "varchar(35) comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(15) comment '手机号码'")
    private String telephone;

    @Column(columnDefinition = "varchar(20) comment '盐值'")
    private String salt;

    @Column(columnDefinition = "varchar(30) comment '注册时间'")
    private String registerTime;
}
