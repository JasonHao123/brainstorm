package jason.app.brainstorm.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private MutableAclService aclService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }
    
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
	    DefaultMethodSecurityExpressionHandler expressionHandler = defaultMethodSecurityExpressionHandler();
	    expressionHandler.setPermissionEvaluator(new AclPermissionEvaluator(aclService));
	    expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService));
	    return expressionHandler;
	}
	

	@Bean
	public EhCacheBasedAclCache aclCache() {
	    return new EhCacheBasedAclCache(aclEhCacheFactoryBean().getObject(), permissionGrantingStrategy(), aclAuthorizationStrategy());
	}

	@Bean
	public EhCacheFactoryBean aclEhCacheFactoryBean() {
	    EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
	    ehCacheFactoryBean.setCacheManager(aclCacheManager().getObject());
	    ehCacheFactoryBean.setCacheName("aclCache");
	    return ehCacheFactoryBean;
	}

	@Bean
	public EhCacheManagerFactoryBean aclCacheManager() {
	    return new EhCacheManagerFactoryBean();
	}

	@Bean
	public DefaultPermissionGrantingStrategy permissionGrantingStrategy() {
	    ConsoleAuditLogger consoleAuditLogger = new ConsoleAuditLogger();
	    return new DefaultPermissionGrantingStrategy(consoleAuditLogger);
	}

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
	    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
	}

	
	@Bean
	public LookupStrategy lookupStrategy() {
	    return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
	}

//	@Bean
//	public JdbcMutableAclService aclService() {
//	    JdbcMutableAclService service = new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
//	    return service;
//	}

	@Bean
	public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
	    return new DefaultMethodSecurityExpressionHandler();
	}
}
