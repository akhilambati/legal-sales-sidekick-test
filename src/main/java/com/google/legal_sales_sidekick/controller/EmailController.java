package com.google.legal_sales_sidekick.controller;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static com.google.legal_sales_sidekick.constants.constants.TOKEN;

@RestController
public class EmailController {

    @Value("${google.client_id}")
    private String clientId;
    @Value("${google.client_secret}")
    private String clientSecret;
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final Collection<String> SCOPES = List.of("https://www.googleapis.com/auth/gmail.send");


    @GetMapping("/authorize")
    public void authorize(HttpServletResponse response) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                clientId,
                clientSecret,
                SCOPES
        ).setAccessType("offline").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public String oauthCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException, MessagingException {
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                clientId,
                clientSecret,
                code,
                REDIRECT_URI
        ).execute();
        Credential credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod()).build();
        credential.setAccessToken(tokenResponse.getAccessToken())
                .setRefreshToken(tokenResponse.getRefreshToken());
        Cookie cookie = new Cookie(TOKEN, tokenResponse.getIdToken());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        String setCookieHeaders = response.getHeader("Set-Cookie");
        if (!StringUtils.isEmpty(setCookieHeaders)) {
            response.setHeader("Set-Cookie", setCookieHeaders + "; SameSite=Lax");
        }
        Gmail gmailService = new Gmail.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName("Bulk email").build();

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("akhil.ambati1@gmail.com"));
        mimeMessage.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress("akhil.ambati9030@gmail.com"));
        mimeMessage.setSubject("Test");
        mimeMessage.setText("test....");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);

        com.google.api.services.gmail.model.Message message = new Message();
        message.setRaw(Base64.getUrlEncoder().encodeToString(buffer.toByteArray()));

        gmailService.users().messages().send("me", message).execute();
        return "Access Granted. You can now send emails!";
    }

}
