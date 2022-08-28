package com.tac.springweather.service;

import com.tac.springweather.exception.ForbiddenOperationException;
import com.tac.springweather.helper.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {
    private static final String CLIENT_ID = "test_client_id";
    private static final String KEY = "test_eky";

    private static final String BRISBANE = "Brisbane";
    private static final String CANBERRA = "Canberra";
    private static final String DARWIN = "Darwin";
    private static final String HOBART = "Hobart";
    private static final String MELBOURNE = "Melbourne";
    private static final String PERTH = "Perth";
    private static final String SYDNEY = "Sydney";

    @Mock
    private AuthorizationService authorizationService;
    @InjectMocks
    private LocationService locationService;

    @Test
    public void testGetAusCities_Success() {
        Assertions.assertEquals(7,
                locationService.getAusCities(CLIENT_ID, KEY).size());
        Assertions.assertEquals(BRISBANE,
                locationService.getAusCities(CLIENT_ID, KEY).get(0).getCity());
        Assertions.assertEquals(CANBERRA,
                locationService.getAusCities(CLIENT_ID, KEY).get(1).getCity());
        Assertions.assertEquals(DARWIN,
                locationService.getAusCities(CLIENT_ID, KEY).get(2).getCity());
        Assertions.assertEquals(HOBART,
                locationService.getAusCities(CLIENT_ID, KEY).get(3).getCity());
        Assertions.assertEquals(MELBOURNE,
                locationService.getAusCities(CLIENT_ID, KEY).get(4).getCity());
        Assertions.assertEquals(PERTH,
                locationService.getAusCities(CLIENT_ID, KEY).get(5).getCity());
        Assertions.assertEquals(SYDNEY,
                locationService.getAusCities(CLIENT_ID, KEY).get(6).getCity());
    }

    @Test
    public void testGetAusCities_Failed() {
        Mockito.when(authorizationService.checkCredentials(CLIENT_ID, KEY))
                .thenThrow(new ForbiddenOperationException("Error"));
        Assertions.assertThrows(ForbiddenOperationException.class,
                () -> locationService.getAusCities(CLIENT_ID, KEY));
    }

}
