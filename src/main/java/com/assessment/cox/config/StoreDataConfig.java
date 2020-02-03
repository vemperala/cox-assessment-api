package com.assessment.cox.config;

import java.util.HashMap;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by akhilesh on 2/1/20.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.assessment.cox.repository"}, entityManagerFactoryRef = "coxStoreDataEntityManagerFactory", transactionManagerRef = "coxStoreDataTransactionManager")
@EnableTransactionManagement
public class StoreDataConfig {


  @Value("${cox.store.data.hibernate.show_sql}")
  private boolean hibernateShowSql;

  @Bean("coxStoreDataSourceProperties")
  @ConfigurationProperties("cox.store.data.spring.datasource")
  @Primary
  public DataSourceProperties coxStoreDataSourceProperties() {
    DataSourceProperties dataSourceProperties =  new DataSourceProperties();
    return dataSourceProperties;
  }

  @Bean("coxStoreDataSource")
  @Primary
  public DataSource coxStoreDataSource(@Qualifier("coxStoreDataSourceProperties") final DataSourceProperties dataSourceProperties){
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Bean("coxStoreDataEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean coxStoreDataEntityManagerFactory(@Qualifier("coxStoreDataSource") final DataSource dataSource){
    final HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.search.default.directory_provider", "filesystem");
    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

    final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(dataSource);
    entityManagerFactory.setPackagesToScan("com.assessment.cox.entity");
    entityManagerFactory.setPersistenceUnitName("store-data");
    entityManagerFactory.setJpaVendorAdapter(coxStoreDataJpaVendorAdapter());
    entityManagerFactory.setJpaPropertyMap(properties);
    entityManagerFactory.afterPropertiesSet();
    return entityManagerFactory;
  }

  @Bean("coxStoreDataJpaVendorAdapter")
  public JpaVendorAdapter coxStoreDataJpaVendorAdapter(){
    final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(hibernateShowSql);
    return hibernateJpaVendorAdapter;
  }

  @Bean("coxStoreDataTransactionManager")
  public JpaTransactionManager coxStoreDataTransactionManager(@Qualifier("coxStoreDataEntityManagerFactory") final EntityManagerFactory entityManagerFactory){
    return new JpaTransactionManager(entityManagerFactory);
  }
}

