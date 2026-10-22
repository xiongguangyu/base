package com.xgy.base.mapper;

import com.xgy.base.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2026-10-22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Options(fetchSize = Integer.MIN_VALUE)
    @Select("select * from user")
    Cursor<User> selectAll();
}
