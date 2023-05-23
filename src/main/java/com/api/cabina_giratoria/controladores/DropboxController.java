package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.TokenDropBox;
import com.dropbox.core.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/dropbox")
public class DropboxController {
    @Autowired
    private DbxAppInfo appInfo;

    @Autowired
    private DbxRequestConfig requestConfig;

    @Autowired
    private TokenDropBox tokenDropBox;

    @Value("${dropbox.urlRedirect}")
    private String urlRedirect;

    public static String accessToken;

    @GetMapping("/auth")
    public JSONObject authRedirect(HttpSession session) {
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
        String redirectUrl = urlRedirect; // URL de redirección después de la autenticación
        DbxSessionStore sessionStore = new DbxStandardSessionStore(session, "request");
        String authorizeUrl = webAuth.authorize(DbxWebAuth.newRequestBuilder()
                .withRedirectUri(redirectUrl, sessionStore)
                .build());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("URL", authorizeUrl);
        return jsonObject;
    }

    @GetMapping("/getAccessToken")
    public String getAccessToken(@RequestParam("code") String authorizationCode) {
        accessToken = tokenDropBox.getAccessToken(authorizationCode);
        return "Access Token: " + accessToken;
    }
}
