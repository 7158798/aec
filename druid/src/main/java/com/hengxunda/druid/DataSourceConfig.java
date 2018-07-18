package com.hengxunda.druid;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.initialSize}")
	private Integer initialSize;
	@Value("${spring.datasource.minIdle}")
	private Integer minIdle;
	@Value("${spring.datasource.maxActive}")
	private Integer maxActive;
	@Value("${spring.datasource.maxWait}")
	private Integer maxWait;
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private Integer timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private Integer minEvictableIdleTimeMillis;
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.testWhileIdle}")
	private Boolean testWhileIdle;
	@Value("${spring.datasource.testOnBorrow}")
	private Boolean testOnBorrow;
	@Value("${spring.datasource.testOnReturn}")
	private Boolean testOnReturn;
	@Value("${spring.datasource.poolPreparedStatements}")
	private Boolean poolPreparedStatements;
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private Integer maxPoolPreparedStatementPerConnectionSize;
	@Value("${spring.datasource.filters}")
	private String filters;

	@Bean(destroyMethod = "close", initMethod = "init")
	@Primary
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		// configuration
		if (initialSize != null && initialSize > 0) {
			datasource.setInitialSize(initialSize);
		}
		if (minIdle != null && minIdle > 0) {
			datasource.setMinIdle(minIdle);
		}
		if (maxActive != null && maxActive > 0) {
			datasource.setMaxActive(maxActive);
		}
		if (maxWait != null && maxWait > 0) {
			datasource.setMaxWait(maxWait);
		}
		if (timeBetweenEvictionRunsMillis != null) {
			datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		}
		if (minEvictableIdleTimeMillis != null) {
			datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		}
		if (validationQuery != null) {
			datasource.setValidationQuery(validationQuery);
		}
		if (testWhileIdle != null) {
			datasource.setTestWhileIdle(testWhileIdle);
		}
		if (testOnBorrow != null) {
			datasource.setTestOnBorrow(testOnBorrow);
		}
		if (testOnReturn != null) {
			datasource.setTestOnReturn(testOnReturn);
		}
		if (poolPreparedStatements != null) {
			datasource.setPoolPreparedStatements(poolPreparedStatements);
		}
		if (maxPoolPreparedStatementPerConnectionSize != null) {
			datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		}
		if (filters != null && !"".equals(filters)) {
			try {
				datasource.setFilters(filters);
			} catch (SQLException e) {
				logger.error("druid configuration initialization filter: {}", e);
			}
		}
		return datasource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public Boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public Boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public Boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

}
