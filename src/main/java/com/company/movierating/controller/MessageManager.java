package com.company.movierating.controller;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {
    private static final String BUNDLE_NAME = "app_messages";
    private final ResourceBundle resourceBundle;

    public MessageManager(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
    }

    public String localize(String msg) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("#(.+?)#");
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            matcher.appendReplacement(sb, getMessage(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}
