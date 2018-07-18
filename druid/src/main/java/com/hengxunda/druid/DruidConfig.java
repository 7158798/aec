package com.hengxunda.druid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfig {

	// web-stat-filter
	@Value("${spring.datasource.druid.web-stat-filter.url-pattern:/*}")
	private String filterUrlPattern;
	@Value("${spring.datasource.druid.web-stat-filter.exclusions:*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*}")
	private String exclusions;

	// stat-view-servlet
	@Value("${spring.datasource.druid.stat-view-servlet.url-pattern:/druid/*}")
	private String statViewUrlPattern;
	@Value("${spring.datasource.druid.stat-view-servlet.login-username:blockDruid}")
	private String loginUsername;
	@Value("${spring.datasource.druid.stat-view-servlet.login-password:block@2018}")
	private String loginPassword;
	@Value("${spring.datasource.druid.stat-view-servlet.reset-enable:false}")
	private String resetEnable;
	@Value("${spring.datasource.druid.stat-view-servlet.allow:}")
	private String allow;
	@Value("${spring.datasource.druid.stat-view-servlet.deny:}")
	private String deny;

	@Bean
	public ServletRegistrationBean<StatViewServlet> DruidStatViewServle2() {
		ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
				new StatViewServlet(), statViewUrlPattern);
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", allow);
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to
		// view this page.
		servletRegistrationBean.addInitParameter("deny", deny);
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
		servletRegistrationBean.addInitParameter("loginPassword", loginPassword);
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", resetEnable);
		return servletRegistrationBean;

	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> druidStatFilter2() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(
				new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns(filterUrlPattern);
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", exclusions);
		return filterRegistrationBean;
	}

	public String getFilterUrlPattern() {
		return filterUrlPattern;
	}

	public void setFilterUrlPattern(String filterUrlPattern) {
		this.filterUrlPattern = filterUrlPattern;
	}

	public String getExclusions() {
		return exclusions;
	}

	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}

	public String getStatViewUrlPattern() {
		return statViewUrlPattern;
	}

	public void setStatViewUrlPattern(String statViewUrlPattern) {
		this.statViewUrlPattern = statViewUrlPattern;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getResetEnable() {
		return resetEnable;
	}

	public void setResetEnable(String resetEnable) {
		this.resetEnable = resetEnable;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getDeny() {
		return deny;
	}

	public void setDeny(String deny) {
		this.deny = deny;
	}

}
