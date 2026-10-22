package com.xgy.base.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.xgy.base.entity.BaseEntity;

import java.util.Collections;

public class CodeGenerator {

    private static final String URL = "jdbc:mysql://localhost:3306/space?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /**
     * 父包名，与当前项目保持一致。
     */
    private static final String PARENT_PACKAGE = "com.xgy.base";

    /**
     * 要生成代码的表名（不含库名前缀），可传多个。
     */
    private static final String[] TABLE_NAMES = {"user"};

    private static final String PROJECT_ROOT = System.getProperty("user.dir");
    private static final String JAVA_OUTPUT_DIR = PROJECT_ROOT + "/src/main/java";
    private static final String XML_OUTPUT_DIR = PROJECT_ROOT + "/src/main/resources/mapper";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder
                        .author("xgy")
                        .outputDir(JAVA_OUTPUT_DIR)
                        .disableOpenDir())
                .packageConfig(builder -> builder
                        .parent(PARENT_PACKAGE)
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, XML_OUTPUT_DIR)))
                .strategyConfig(builder -> builder
                        .addInclude(TABLE_NAMES)
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        // 生成的实体自动继承 BaseEntity，且下列父类字段不再重复生成
                        .superClass(BaseEntity.class)
                        .addSuperEntityColumns("id", "create_user", "create_date", "update_user", "update_date")
                        .controllerBuilder()
                        .enableRestStyle()
                        .mapperBuilder()
                        .enableMapperAnnotation()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl"))
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}
