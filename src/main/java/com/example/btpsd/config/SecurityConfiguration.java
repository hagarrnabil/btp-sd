package com.example.btpsd.config;

import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;
import com.sap.cloud.security.spring.config.XsuaaServiceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })
@EnableWebSecurity
public class SecurityConfiguration {

    private XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Bean
    public JwtDecoder jwtDecoder(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
             String jwkSetUri =  xsuaaServiceConfiguration.getProperty("url") + "/oauth2/certs";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/accounts",
                "/accounts/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui/index.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/localhost/**",
                "/accounts/create",
                "/accounts/{userId}",
                "/accounts/login"
        );
    }
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:56033");
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/accounts",
                                "/accounts/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/localhost/**",
                                "/accounts/create",
                                "/accounts/{userId}",
                                "/accounts/login"
                        )
                        .permitAll()
                        .requestMatchers(
                                "/formulas/*",
                                "/formulas",
                                "/linetypes/*",
                                "/linetypes",
                                "/materialgroups/*",
                                "/materialgroups",
                                "/modelspecs",
                                "/modelspecs/*",
                                "/modelspecdetails/*",
                                "/modelspecdetails",
                                "/personnelnumbers/*",
                                "/personnelnumbers",
                                "/servicenumbers/*",
                                "/servicenumbers",
                                "/servicetypes/*",
                                "/servicetypes",
                                "/invoices/*",
                                "/invoices",
                                "/subitems/*",
                                "/subitems",
                                "/currencies/*",
                                "/currencies")
                        .hasAnyAuthority("Read","XSUAA-Viewer")
                        .requestMatchers("/mainitems")
                        .hasAnyAuthority("InvoiceViewer", "InvoiceViewerExceptTotal")
                        .requestMatchers("/**").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(authConverter())))
                //.csrf(csrf-> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())); // Use a cookie-based CSRF token)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
