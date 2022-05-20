package vttp2022.nusiss.arian.miniproject.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;
import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PortfolioRestControllerTest {

    @Autowired
    private PortfolioRestController portfolioRestCtrl;

    @MockBean
    private PortfolioService portSvc;

    @Test
    void testAddHoldingToPortfolio() throws PortfolioException {
        JsonObject responseJson = Json.createObjectBuilder()
                .add("portfolioId", "abc123")
                .add("symbol", "AAPL")
                .add("quantity", "10")
                .add("costBasis", "15.33")
                .add("currentPrice", "160.55")
                .build();
        String json = responseJson.toString();

        Holdings holding = new Holdings();

        Mockito.when(portSvc.addHoldingToPortfolio(holding))
        .thenReturn(true);
        
        ResponseEntity<String> result = portfolioRestCtrl.addHoldingToPortfolio(json);
        Integer priceResult = result.getStatusCodeValue();
        
        assertEquals(200, priceResult);
    }
    @Test
    void testAddHoldingToPortfolioInvalid() {

        ResponseEntity<String> result = portfolioRestCtrl.addHoldingToPortfolio(null);
        String failResult = result.getBody();
        String expected = "{}";
        assertEquals(expected.length() > 0, failResult.length() > 2);
    }


    @Test
    void testDeactivateHolding() {

    }

    @Test
    void testEditHolding() {

    }

    @Test
    void testGetStockInfoViaAjax() {

        Double currentPrice = 0.0d;
        String searchSymbol = "AAPL";

        Holdings holding = new Holdings();
        holding.setCurrentPrice(currentPrice);

        Optional<Holdings> optHolding = Optional.of(holding);

        Mockito.when(portSvc.retrieveStockPriceByTicker(searchSymbol))
                .thenReturn(optHolding);

        ResponseEntity<String> result = portfolioRestCtrl.getStockInfoViaAjax(searchSymbol);
        String priceResult = result.getBody();

        assertEquals(currentPrice, Double.valueOf(priceResult));
    }

    @Test
    void testGetStockInfoViaAjaxError() {

        Double currentPrice = 0.0d;
        String searchSymbol = "AAPL";

        ResponseEntity<String> result = portfolioRestCtrl.getStockInfoViaAjax(searchSymbol);
        String priceResult = result.getBody();
        String expected = "{}";
        assertEquals(expected.length() > 0, priceResult.length() > 2);
    }
}
