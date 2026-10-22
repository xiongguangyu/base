package com.xgy.base.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源与 SqlSessionFactory 配置。
 * <p>
 * 定义名为 dsbase 的主数据源，并基于该数据源手动构建 MyBatis-Plus 的 SqlSessionFactory。
 * 由于手动声明了 SqlSessionFactory，MyBatis-Plus 的自动配置会退让，
 * 因此原先依赖 application.yml 中 mybatis-plus.* 的配置需在此显式设置。
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 主数据源 dsbase。
     */
    @Bean("dsbase")
    @Primary
    public DataSource dsbase() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(url);
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setDriverClassName(driverClassName);
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * 基于 dsbase 重写 SqlSessionFactory，保留分页插件、字段自动填充、驼峰映射与主键策略。
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dsbase") DataSource dataSource,
                                               MybatisPlusInterceptor mybatisPlusInterceptor,
                                               MetaObjectHandler metaObjectHandler) throws Exception {

        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // MyBatis 核心配置：驼峰映射 + 日志实现
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(StdOutImpl.class);
        factoryBean.setConfiguration(configuration);

        // 插件：分页等
        factoryBean.setPlugins(mybatisPlusInterceptor);

        // 全局配置：主键策略 + 自动填充处理器
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setMetaObjectHandler(metaObjectHandler);
        factoryBean.setGlobalConfig(globalConfig);

        // 类型别名 + mapper xml 位置
        factoryBean.setTypeAliasesPackage("com.xgy.base.entity");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:/mapper/**/*.xml"));

        return factoryBean.getObject();
    }

    /**
     * 事务管理器，绑定 dsbase，确保 Spring Batch 与 MyBatis-Plus 使用同一数据源。
     * 命名为 dsbaseTransactionManager，避免与 Spring Batch 内置的 transactionManager Bean 冲突。
     */
    @Bean("dsbaseTransactionManager")
    @Primary
    public DataSourceTransactionManager dsbaseTransactionManager(@Qualifier("dsbase") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
