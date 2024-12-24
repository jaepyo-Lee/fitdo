package com.jaejoo.fitdoweb.user.web.res;

import lombok.Data;

@Data
public class DeepLinkResponse {
    private String deepLink;

    public DeepLinkResponse(String deepLink) {
        this.deepLink = deepLink;
    }
}
