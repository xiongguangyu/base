package com.xgy.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "create_user", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private String createUser;

    @TableField(value = "create_date", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private String createDate;

    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;

    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private String updateDate;


}
