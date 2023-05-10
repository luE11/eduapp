package pra.luis.eduapp.eduapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
    OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Educational platform api to practice")
                                 .description("API Designed to work with an Angular application for tech test")
                                 .version("1.0"));
    }
	
	@Configuration
	@SecurityScheme(
	  name = "Bearer Authentication",
	  type = SecuritySchemeType.HTTP,
	  bearerFormat = "JWT",
	  scheme = "bearer"
	)
	public class OpenAPI30Configuration {}
}
