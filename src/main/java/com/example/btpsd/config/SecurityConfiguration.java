package com.example.btpsd.config;
import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;



@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Use 'groups' claim instead of 'scope'
        grantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
        grantedAuthoritiesConverter.setAuthorityPrefix(""); // No prefix for roles in groups

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

//    @Bean
//    public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }

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
                "/accounts/*",
                "/accounts/login",
                "/iasusers",
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
                "/mainitems/*",
                //"/mainitems",
                "/subitems/*",
                "/subitems",
                "/currencies/*",
                "/currencies"
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:56033");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
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
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/localhost/**",
                                "/accounts",
                                "/accounts/**",
                                "/accounts/*",
                                "/accounts/create",
                                "/accounts/{userId}",
                                "/accounts/login",
                                "/iasusers",
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
                                "/mainitems/*",
//                                "/mainitems",
                                "/subitems/*",
                                "/subitems",
                                "/currencies/*",
                                "/currencies"
                        ).permitAll()
                        .requestMatchers("/mainitems").hasAuthority("InvoiceViewer")
                       .requestMatchers("/*").authenticated()
                                .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(authConverter())))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
