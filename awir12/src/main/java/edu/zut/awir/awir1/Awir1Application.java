package edu.zut.awir.awir1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    servers = {@Server(url = "/", description = "Default Server URL")},
    security = @SecurityRequirement(name = "basicAuth") 
)

@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
@SpringBootApplication
public class Awir1Application {

    public static void main(String[] args) {
        SpringApplication.run(Awir1Application.class, args);
    }
}