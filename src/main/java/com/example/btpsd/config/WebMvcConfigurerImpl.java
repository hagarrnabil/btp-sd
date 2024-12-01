package com.example.btpsd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {
    // @Bean
    // public LocaleResolver localeResolver()
    // {
    //     final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    //     localeResolver.setDefaultLocale(new Locale("en", "US"));
    //     return localeResolver;
    // }

    @Bean
    public LocaleChangeInterceptor LocaleChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(LocaleChangeInterceptor());
    }

}
