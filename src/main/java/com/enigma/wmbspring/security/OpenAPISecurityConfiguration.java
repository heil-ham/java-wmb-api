package com.enigma.wmbspring.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "WMB SPRING API",
                version = "1.0.0",
                contact = @Contact(
                        name = "El-Ham", email = "ardiansyahilham11@gmail.com", url = "https://www.baeldung.com"
                ),
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "${tos.uri}",
                description = "WMB SPRING API"
        ),
        servers = @Server(
                url = "@{api.server.url}",
                description = "Production"
        )
)
public class OpenAPISecurityConfiguration {
}
