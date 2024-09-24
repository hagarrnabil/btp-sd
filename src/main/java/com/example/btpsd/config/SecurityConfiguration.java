package com.example.btpsd.config;
import com.sap.cloud.security.spring.config.IdentityServicesPropertySourceFactory;
import com.sap.cloud.security.spring.token.authentication.AuthenticationToken;
import com.sap.cloud.security.token.TokenClaims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@PropertySource(factory = IdentityServicesPropertySourceFactory.class, ignoreResourceNotFound = true, value = { "" })
public class SecurityConfiguration {

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> authConverter() {
        return new MyCustomIasTokenAuthenticationConverter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/sayHello").hasAuthority("spring-security-hybrid-usage.Read")
                        .requestMatchers("/comp/sayHello").hasAuthority("spring-security-hybrid-usage.Read")
                        .requestMatchers("/*").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new MyCustomHybridTokenAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n" +
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAx2ZOcKplHEIvBx1j0dNK\n" +
                "9J/3E8uKrWFnJRBsdrYvSI6gbV2j2vakAobNxGEz0rXVn+Ljz+2XMgFxlv8in7co\n" +
                "W7wzLNkOZDXkI6m27hxAdFtihDzoVGRGDhLkxMytvNcz/uVwoa+RSviA3kJ56s7D\n" +
                "5h2kXsVEUQVHURvfku6MwDO0+ZnEuY174QY6NiwCn6DiMDyQWFtPwHT3l2b9uQaL\n" +
                "h2szCC21Stze788prD142LePX1CdhRLkwrOZOoAlMNrEdlqqNLadO5WX2m71VICI\n" +
                "Gjp6iQ4qFHPPJ8tD0d7cRSFnjsBXcbGfzFEhDoYTAF37upXQhhsQywP37AD2q0E9\n" +
                "LqRwaMkYOuDEEY5Q/Ph9EVeu8zmWbNRQCoqwJ4CbTJ2CMgYCGPj2cgc+lKkPXQOI\n" +
                "AYN8yh2MaLDPybV0P+dBMI9z2jeeT5ODRsgyfa+7ucLQnOXrR8fRUgULbmtLfHNq\n" +
                "GZOmhD1cN0MI+S6KxqvPDJmqHOn+nGAQ7uXnW9mjXlNwn3xqZgoXP7+sMRoldDuw\n" +
                "GcCIAjRF7DOvNkT0cgyYIJD98tCHMWVm/Q6AxKsEwnvxp55g+wNaVaG20qRyDssR\n" +
                "2DTCQW4cpkgZpWk6jKHZh6tsgzcm0EOB2h0LQiN/hM9sF/BX2cd8ihXfxgWuEkbL\n" +
                "XWjQAXI68eIOaUJ1LqsoEJsCAwEAAQ==\n" +
                "-----END PUBLIC KEY-----";

        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");

        try {
            byte[] encoded = java.util.Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
            return NimbusJwtDecoder.withPublicKey(publicKey).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWT decoder", e);
        }
    }

    class MyCustomHybridTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
        public AbstractAuthenticationToken convert(Jwt jwt) {
            // Implementation for hybrid use case
            if (jwt.hasClaim(TokenClaims.XSUAA.EXTERNAL_ATTRIBUTE)) {
                return authConverter().convert(jwt);
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

    // The MyCustomIasTokenAuthenticationConverter class can be defined similarly if needed
    class MyCustomIasTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
        public AbstractAuthenticationToken convert(Jwt jwt) {
            final List<String> groups = jwt.getClaimAsStringList(TokenClaims.GROUPS);
            final List<GrantedAuthority> groupAuthorities = groups == null ? Collections.emptyList()
                    : groups.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new AuthenticationToken(jwt, groupAuthorities);
        }
    }
}
