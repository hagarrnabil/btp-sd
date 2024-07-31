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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true) // TODO "debug" may include sensitive information. Do not use in a production system!
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
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

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(jpaUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/iasusers", "/formulas/*", "/formulas", "/linetypes/*", "/linetypes", "/materialgroups/*", "/materialgroups", "/modelspecs", "/modelspecs/*",
                "/modelspecdetails/*", "/modelspecdetails", "/personnelnumbers/*", "/personnelnumbers", "/servicenumbers/*", "/servicenumbers", "/servicetypes/*", "/servicetypes",
                "/invoices/*", "/invoices", "/mainitems/*", "/mainitems", "/subitems/*", "/subitems", "/currencies/*", "/currencies", "/salesorder", "/salesorder/*", "/salesorderitems", "/salesorderitems/*," ,
                        "/salesorderpricing", "/salesorderpricing/*", "/executionordersub", "/executionordersub/*", "/executionordermain", "/executionordermain/*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers("/measurements/*").hasRole("USER")
                                .requestMatchers("/formulas/*").hasRole("USER")
                                .requestMatchers("/currencies/*").hasRole("USER")
                                .requestMatchers("/linetypes/*").hasRole("USER")
                                .requestMatchers("/materialgroups/*").hasRole("USER")
                                .requestMatchers("/modelspecs/*").hasRole("USER")
                                .requestMatchers("/modelspecdetails/*").hasRole("USER")
                                .requestMatchers("/personnelnumbers/*").hasRole("USER")
                                .requestMatchers("/servicenumbers/*").hasRole("USER")
                                .requestMatchers("/servicetypes/*").hasRole("USER")
                                .requestMatchers("/invoices/*").hasRole("USER")
                                .requestMatchers("/mainitems/*").hasRole("USER")
                                .requestMatchers("/subitems/*").hasRole("USER")
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                .requestMatchers("/*").authenticated()
                                .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new MyCustomHybridTokenAuthenticationConverter())));

        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }



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