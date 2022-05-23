package vttp2022.nusiss.arian.miniproject.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;
import vttp2022.nusiss.arian.miniproject.models.Stock;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProtectedControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PortfolioService portSvc;

    @MockBean
    private PortfolioRepository portRepo;

    @Test
    void testManagePortfolio() throws PortfolioException {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");
        authUser.setPortfolioId("abc123");

        MockHttpSession session = new MockHttpSession();

        List<Holdings> holdings = new LinkedList();
        Holdings holding = new Holdings();
        holding.setCurrentPrice(1.00);
        holding.setQuantity(1);
        holding.setCostBasis(1.00f);
        holding.setTotalValue(1.00);
        holding.setProfitLoss(1.00);

        holdings.add(holding);
        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenReturn(holdings);

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/manage/portfolio")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "portfolio")
                .session(session)
                .sessionAttr("authUserSess", authUser);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();

        assertEquals("portfolio", result.getModelAndView().getViewName());

    }

    @Test
    void testManagePortfolioInvalidAuth() {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");

        MockHttpSession session = new MockHttpSession();

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/manage/portfolio")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "portfolio")
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
    void testManagePortfolioPortfolioExcept() throws PortfolioException {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");
        authUser.setPortfolioId("abc123");

        MockHttpSession session = new MockHttpSession();

        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenThrow(PortfolioException.class);
        assertThrows(PortfolioException.class, () -> portSvc.findHoldingsByPortfolioId(any()));

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/manage/portfolio")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "portfolio")
                .session(session)
                .sessionAttr("authUserSess", authUser);

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
    void testPost() throws PortfolioException {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");
        authUser.setPortfolioId("abc123");

        MockHttpSession session = new MockHttpSession();

        List<Holdings> holdings = new LinkedList();
        Holdings holding = new Holdings();
        holding.setCurrentPrice(1.00);
        holding.setQuantity(1);
        holding.setCostBasis(1.00f);
        holding.setTotalValue(1.00);
        holding.setProfitLoss(1.00);
        holding.setSymbol("a");
        holding.setId(1);

        holdings.add(holding);

        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenReturn(holdings);

        Mockito.when(portSvc.retrieveHolding(any(), any()))
                .thenReturn(Optional.of(holding));

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "dashboard")
                .session(session)
                .sessionAttr("authUserSess", authUser);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();

        assertEquals("dashboard", result.getModelAndView().getViewName());

    }

    @Test
    void testPostInvalidAuth() throws PortfolioException {
        User authUser = new User();

        MockHttpSession session = new MockHttpSession();

        List<Holdings> holdings = new LinkedList();
        Holdings holding = new Holdings();
        holding.setCurrentPrice(1.00);
        holding.setQuantity(1);
        holding.setCostBasis(1.00f);
        holding.setTotalValue(1.00);
        holding.setProfitLoss(1.00);
        holding.setSymbol("a");
        holding.setId(1);

        holdings.add(holding);

        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenReturn(holdings);

        Mockito.when(portSvc.retrieveHolding(any(), any()))
                .thenReturn(Optional.of(holding));

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "dashboard")
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
    void testPostPortfolioExcept() throws PortfolioException {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");
        authUser.setPortfolioId("abc123");

        MockHttpSession session = new MockHttpSession();

        List<Holdings> holdings = new LinkedList();
        Holdings holding = new Holdings();
        holding.setCurrentPrice(1.00);
        holding.setQuantity(1);
        holding.setCostBasis(1.00f);
        holding.setTotalValue(1.00);
        holding.setProfitLoss(1.00);
        holding.setSymbol("a");
        holding.setId(1);

        holdings.add(holding);

        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenThrow(PortfolioException.class);
        assertThrows(PortfolioException.class, () -> portSvc.findHoldingsByPortfolioId(any()));

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "dashboard")
                .session(session)
                .sessionAttr("authUserSess", authUser);

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
    void testPostOptHoldingEmpty() throws PortfolioException {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");
        authUser.setPortfolioId("abc123");

        MockHttpSession session = new MockHttpSession();

        List<Holdings> holdings = new LinkedList();
        Holdings holding = new Holdings();
        holding.setCurrentPrice(1.00);
        holding.setQuantity(1);
        holding.setCostBasis(1.00f);
        holding.setTotalValue(1.00);
        holding.setProfitLoss(1.00);
        holding.setSymbol("a");
        holding.setId(1);

        holdings.add(holding);
        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenReturn(holdings);

        Mockito.when(portSvc.retrieveHolding(any(), any()))
                .thenReturn(Optional.empty());

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "dashboard")
                .session(session)
                .sessionAttr("authUserSess", authUser);

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
    void testPostPortfolioExceptionUpdateCurrPrice() throws PortfolioException {
        User authUser = new User();
        authUser.setUserId(1);
        authUser.setUsername("arian");
        authUser.setPassword("arian");
        authUser.setPortfolioId("abc123");

        MockHttpSession session = new MockHttpSession();

        List<Holdings> holdings = new LinkedList();
        Holdings holding = new Holdings();
        holding.setCurrentPrice(1.00);
        holding.setQuantity(1);
        holding.setCostBasis(1.00f);
        holding.setTotalValue(1.00);
        holding.setProfitLoss(1.00);
        holding.setSymbol("a");
        holding.setId(1);

        holdings.add(holding);

        Mockito.when(portSvc.findHoldingsByPortfolioId(any()))
                .thenReturn(holdings);

        Mockito.when(portSvc.retrieveHolding(any(), any()))
                .thenReturn(Optional.of(holding));
        Mockito.doThrow(PortfolioException.class).when(portSvc).updateCurrPrice(any(),any());
 
        
        RequestBuilder req = MockMvcRequestBuilders.get("/protected/dashboard")
                .contentType(MediaType.TEXT_HTML)
                .param("view", "dashboard")
                .session(session)
                .sessionAttr("authUserSess", authUser);

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
    void testSearchStockFinancials() throws PortfolioException {

        Stock stock = new Stock();
        Mockito.when(portSvc.retrieveStockFinancialsByTicker("AAPL"))
                .thenReturn(Optional.of(stock));

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stock", "AAPL");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();

        assertEquals("stockinfo", result.getModelAndView().getViewName());

    }

    @Test
    void testSearchStockFinancialsOptEmpty() throws PortfolioException {

        Stock stock = new Stock();
        Mockito.when(portSvc.retrieveStockFinancialsByTicker("AAPL"))
                .thenReturn(Optional.empty());

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stock", "AAPL");

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
    void testSearchStockFinancialsException() throws PortfolioException {

        Stock stock = new Stock();
        Mockito.when(portSvc.retrieveStockFinancialsByTicker(any()))
                .thenThrow(PortfolioException.class);

        assertThrows(PortfolioException.class, () -> portSvc.retrieveStockFinancialsByTicker(any()));

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stock", "");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();

        } catch (Exception e) {
            fail("Failed mvc request", e);
        }
        MockHttpServletResponse resp = result.getResponse();

        assertEquals("error", result.getModelAndView().getViewName());

    }
}
