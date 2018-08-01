package com.sds.act.coe.zuul;

import com.sds.act.coe.zuul.filter.SimpleFilter;
import com.sds.act.coe.zuul.locator.CoeRouterLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@SpringBootApplication
public class CoeZuulApplication {

    @Autowired
    ZuulProperties zuulProperties;

    @Autowired
    ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(CoeZuulApplication.class, args);
    }

    @Bean
    public SimpleFilter simpleFilter() {
        return new SimpleFilter();
    }

    @Bean
    public CoeRouterLocator coeRouterLocator() {
        return new CoeRouterLocator(serverProperties.getServlet().getServletPrefix(), zuulProperties);
    }

    @Bean
    public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Collections.singletonList("*"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
    }
}
