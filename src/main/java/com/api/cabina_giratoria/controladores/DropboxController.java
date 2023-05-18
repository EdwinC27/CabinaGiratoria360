package com.api.cabina_giratoria.controladores;

import com.dropbox.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/dropbox")
public class DropboxController {
    @Autowired
    private DbxAppInfo appInfo;

    @Autowired
    private DbxRequestConfig requestConfig;

    @GetMapping("/auth")
    public String authRedirect(HttpSession session) {
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
        String redirectUrl = "http://localhost:8080/dropbox/auth"; // URL de redirección después de la autenticación
        DbxSessionStore sessionStore = new DbxStandardSessionStore(session, "request");
        String authorizeUrl = webAuth.authorize(DbxWebAuth.newRequestBuilder()
                .withRedirectUri(redirectUrl, sessionStore)
                .build());
        return "redirect:" + authorizeUrl;
    }
}
