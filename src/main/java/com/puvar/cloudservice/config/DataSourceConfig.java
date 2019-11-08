package com.puvar.cloudservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.puvar.cloudservice.common.constants.MapperPackage;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/***
 * 多数据源等相关配置
 * @Auther: dingyuanmeng
 * @Date: 2019/10/22
 * @version : 1.0
 */
@Configuration
public class DataSourceConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * springcloud数据源配置
     */
    @Bean("springCloudDataSource")
    @Primary
    @ConfigurationProperties(prefix = "springcloud.spring.datasource")
    public DataSource springCloudDataSource() {
        return new DruidDataSource();
    }

    @Primary
    @Bean("springCloudTransactionManager")
    public DataSourceTransactionManager springCloudTransactionManager(@Qualifier("springCloudDataSource") DataSource springCloudDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(springCloudDataSource);
        return transactionManager;
    }

    @Bean("springCloudSqlSessionFactory")
    @Primary
    public SqlSessionFactory springCloudSqlSessionFactory(@Qualifier("springCloudDataSource") DataSource springCloudDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        Resource[] resource = context.getResources(MapperPackage.springcloud_xml_mapper);
        factoryBean.setDataSource(springCloudDataSource);
        factoryBean.setMapperLocations(resource);
        return factoryBean.getObject();
    }

    @Bean
    @Primary
    public MapperScannerConfigurer springCloudScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(MapperPackage.springcloud_mapper);
        scannerConfigurer.setSqlSessionFactoryBeanName("springCloudSqlSessionFactory");
        return scannerConfigurer;
    }

    /**
     * test数据源配置
     */
    @Bean("testDataSource")
    @ConfigurationProperties(prefix = "test.spring.datasource")
    public DataSource testDataSource() {
        return new DruidDataSource();
    }

    @Bean("testTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("testDataSource")DataSource testDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(testDataSource);
        return transactionManager;
    }

    @Bean("testSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("testDataSource")DataSource testDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        Resource[] resource = context.getResources(MapperPackage.test_xml_mapper);
        factoryBean.setDataSource(testDataSource);
        factoryBean.setMapperLocations(resource);
        return factoryBean.getObject();
    }

    @Bean
    public MapperScannerConfigurer testScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(MapperPackage.test_mapper);
        scannerConfigurer.setSqlSessionFactoryBeanName("testSqlSessionFactory");
        return scannerConfigurer;
    }
}

