package com.xgy.base.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MyBatis-Plus 配置
 */
@Configuration
@MapperScan("com.xgy.base.mapper")
public class MyBatisPlusConfig {

    private static final String DEFAULT_CREATE_USER = "system";

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                // create_user：默认 system
                if (getFieldValByName("createUser", metaObject) == null) {
                    setFieldValByName("createUser", DEFAULT_CREATE_USER, metaObject);
                }
                // create_date：当前系统时间
                if (getFieldValByName("createDate", metaObject) == null) {
                    setFieldValByName("createDate",
                            LocalDateTime.now().format(DATE_TIME_FORMATTER), metaObject);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                setFieldValByName("updateUser", DEFAULT_CREATE_USER, metaObject);
                setFieldValByName("updateDate",
                        LocalDateTime.now().format(DATE_TIME_FORMATTER), metaObject);
            }
        };
    }
}
