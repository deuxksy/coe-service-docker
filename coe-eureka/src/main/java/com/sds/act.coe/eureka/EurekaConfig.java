package com.sds.act.coe.eureka;

import com.netflix.appinfo.AmazonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
public class EurekaConfig {

    @Value("${server.port}")
    private int port;


    // THIS BEAN IS USED IN ONLY AWS
    @Bean
//    @Profile({"!docker"})
    @Conditional(ProfileCondition.class)
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {
        EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
        b.setHostname("test");
        b.setIpAddress("bcd");
        b.setHomePageUrl("dddd");
        b.setNonSecurePort(port);
        return b;
    }
}

class ProfileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().acceptsProfiles("docker");
    }
}