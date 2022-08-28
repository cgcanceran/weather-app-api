package com.tac.springweather.helper;

import com.tac.springweather.config.ApplicationConfiguration;
import com.tac.springweather.exception.ForbiddenOperationException;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    public static final String SUCCESS = "success";
    private final ApplicationConfiguration applicationConfiguration;

    public String checkCredentials(String clientId, String key) {
        if (StringUtil.isNullOrEmpty(clientId) && StringUtil.isNullOrEmpty(key)
            || !(applicationConfiguration.getClientId().equalsIgnoreCase(clientId)
                && applicationConfiguration.getClientSecret().equalsIgnoreCase(key))) {
            throw new ForbiddenOperationException("Invalid Credentials");
        }
        return SUCCESS;
    }
}
