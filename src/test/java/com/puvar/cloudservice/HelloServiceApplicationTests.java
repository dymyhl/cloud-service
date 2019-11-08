package com.puvar.cloudservice;

import com.puvar.cloudservice.controller.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceApplicationTests {

    private MockMvc mockMvc;

    @Value("${log.level}")
    public String logLevel;

    @Before
    public void createMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    // 测试本地调用，查询本地服务
    @Test
    public void testHello() throws Exception {
        System.out.println(logLevel + "=====");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get")).andReturn();
        System.out.println(mvcResult);
    }

    @Configuration
    @PropertySource("classpath:application.yml")
    static class PropertiesWithJavaConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer
        propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

}
