package vn.atdigital.cameraservice.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public abstract class MessageUtils {
    private final static String BASE_NAME = "messages";

    private static String getMessage(String code, Locale locale, Object... args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
        String message;
        try {
            message = resourceBundle.getString(code);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            message = code;
        }
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

    public static String getMessage(String code) {
        return getMessage(code,
                LocaleContextHolder.getLocale()
        );
    }

    public static String getMessage(String code, Object... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }
}

