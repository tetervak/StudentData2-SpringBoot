package ca.javateacher.studentdata.config;

import ca.javateacher.studentdata.filters.RequestLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestLogFilter> loggingFilter(){
        FilterRegistrationBean<RequestLogFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestLogFilter());
        registrationBean.setOrder(10);
        registrationBean.addUrlPatterns("/users/*");

        return registrationBean;
    }

}
