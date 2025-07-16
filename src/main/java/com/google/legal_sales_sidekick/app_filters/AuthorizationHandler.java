package com.google.legal_sales_sidekick.app_filters;

import com.google.legal_sales_sidekick.app_filters.annotations.APIAccess;
import com.google.legal_sales_sidekick.constants.enums.AccessType;
import com.google.legal_sales_sidekick.exception.AuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.google.legal_sales_sidekick.constants.constants.AUTHORIZATION_EXCEPTION;

@Component
public class AuthorizationHandler implements HandlerInterceptor {

    //replace this with db call
    private static final Map<String, Set<AccessType>> userAccessTypes = new HashMap<>();

    @PostConstruct
    public void init() {
        userAccessTypes.put("akhil.ambati1@gmail.com", Set.of(AccessType.DELETE, AccessType.UPDATE, AccessType.READ, AccessType.WRITE));
        userAccessTypes.put("akhil.ambati9030@gmail.com", Set.of(AccessType.READ));
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        APIAccess accessLevels = handlerMethod.getMethodAnnotation(APIAccess.class);
        if (Objects.nonNull(accessLevels)) {
            AccessType[] requiredAccessTypes = accessLevels.accessTypes();
            String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            Set<AccessType> userAppAuthorizations = userAccessTypes.get(principal);
            if (CollectionUtils.isEmpty(userAppAuthorizations)) {
                throw new AuthenticationException(AUTHORIZATION_EXCEPTION.formatted(principal));
            }
            for (AccessType requiredAccessType : requiredAccessTypes) {
                if (!userAppAuthorizations.contains(requiredAccessType)) {
                    return false;
                }
            }
        }
        return true;
    }
}
