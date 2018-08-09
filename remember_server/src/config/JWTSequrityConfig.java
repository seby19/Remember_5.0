package config;

 

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import authentication.JWTAuthenticationEntryPoint;
import authentication.JWTAuthenticationProvider;
//import authentication.JWTSuccessHandler;
//import authentication.JwtAuthenticationTokenFilter;
import authentication.JwtAuthenticationTokenFilter1;



@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
@EnableWebMvcSecurity
@Configuration
@ComponentScan({"com" , "remember_dao" , "remember_service" , "authentication" , "remember_dto" , "webSocket" , "config"})
public class JWTSequrityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTAuthenticationProvider authenticationProvider;
	
	@Autowired
	private JWTAuthenticationEntryPoint entryPoint;
	
	
	/*@Autowired
	private JWTSuccessHandler successHandler;*/
	
	@Bean
	public AuthenticationManager authenticationManager()
	{
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}
	
	/*@Bean
	public JwtAuthenticationTokenFilter authenticationFilter() {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(this.authenticationManager());
		filter.setAuthenticationSuccessHandler(this.successHandler);
		return filter;
	}*/
	
	
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/signup").permitAll()
			.antMatchers("/token").permitAll()
			.antMatchers("/socket/**").permitAll()
			.anyRequest().authenticated() 
			.and()
			.exceptionHandling().authenticationEntryPoint(entryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	;
		JwtAuthenticationTokenFilter1 jwt = new JwtAuthenticationTokenFilter1();
		http.addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
		//http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}
	
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
            .ignoring()
            .antMatchers(
                
                "/login",
                "/signup",
                "/token",
                "/socket/**");
		}
	
	
	private String driverClassName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/test";
    private String username = "root";
    private String password = "Changeyourlife";
    
    private String hibernateDialect = "org.hibernate.dialect.MySQLDialect";
    private String hibernateShowSql = "true";
    private String hibernateHbm2ddlAuto = "create";
        
    @Bean()    
    public DataSource getDataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();        
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);        
        return ds;
    }
    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory);
        return htm;
    }
    
    @Bean
    @Autowired
    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
    {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }
        
    @Bean
    public LocalSessionFactoryBean getSessionFactory()
    {
    	LocalSessionFactoryBean asfb = new LocalSessionFactoryBean();
        asfb.setDataSource(getDataSource());
        asfb.setHibernateProperties(getHibernateProperties());        
        asfb.setPackagesToScan(new String[]{"remember_dto"});
        return asfb;
    }

    @Bean
    public Properties getHibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        
        return properties;
    }
	
}
