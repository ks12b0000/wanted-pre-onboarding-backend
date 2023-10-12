package wantedpreonboardingbackend.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo())
                .ignoredParameterTypes(ErrorController.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("wanted-pre-onboarding-backend API")
                .description("wanted-pre-onboarding-backend API 문서")
                .version("1.0")
                .build();
    }
}
