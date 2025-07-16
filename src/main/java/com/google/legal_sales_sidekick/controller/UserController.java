package com.google.legal_sales_sidekick.controller;

import com.google.legal_sales_sidekick.app_filters.annotations.APIAccess;
import com.google.legal_sales_sidekick.constants.enums.AccessType;
import com.google.legal_sales_sidekick.dto.Response;
import com.google.legal_sales_sidekick.model.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.google.legal_sales_sidekick.constants.constants.SUCCESS;

@RequestMapping("/user")
@RestController
public class UserController {


    @PostMapping("/login")
    public String addUser(@PathVariable String email) {
        return SUCCESS;
    }

    @APIAccess(accessTypes = {AccessType.READ, AccessType.WRITE})
    @GetMapping("/getUserInfo")
    public ResponseEntity<Response> getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Response response = new Response(email, null, SUCCESS, 200);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }
}
