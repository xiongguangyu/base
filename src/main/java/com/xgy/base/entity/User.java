package com.xgy.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author xgy
 * @since 2026-10-22
 */
@Getter
@Setter
@TableName("user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 角色: 1-普通用户 2-管理员
     */
    @TableField("role")
    private Integer role;

    /**
     * 状态: 0-禁用 1-启用
     */
    @TableField("status")
    private Integer status;


    @TableField("email")
    private String email;
}
