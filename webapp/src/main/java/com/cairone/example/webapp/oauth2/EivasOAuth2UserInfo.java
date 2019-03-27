package com.cairone.example.webapp.oauth2;

import java.util.Map;

public class EivasOAuth2UserInfo extends OAuth2UserInfo {

    public EivasOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("username");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
