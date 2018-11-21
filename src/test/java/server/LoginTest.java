package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import controllers.dao.LoginAccesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.concurrentlinkedhashmap.Weighers.set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginTest {
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;
    private CookieHelper cookieHelper;
    private HttpExchange httpExchange;
    private Headers headers;

    @BeforeEach
    public void initialize() throws IOException{
        loginAccesDAO = mock(LoginAccesDAO.class);
        formDataParser = mock(FormDataParser.class);
        cookieHelper = mock(CookieHelper.class);
        httpExchange = mock(HttpExchange.class);
        headers = mock(Headers.class);
        when(httpExchange.getRequestMethod()).thenReturn("POST");
        Map<String, String> inputs = new HashMap();
        inputs.put("email", "xxx");
        inputs.put("pass", "xxx");
        when(formDataParser.getData(httpExchange)).thenReturn(inputs);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        OutputStream os = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(os);
    }

    @Test
    public void loginHandlerRedirectsToCodecoolerPage() throws IOException{
        Login login = new Login(loginAccesDAO, formDataParser, cookieHelper);
        List<Integer> loginData = new ArrayList<>();
        loginData.add(1);
        when(loginAccesDAO.readLoginData(anyString(), anyString())).thenReturn(loginData);

        login.handle(httpExchange);

        verify(headers).set("Location", "/codecoolerJavaPages/CodecoolerIndex");
    }

    @Test
    public void loginHandlerRedirectsToMentorPage() throws IOException{
        Login login = new Login(loginAccesDAO, formDataParser, cookieHelper);
        List<Integer> loginData = new ArrayList<>();
        loginData.add(2);
        when(loginAccesDAO.readLoginData(anyString(), anyString())).thenReturn(loginData);

        login.handle(httpExchange);

        verify(headers).set("Location", "/mentorJavaPages/MentorWelcomePage");
    }

    @Test
    public void loginHandlerRedirectsToAdminPage() throws IOException{
        Login login = new Login(loginAccesDAO, formDataParser, cookieHelper);
        List<Integer> loginData = new ArrayList<>();
        loginData.add(3);
        when(loginAccesDAO.readLoginData(anyString(), anyString())).thenReturn(loginData);

        login.handle(httpExchange);

        verify(headers).set("Location", "/adminJavaPages/GreetAdmin");
    }

    @Test
    public void loginHandlerRedirectsToTheSamePageIfUserProvidesInvalidInput() throws IOException{
        Login login = new Login(loginAccesDAO, formDataParser, cookieHelper);
        List<Integer> loginData = new ArrayList<>();
        when(loginAccesDAO.readLoginData(anyString(), anyString())).thenReturn(loginData);

        login.handle(httpExchange);

        verify(headers, never()).set(eq("Location"), anyString());
    }

}