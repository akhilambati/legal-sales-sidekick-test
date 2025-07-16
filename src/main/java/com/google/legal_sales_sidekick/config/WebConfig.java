package com.google.legal_sales_sidekick.config;

import com.google.legal_sales_sidekick.app_filters.AuthorizationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.google.legal_sales_sidekick.constants.constants.UNAUTHORIZED_PATHS;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationHandler authorizationHandler;

    @Autowired
    public WebConfig(AuthorizationHandler authorizationHandler) {
        this.authorizationHandler = authorizationHandler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationHandler).addPathPatterns("/**").excludePathPatterns(UNAUTHORIZED_PATHS);
    }
}
