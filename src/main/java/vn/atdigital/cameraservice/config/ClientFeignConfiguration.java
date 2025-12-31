package vn.atdigital.cameraservice.config;

import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

@Configuration
@ConditionalOnClass(Feign.class)
@EnableFeignClients(basePackages = "vn.atdigital.cameraservice.feignclient")
public class ClientFeignConfiguration {

    /**
     * Interceptor thêm header Accept-Language
     */
    @Bean
    RequestInterceptor languageRequestInterceptor() {
        return requestTemplate ->
                requestTemplate.header("Accept-Language", LocaleContextHolder.getLocale().toLanguageTag());
    }
}
