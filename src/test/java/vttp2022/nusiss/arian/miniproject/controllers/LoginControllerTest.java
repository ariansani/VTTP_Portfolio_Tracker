package vttp2022.nusiss.arian.miniproject.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;

import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.services.LoginService;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Autowired
    MockHttpSession session;

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private LoginController loginCtrl;

    @Mock
    private LoginService loginSvc;

    @Mock
    private PortfolioService portSvc;

    @Test
    void contextLoginControllerLoads() throws Exception {
        assertThat(loginCtrl).isNotNull();
    }

    @Test
    void contextLoginServiceLoads() throws Exception {
        assertThat(loginSvc).isNotNull();
    }

    @Test
    void contextPortfolioServiceLoads() throws Exception {
        assertThat(portSvc).isNotNull();
    }

    @Test
    void testAuthenticateLogin() {
  
    }

    @Test
    void testCreateAccount() {

    }

    @Test
    void testGetLogout() {
        
        ModelAndView mav = loginCtrl.getLogout(session);
        assertEquals( "index", mav.getViewName());
    }

    @Test
    void testLoadIndex() {
        ModelAndView mav = loginCtrl.loadIndex();
        assertEquals( "index", mav.getViewName());
    }

    @Test
    void testLoadSignUp() {
        ModelAndView mav = loginCtrl.loadSignUp();
        assertEquals( "signup", mav.getViewName());
    }

    @Test
    void testManagePortfolio() {
       
    }

    @Test
    void testCreate(){
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("username", "arian");
        form.add("password","arian");
        User user = loginCtrl.create(form);
        assertEquals("arian", user.getUsername());

    }

}
