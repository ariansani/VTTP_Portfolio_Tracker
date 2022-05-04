package vttp2022.nusiss.arian.miniproject.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vttp2022.nusiss.arian.miniproject.filters.AuthenticationFilter;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> registerFilters() {

        AuthenticationFilter authFilter = new AuthenticationFilter();
        FilterRegistrationBean<AuthenticationFilter> regFilter = new FilterRegistrationBean<>();
        regFilter.setFilter(authFilter);
        regFilter.addUrlPatterns("/protected/*");

        return regFilter;

    }
}
