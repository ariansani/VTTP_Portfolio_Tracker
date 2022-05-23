package vttp2022.nusiss.arian.miniproject.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.exceptions.UserException;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.repositories.LoginRepository;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;
import vttp2022.nusiss.arian.miniproject.services.LoginService;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private LoginController loginCtrl;

    @MockBean
    private LoginService loginSvc;

    @MockBean
    private LoginRepository loginRepo;

    @MockBean
    private PortfolioService portSvc;

    @MockBean
    private PortfolioRepository portRepo;

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
    void testAuthenticateLogin() throws PortfolioException, UnsupportedEncodingException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.authenticate(any()))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(1))
                .thenReturn("abc123");

        RequestBuilder req = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();

        assertEquals("/protected/dashboard", resp.getRedirectedUrl());
    }

    @Test
    void testAuthenticateLoginPortfolioException() throws PortfolioException, UnsupportedEncodingException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.authenticate(any()))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(any()))
                .thenThrow(PortfolioException.class);

        assertThrows(PortfolioException.class,
                () -> portSvc.findPortfolioByUserId(any()));

        RequestBuilder req = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals(401, resp.getStatus());
    }

    @Test
    void testAuthenticateLoginInvalidAuth() throws PortfolioException, UnsupportedEncodingException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.authenticate(authUser))
                .thenReturn(authUser);

        RequestBuilder req = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals(401, resp.getStatus());

    }

    @Test
    void testCreateAccount() throws UserException, PortfolioException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.insertUser(any(), any()))
                .thenReturn(true);

        Mockito.when(loginSvc.authenticate(any()))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(1))
                .thenReturn("abc123");

        RequestBuilder req = MockMvcRequestBuilders.post("/createAccount")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("/protected/dashboard", resp.getRedirectedUrl());

    }

    @Test
    void testCreateAccountFailInsert() throws UserException, PortfolioException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.insertUser(any(), any()))
                .thenReturn(false);

        Mockito.when(loginSvc.authenticate(any()))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(1))
                .thenReturn("abc123");

        RequestBuilder req = MockMvcRequestBuilders.post("/createAccount")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("error", result.getModelAndView().getViewName());

    }

    @Test
    void testCreateAccountFailInsertUserExcept() throws UserException, PortfolioException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.insertUser(any(), any()))
                .thenThrow(UserException.class);
        assertThrows(UserException.class,
                () -> loginSvc.insertUser(any(), any()));

        Mockito.when(loginSvc.authenticate(any()))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(1))
                .thenReturn("abc123");

        RequestBuilder req = MockMvcRequestBuilders.post("/createAccount")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("error", result.getModelAndView().getViewName());

    }

    @Test
    void testCreateAccountFailInsertUserPortfolioExcept() throws UserException, PortfolioException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.insertUser(any(), any()))
                .thenReturn(true);

        Mockito.when(loginSvc.authenticate(any()))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(any()))
                .thenThrow(PortfolioException.class);
        assertThrows(PortfolioException.class, () -> portSvc.findPortfolioByUserId(any()));

        RequestBuilder req = MockMvcRequestBuilders.post("/createAccount")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("error", result.getModelAndView().getViewName());

    }

    @Test
    void testCreateAccountFailAuth() throws UserException, PortfolioException {
        MockHttpSession session = new MockHttpSession();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("username", "arian");
        form.add("password", "arian");

        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        Mockito.when(loginSvc.insertUser(any(), any()))
                .thenReturn(true);

        Mockito.when(loginSvc.authenticate(authUser))
                .thenReturn(authUser);

        Mockito.when(portSvc.findPortfolioByUserId(1))
                .thenReturn("abc123");

        RequestBuilder req = MockMvcRequestBuilders.post("/createAccount")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(form)
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("error", result.getModelAndView().getViewName());

    }

    @Test
    void testGetLogout() {
        MockHttpSession session = new MockHttpSession();
        ModelAndView mav = loginCtrl.getLogout(session);
        assertEquals("index", mav.getViewName());
    }

    @Test
    void testLoadIndex() {
        ModelAndView mav = loginCtrl.loadIndex();
        assertEquals("index", mav.getViewName());
    }

    @Test
    void testLoadSignUp() {
        ModelAndView mav = loginCtrl.loadSignUp();
        assertEquals("signup", mav.getViewName());
    }

    @Test
    void testManagePortfolio() {
        MockHttpSession session = new MockHttpSession();
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        RequestBuilder req = MockMvcRequestBuilders.get("/manage")
                .session(session)
                .sessionAttr("authUserSess", authUser);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("/protected/manage/portfolio", resp.getRedirectedUrl());

    }

    @Test
    void testManagePortfolioInvalidSess() {
        MockHttpSession session = new MockHttpSession();

        RequestBuilder req = MockMvcRequestBuilders.get("/manage")
                .session(session);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals("error", result.getModelAndView().getViewName());

    }

    @Test
    void testCreate() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("username", "arian");
        form.add("password", "arian");
        User user = loginCtrl.create(form);
        assertEquals("arian", user.getUsername());

    }

}
