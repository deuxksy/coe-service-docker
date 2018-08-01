package com.sds.act.coe.zuul.config;

import com.google.common.base.Predicates;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile("!dev")
@Configuration
@EnableSwagger2
@SwaggerDefinition(
        info = @Info(description = "MSA-COE",
                version = "1.0.0",
                title = "MSA-COE",
                termsOfService = "Term of Service",
                contact = @Contact(
                        name = "Samsung SDS",
                        url = "https://www.samsungsds.com/",
                        email = "samsungsds@samsung.com"),
                license = @License(name = "Apache License Version 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0")
        )
)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
//                .paths(PathSelectors.regex("/api/.*"))
                .paths(PathSelectors.any())
                .build();

    }
}
