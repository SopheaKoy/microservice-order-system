package dev.sophea.authorizationservice.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfiguration {

    // Security Filter China for Web
    @Bean
    @Order(1)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());	// Initialize `OidcConfigurer`


        // sample exception handling customization.
        http.exceptionHandling(e -> {
            e.authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login"));
        });


        return http.build();
    }

    // Security Filter China for App
    @Bean
    @Order(2)
    public SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService (){

        var viewer = User.withUsername("viewer")
                .password("1234")
                .authorities("read")
                .roles("VIEWER")
                .build();

        var admin = User.withUsername("admin")
                .password("pass5678")
                .authorities("read")
                .roles("VIEWER","ADMIN")
                .build();


        return new InMemoryUserDetailsManager(viewer , admin);
    }

    // Create Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }


    // Create RegisteredClientRepository for define scope grant type .

    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        var registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())

                .clientId("public-client-react-app")

                .clientSecret("secret")

                .scope(OidcScopes.OPENID)

                .scope(OidcScopes.PROFILE)

                .tokenSettings(tokenSettings())

                .redirectUri("http://127.0.0.1:8090/login/oauth2/code/public-client-react-app")

                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // public client

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

                .authorizationGrantTypes(grantType -> {

                    grantType.add(AuthorizationGrantType.AUTHORIZATION_CODE);

                    grantType.add(AuthorizationGrantType.REFRESH_TOKEN);

                    grantType.add(AuthorizationGrantType.CLIENT_CREDENTIALS);


                }).clientSettings(ClientSettings.builder().requireProofKey(true).build())

                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

     //Create AuthorizationServerSetting
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){

        return AuthorizationServerSettings.builder().build();
    }

    // Create JWTSource
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {

        // create key pair

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        // create public key and private key

        var keys = keyPairGenerator.generateKeyPair();

        var publicKey = (RSAPublicKey) keys.getPublic();

        var privateKey = (RSAPrivateKey) keys.getPrivate();


        RSAKey rsaKey = new RSAKey.Builder(publicKey)

                .privateKey(privateKey)

                .keyID(UUID.randomUUID().toString())

                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);
    }

    // create jwt decoder
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {

        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


    // Customize OAuth2 Token
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer(){

        return context -> {

            if (context.getTokenType().getValue().equals(OAuth2TokenType.ACCESS_TOKEN.getValue())){

                Authentication principal = context.getPrincipal();

                var authorities = principal.getAuthorities().stream()

                        .map(GrantedAuthority::getAuthority)

                        .collect(Collectors.toSet());

                context.getClaims().claim("authorities", authorities);

            }

        };

    }

    // Defined Token expiration time

    @Bean
    public TokenSettings tokenSettings(){
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(2)) // token expiration 2 hours
                .build();

    }
}
