package com.xgy.base.job;

import com.xgy.base.config.JobHelper;
import com.xgy.base.entity.User;
import com.xgy.base.service.UserService;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@EnableBatchProcessing
@Configuration
public class TestJob extends JobHelper {

    @Autowired
    private UserService userService;

    @Bean
    public Job testJob1() {
        return jobs.get("testJob1")
                .start(testStep())
                .build();
    }

    @Bean
    public Step testStep() {
        return steps.get("testStep")
                .<User, User>chunk(batchSize)
                .reader(testUserReader())
                .processor(testUserProcessor())
                .writer(testUserWriter())
                .build();
    }

    /**
     * 游标方式流式读取 user 表全部数据。
     * queryId 为 mapper 方法的全限定名。
     */
    @Bean
    public MyBatisCursorItemReader<User> testUserReader() {
        MyBatisCursorItemReader<User> reader = new MyBatisCursorItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.xgy.base.mapper.UserMapper.selectAll");
        return reader;
    }

    /**
     * 将用户 status 字段修改为 2。
     */
    @Bean
    public ItemProcessor<User, User> testUserProcessor() {
        return user -> {
            user.setStatus(2);
            return user;
        };
    }

    /**
     * 批量更新到数据库。
     */
    @Bean
    public ItemWriter<User> testUserWriter() {
        return list -> userService.updateBatchById(new ArrayList<>(list));
    }
}
