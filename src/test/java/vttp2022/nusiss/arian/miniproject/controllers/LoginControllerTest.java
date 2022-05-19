package vttp2022.nusiss.arian.miniproject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.nusiss.arian.miniproject.services.LoginService;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class LoginControllerTest {

    @Autowired
	private LoginController loginCtrl;   
    
    @Autowired
    private LoginService loginSvc;

    @Autowired
    private PortfolioService portSvc;


    @Test
    void contextLoginControllerLoads() throws Exception{
        assertThat(loginCtrl).isNotNull();
    }

    @Test
    void contextLoginServiceLoads() throws Exception{
        assertThat(loginSvc).isNotNull();
    }

    @Test
    void contextPortfolioServiceLoads() throws Exception{
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

    }

    @Test
    void testLoadIndex() {

    }

    @Test
    void testLoadSignUp() {

    }

    @Test
    void testManagePortfolio() {

    }
}
