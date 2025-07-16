package com.google.legal_sales_sidekick.controller;

import com.google.legal_sales_sidekick.app_filters.annotations.APIAccess;
import com.google.legal_sales_sidekick.constants.enums.AccessType;
import com.google.legal_sales_sidekick.dto.Response;
import com.google.legal_sales_sidekick.model.User;
import com.google.legal_sales_sidekick.utils.HttpUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.google.legal_sales_sidekick.constants.constants.SUCCESS;
import static com.google.legal_sales_sidekick.constants.constants.TOKEN;

@RequestMapping("/user")
@RestController
public class UserController {

    @Value("${google.client_id}")
    public String clientId;

    @Value("${google.client_secret}")
    public String clientSecret;

    @GetMapping("/login")
    public String addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        //move to service at actual impl
        String code = request.getParameter("code");
        Map<String, String> accessTokenResponse = HttpUtils.getPostResponse("https://oauth2.googleapis.com/token", null, Pair.of("code", code), Pair.of("client_id", clientId),
                Pair.of("client_secret", clientSecret), Pair.of("grant_type", "authorization_code"), Pair.of("redirect_uri", "http://localhost:8080/user/login"));
        Cookie cookie = new Cookie(TOKEN, accessTokenResponse.get("id_token"));
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        String setCookieHeaders = response.getHeader("Set-Cookie");
        if (!StringUtils.isEmpty(setCookieHeaders)) {
            response.setHeader("Set-Cookie", setCookieHeaders + "; SameSite=Lax");
        }
        return SUCCESS;
    }

    @APIAccess(accessTypes = {AccessType.READ, AccessType.WRITE})
    @GetMapping("/getUserInfo")
    public ResponseEntity<Response> getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Response response = new Response(email, null, SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
