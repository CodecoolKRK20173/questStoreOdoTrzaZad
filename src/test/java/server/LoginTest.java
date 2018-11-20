package server;

import controllers.dao.LoginAccesDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    @Mock
    private LoginAccesDAO loginAccesDAO;
    @Mock
    private FormDataParser formDataParser;
    @Mock
    private CookieHelper cookieHelper;


}