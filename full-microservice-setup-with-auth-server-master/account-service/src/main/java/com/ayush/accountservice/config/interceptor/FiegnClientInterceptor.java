package com.ayush.accountservice.config.interceptor;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.TokenType;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class FiegnClientInterceptor  implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!= null && authentication.getDetails() instanceof OAuth2AuthenticationDetails){
            OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
            requestTemplate.header(
                    AUTHORIZATION_HEADER,
                    String.format("%s %s", TOKEN_TYPE,authenticationDetails.getTokenValue())
            );
        }
    }
}
