package com.example.btpsd.config;
import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })

@EnableWebSecurity
public class SecurityConfiguration {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/v3/api-docs/**",    // OpenAPI 3 Docs
                                "/swagger-ui/**",      // Swagger UI resources
                                "/swagger-ui/index.html",    // Main Swagger UI page
                                "/swagger-resources/**", // Swagger resources
                                "/webjars/**",
                                "/localhost/**"
                        ).permitAll()
                        .requestMatchers( "/measurements/*",
                                "/api/v1/auth/**")
                        .hasAuthority("XSUAA-User") // Map to XSUAA-User role collection
                        .requestMatchers("/sayHello").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/comp/sayHello").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/formulas/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/currencies/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/linetypes/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/materialgroups/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/modelspecs/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/modelspecdetails/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/personnelnumbers/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/servicenumbers/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/servicetypes/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/invoices/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/mainitems/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/subitems/*").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers("/*").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    // Convert JWT scopes to authorities
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
    }
}



