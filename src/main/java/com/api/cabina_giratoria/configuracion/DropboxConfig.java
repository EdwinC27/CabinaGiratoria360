package com.api.cabina_giratoria.configuracion;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxConfig {
    @Value("${dropbox.appKey}")
    private String appKey;

    @Value("${dropbox.appSecret}")
    private String appSecret;


    @Bean
    public DbxAppInfo appInfo() {
        return new DbxAppInfo(appKey, appSecret);
    }

    @Bean
    public DbxRequestConfig requestConfig() {
        return DbxRequestConfig.newBuilder("CabinaGiratoria").build();
    }
}
