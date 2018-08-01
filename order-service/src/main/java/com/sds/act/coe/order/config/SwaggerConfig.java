package com.sds.act.coe.order.config;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.sds.act.coe.order.web.rest.ApiConstants.API_V1_BASE_PATH;

@Configuration
@EnableSwagger2
@SwaggerDefinition(
        info = @Info(description = "MSA-COE-CUSTOMER",
                version = "1.0.0",
                title = "MSA-COE-CUSTOMER",
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
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(API_V1_BASE_PATH + "/.*"))
                .build();

    }
}
