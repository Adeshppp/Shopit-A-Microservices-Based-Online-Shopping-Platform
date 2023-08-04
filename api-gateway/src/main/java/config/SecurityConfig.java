package config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
// We are adding enablewebfluxsecurity annotation bcoz in api gateway, spring-cloud-gateway-project is based on spring webflux project not on spring web MVC.
public class SecurityConfig {
    // this function will configure webfluxsecurity details
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity.csrf()
//                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()) // added CSRF token repository
                .disable() // as we are only communication through REST API
                .authorizeExchange(exchange -> exchange // providing details to declare that how webflux will handle the security
                        .pathMatchers("/eureka/**") // application.properties file, route 3 is declared that root is for static files and we do not want to send any authetication token for those while rendering, so to exclude this call
                        .permitAll() // permit all calls
                        .anyExchange()
                        .authenticated()) // for any exchange/access we want all the calls to be authenticated except above eureka call.
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // enable resource server and JSON web token capabilities in api gateway
        return serverHttpSecurity.build(); // will create object of filterchain and return it
    }
}


//    Configures authorization. An example configuration can be found below:
//@Bean
//public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//        // ...
//        .authorizeExchange((exchanges) ->
//        exchanges
//        // any URL that starts with /admin/ requires the role "ROLE_ADMIN"
//        .pathMatchers("/admin/**").hasRole("ADMIN")
//        // a POST to /users requires the role "USER_POST"
//        .pathMatchers(HttpMethod.POST, "/users").hasAuthority("USER_POST")
//        // a request to /users/{username} requires the current authentication's username
//        // to be equal to the {username}
//        .pathMatchers("/users/{username}").access((authentication, context) ->
//        authentication
//        .map(Authentication::getName)
//        .map((username) -> username.equals(context.getVariables().get("username")))
//        .map(AuthorizationDecision::new)
//        )
//        // allows providing a custom matching strategy that requires the role "ROLE_CUSTOM"
//        .matchers(customMatcher).hasRole("CUSTOM")
//        // any other request requires the user to be authenticated
//        .anyExchange().authenticated()
//        );
//        return http.build();
//        }
