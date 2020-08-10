package ec.edu.ups.appdis.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		servers = {
				@Server(
						description = "Servidor local",
						url = "/owa")		
				}
)

@ApplicationPath("/rs")
public class RestApplication extends Application {
	
}
