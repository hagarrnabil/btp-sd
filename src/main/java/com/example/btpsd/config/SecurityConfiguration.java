package com.example.btpsd.config;

import com.example.btpsd.services.security.JpaUserDetailsService;
import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;
import com.sap.cloud.security.spring.token.authentication.AuthenticationToken;
import com.sap.cloud.security.token.TokenClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true) // TODO "debug" may include sensitive information. Do not use in a production system!
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = {""})
public class SecurityConfiguration {

    @Autowired
    Converter<Jwt, AbstractAuthenticationToken> authConverter; // Required only when Xsuaa is used

    @Autowired
    JpaUserDetailsService jpaUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, JpaUserDetailsService jpaUserDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(jpaUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//
//        return new InMemoryUserDetailsManager(
//                User.withUsername("user").password(bCryptPasswordEncoder.encode("1234")).roles("USER").build(),
//                User.withUsername("salesAdmin").password(bCryptPasswordEncoder.encode("1234")).roles("SALESADMIN").build(),
//                User.withUsername("admin").password(bCryptPasswordEncoder.encode("1234")).roles("ADMIN").build()
//        );
//    }

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/iasusers");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers("/companies/*").hasRole("ADMIN")
                                .requestMatchers("/locations/*").hasRole("USER")
                                .requestMatchers("/profits/*").hasRole("USER")
                                .requestMatchers("/projects/*").hasRole("USER")
                                .requestMatchers("/buildings/*").hasRole("USER")
                                .requestMatchers("/buildingtypes/*").hasRole("USER")
                                .requestMatchers("/units/*").hasRole("USER")
                                .requestMatchers("/unitorientations/*").hasRole("USER")
                                .requestMatchers("/unitfixture/*").hasRole("USER")
                                .requestMatchers("/unitstatuses/*").hasRole("USER")
                                .requestMatchers("/unitviews/*").hasRole("USER")
                                .requestMatchers("/usagetype/*").hasRole("USER")
                                .requestMatchers("/unitsubtypes/*").hasRole("USER")
                                .requestMatchers("/unitfloors/*").hasRole("USER")
                                .requestMatchers("/areas/*").hasRole("USER")
                                .requestMatchers("/projectareas/*").hasRole("USER")
                                .requestMatchers("/buildingareas/*").hasRole("USER")
                                .requestMatchers("/unitareas/*").hasRole("USER")
                                .requestMatchers("/measurements/*").hasRole("USER")
                                .requestMatchers("/*").authenticated()
                                .anyRequest().denyAll())
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new MyCustomHybridTokenAuthenticationConverter());

        http.csrf().disable();
//        http.csrf().ignoringRequestMatchers("/companies/*","/locations/*","/profits/*","/projects/*",
//                "/buildings/*","/buildingtypes/*","/units/*","/unitorientations/*","/unitfixture/*",
//                "/unitstatuses/*","/unitviews/*","/usagetype/*","/unitsubtypes/*","/unitfloors/*","/areas/*",
//                "/projectareas/*","/buildingareas/*","/unitareas/*","/measurements/*","/iasusers/*");

        return http.build();
    }


//    @Configuration
//    public class CustomClaimsConfiguration {
//        @Bean
//        public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
//            return (context) -> {
//                if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
//                    context.getClaims().claims((claims) -> {
//                        claims.put("claim-1", "value-1");
//                        claims.put("claim-2", "value-2");
//                    });
//                }
//            };
//        }
//    }

//    @Bean
//    OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
//        return context -> {
//            if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
//                Authentication principal = context.getPrincipal();
//                Set<String> authorities = principal.getAuthorities().stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.toSet());
//                context.getClaims().claim("roles", authorities);
//            }
//        };
//    }

    /**
     * Workaround for hybrid use case until Cloud Authorization Service is globally available.
     */
    class MyCustomHybridTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        public AbstractAuthenticationToken convert(Jwt jwt) {
            if (jwt.hasClaim(TokenClaims.XSUAA.EXTERNAL_ATTRIBUTE)) {
                return authConverter.convert(jwt);
            }
            return new AuthenticationToken(jwt, deriveAuthoritiesFromGroup(jwt));
        }

        private Collection<GrantedAuthority> deriveAuthoritiesFromGroup(Jwt jwt) {
            Collection<GrantedAuthority> groupAuthorities = new ArrayList<>();
            if (jwt.hasClaim(TokenClaims.GROUPS)) {
                List<String> groups = jwt.getClaimAsStringList(TokenClaims.GROUPS);
                for (String group : groups) {
                    groupAuthorities.add(new SimpleGrantedAuthority(group.replace("IASAUTHZ_", "")));
                }
            }
            return groupAuthorities;
        }
    }

    /**
     * Workaround for IAS only use case until Cloud Authorization Service is globally available.
     */

    static class MyCustomIasTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        public AbstractAuthenticationToken convert(Jwt jwt) {
            final List<String> groups = jwt.getClaimAsStringList(TokenClaims.GROUPS);
            final List<GrantedAuthority> groupAuthorities = groups == null ? Collections.emptyList()
                    : groups.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new AuthenticationToken(jwt, groupAuthorities);
        }
    }

}
