package com.tac.springweather.helper;

import com.tac.springweather.config.ApplicationConfiguration;
import com.tac.springweather.exception.ForbiddenOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {
    private static final String CLIENT_ID = "40262456";
    private static final String CLIENT_SECRET = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9";

    @Mock
    private ApplicationConfiguration configuration;
    @InjectMocks
    private AuthorizationService authorizationService;

    @Test
    public void testCheckCredentials_Success() {
        Mockito.when(configuration.getClientId()).thenReturn(CLIENT_ID);
        Mockito.when(configuration.getClientSecret()).thenReturn(CLIENT_SECRET);
        Assertions.assertEquals("success", authorizationService.checkCredentials(CLIENT_ID, CLIENT_SECRET));
    }

    @Test
    public void testCheckCredentials_Failed() {
        Mockito.when(configuration.getClientId()).thenReturn(CLIENT_ID);
        Assertions.assertThrows(ForbiddenOperationException.class,
                () -> authorizationService.checkCredentials("CLIENT_ID", "CLIENT_SECRET"));
    }
}
