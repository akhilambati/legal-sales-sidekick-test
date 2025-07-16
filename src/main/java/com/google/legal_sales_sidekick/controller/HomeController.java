package com.google.legal_sales_sidekick.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <html lang='en'>
                <head>
                    <meta charset='UTF-8'>
                    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                    <title>Legal Sales</title>
                    <style>
                        .button-link {
                            display: inline-block;
                            padding: 10px 20px;
                            background-color: #007bff;
                            color: white;
                            text-align: center;
                            text-decoration: none;
                            border-radius: 5px;
                            cursor: pointer;
                            font-size: 16px;
                            border: none;
                            transition: background-color 0.3s ease;
                        }
                        .button-link:hover {
                            background-color: #0056b3;
                        }
                    </style>
                </head>
                <body>
                    <a href='https://accounts.google.com/o/oauth2/v2/auth?scope=openid profile email &access_type=offline&include_granted_scopes=true&response_type=code&state=state_parameter_passthrough_value&redirect_uri=http://localhost:8080/user/login&client_id=163163550532-fav9ts599hreo8kgkj4gtv23jbqv2q43.apps.googleusercontent.com' class='button-link'>Click here to authenticate</a>
                </body>
                </html>""";
    }
}
