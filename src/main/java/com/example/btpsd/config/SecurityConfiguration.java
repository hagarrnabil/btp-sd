package com.example.btpsd.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })
@EnableWebSecurity
@CrossOrigin(origins = "*", allowedHeaders = "*",maxAge = 3600L)
public class SecurityConfiguration {

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
        return jwt -> {
            Collection<GrantedAuthority> authorities = jwt.getClaimAsStringList("Groups").stream()
                    .map(group -> new SimpleGrantedAuthority("ROLE_" + group.toUpperCase()))
                    .collect(Collectors.toList());
            return new JwtAuthenticationToken(jwt, authorities);
        };
    }
    // @Bean
    // public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
    // JwtAuthenticationConverter jwtAuthenticationConverter = new
    // JwtAuthenticationConverter();
    // JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new
    // JwtGrantedAuthoritiesConverter();

    // // Use 'groups' claim instead of 'scope'
    // grantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
    // grantedAuthoritiesConverter.setAuthorityPrefix(""); // No prefix for roles in
    // groups

    // jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    // return jwtAuthenticationConverter;
    // }
  @Bean
    public JwtDecoder jwtDecoder() {
        // Replace the following with your own JWK Set URL or signing key
        return NimbusJwtDecoder.withJwkSetUri("https://avirxf4ow.trial-accounts.ondemand.com/oauth2/authorize/jwks.json").build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/iasusers");
        // return (web) -> web.ignoring().requestMatchers("/iasusers", "/formulas/",
        // "/formulas", "/linetypes/", "/linetypes", "/materialgroups/",
        // "/materialgroups", "/modelspecs", "/modelspecs/",
        // "/modelspecdetails/", "/modelspecdetails", "/personnelnumbers/",
        // "/personnelnumbers", "/servicenumbers/", "/servicenumbers", "/servicetypes/",
        // "/servicetypes",
        // "/invoices/", "/invoices", "/mainitems/////", "/mainitems//",
        // "/mainitems","/mainitems/", "/subitems/", "/subitems", "/currencies/",
        // "/currencies", "/salesorder", "/salesorder/", "/salesorderitems",
        // "/salesorderitems/*," ,
        // "/salesorderpricing", "/salesorderpricing/", "/executionordersub",
        // "/executionordersub/", "/executionordermain//", "/executionordermain///",
        // "/executionordermain", "/executionordermain/","/salesordercloud",
        // "/salesordercloud/*",
        // "/salesorderpostcloud", "/salesorderpostcloud/", "/serviceinvoice/////",
        // "/serviceinvoice//", "/serviceinvoice", "/serviceinvoice/",
        // "/salesorderitemcloud/", "/salesorderitemcloud",
        // "/salesorderitemscloud/", "/salesorderitemscloud",
        // "/salesorderpricingcloud//", "/salesorderpricingcloud",
        // "/salesquotationcloud", "/salesquotationcloud/",
        // "/salesquotationpostcloud/", "/salesquotationpostcloud",
        // "/salesquotationitemcloud/", "/salesquotationitemcloud",
        // "/salesquotationitemscloud", "/salesquotationitemscloud/*",
        // "/salesquotationpricingcloud//", "/salesquotationpricingcloud",
        // "/debitmemocloud/" , "/debitmemocloud", "/debitmemopostcloud/",
        // "/debitmemopostcloud",
        // "/debitmemoitemscloud", "/debitmemoitemscloud/","/salesorderallpricingcloud",
        // "/salesorderallpricingcloud/", "/salesorderitempricingcloudpost//",
        // "/salesquotationricingcloudpatch////", "/productcloud",
        // "/productdescriptioncloud", "/businesspartner", "/salesquotationitem//",
        // "/salesorderitem//", "/debitmemoitems//", "/allproductscloud", "/quantities",
        // "/total", "/totalheader", "/totalsrv", "/totalheadersrv",
        // "/fetchSalesOrderDetails", "/measurements", "/measurements/*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/localhost/**",
                                "/accounts/create",
                                "/accounts/{userId}",
                                "/accounts/*",
                                "/accounts/login")
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(authConverter())))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }

}