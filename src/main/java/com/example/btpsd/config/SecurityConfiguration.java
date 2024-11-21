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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })
@EnableWebSecurity
@CrossOrigin(origins = "*", allowedHeaders = "*",maxAge = 3600L)
public class SecurityConfiguration {

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/iasusers");
//        return (web) -> web.ignoring().requestMatchers("/iasusers", "/formulas/*", "/formulas", "/linetypes/*", "/linetypes", "/materialgroups/*", "/materialgroups", "/modelspecs", "/modelspecs/*",
//                "/modelspecdetails/*", "/modelspecdetails", "/personnelnumbers/*", "/personnelnumbers", "/servicenumbers/*", "/servicenumbers", "/servicetypes/*", "/servicetypes",
//                "/invoices/*", "/invoices", "/mainitems/*/*/*/*/*", "/mainitems/*/*", "/mainitems","/mainitems/*", "/subitems/*", "/subitems", "/currencies/*", "/currencies", "/salesorder", "/salesorder/*", "/salesorderitems", "/salesorderitems/*," ,
//                "/salesorderpricing", "/salesorderpricing/*", "/executionordersub", "/executionordersub/*", "/executionordermain/*/*", "/executionordermain/*/*/*", "/executionordermain", "/executionordermain/*","/salesordercloud", "/salesordercloud/*",
//                "/salesorderpostcloud", "/salesorderpostcloud/*", "/serviceinvoice/*/*/*/*/*", "/serviceinvoice/*/*", "/serviceinvoice", "/serviceinvoice/*", "/salesorderitemcloud/*", "/salesorderitemcloud",
//                "/salesorderitemscloud/*", "/salesorderitemscloud", "/salesorderpricingcloud/*/*", "/salesorderpricingcloud", "/salesquotationcloud", "/salesquotationcloud/*",
//                "/salesquotationpostcloud/*", "/salesquotationpostcloud", "/salesquotationitemcloud/*", "/salesquotationitemcloud", "/salesquotationitemscloud", "/salesquotationitemscloud/*",
//                "/salesquotationpricingcloud/*/*", "/salesquotationpricingcloud", "/debitmemocloud/*" , "/debitmemocloud", "/debitmemopostcloud/*", "/debitmemopostcloud",
//                "/debitmemoitemscloud", "/debitmemoitemscloud/*","/salesorderallpricingcloud", "/salesorderallpricingcloud/*", "/salesorderitempricingcloudpost/*/*",
//                "/salesquotationricingcloudpatch/*/*/*/*", "/productcloud", "/productdescriptioncloud", "/businesspartner", "/salesquotationitem/*/*",
//                "/salesorderitem/*/*", "/debitmemoitems/*/*", "/allproductscloud", "/quantities", "/total", "/totalheader", "/totalsrv", "/totalheadersrv",
//                "/fetchSalesOrderDetails");
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
                                "/localhost/**"
                        ).permitAll()
                        .requestMatchers( "/measurements/*",
                                "/api/v1/auth/**")
                        .hasAuthority("XSUAA-User")
                        .requestMatchers("/sayHello").hasAuthority("$XSAPPNAME.User")
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/**").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(authConverter())))
                .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }
}