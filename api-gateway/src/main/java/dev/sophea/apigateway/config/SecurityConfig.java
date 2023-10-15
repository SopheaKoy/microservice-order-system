package dev.sophea.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize ->

                        authorize.anyRequest().permitAll())

                .oauth2ResourceServer(oauth2 ->

                        oauth2.jwt((Customizer.withDefaults())));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(

                "http://localhost:8080/oauth2/jwks"

        ).build();

        return jwtDecoder;
    }

}

