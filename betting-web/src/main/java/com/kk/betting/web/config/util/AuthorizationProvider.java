package com.kk.betting.web.config.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KK on 2017-07-19.
 */
@Component
@RequestScope
public class AuthorizationProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private HttpServletRequest request;

    @Autowired
    public AuthorizationProvider(HttpServletRequest request) {
        this.request = request;
    }

    public HttpHeaders getAuthorizationHeader() {
        return new HttpHeaders() {{
            set(AUTHORIZATION_HEADER, request.getHeader(AUTHORIZATION_HEADER));
        }};
    }

}
