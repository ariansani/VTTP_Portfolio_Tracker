package vttp2022.nusiss.arian.miniproject.services;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.web.client.RestTemplate;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;
import vttp2022.nusiss.arian.miniproject.models.Stock;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PortfolioServiceTest {

    @Autowired
    private PortfolioService portSvc;

    @MockBean
    private PortfolioRepository portfolioRepo;

    @MockBean
    private RestTemplate template;
    

    @Test
    void testAddHoldingToPortfolio() {

        Holdings holding = new Holdings();

        Mockito.when(portfolioRepo.addHoldingToPortfolio(holding))
                .thenReturn(true);
        Boolean success = true;
        try {
            success = portSvc.addHoldingToPortfolio(holding);
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(true, success);

    }

    @Test
    void testAddHoldingToPortfolioInvalid() {

        Holdings holding = new Holdings();

        Mockito.when(portfolioRepo.addHoldingToPortfolio(holding))
                .thenReturn(false);

        assertThrows(PortfolioException.class,
                () -> portSvc.addHoldingToPortfolio(holding));

    }

    @Test
    void testDeactivateHolding() {
        Holdings holding = new Holdings();

        Mockito.when(portfolioRepo.deactivateHolding(holding))
                .thenReturn(true);
        Boolean success=true;
        try {
            success = portSvc.deactivateHolding(holding);
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(true, success);
    }

    @Test
    void testDeactivateHoldingInvalid() {
        Holdings holding = new Holdings();

        Mockito.when(portfolioRepo.editHolding(holding))
                .thenReturn(false);

        assertThrows(PortfolioException.class,
                () -> portSvc.deactivateHolding(holding));
    }

    @Test
    void testEditHolding() {
        Holdings holding = new Holdings();

        Mockito.when(portfolioRepo.editHolding(holding))
                .thenReturn(true);
        Boolean success=true;
        try {
            success = portSvc.editHolding(holding);
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(true, success);
    }

    @Test
    void testEditHoldingInvalid() {
        Holdings holding = new Holdings();

        Mockito.when(portfolioRepo.editHolding(holding))
                .thenReturn(false);

        assertThrows(PortfolioException.class,
                () -> portSvc.editHolding(holding));
    }

    @Test
    void testFindHoldingsByPortfolioId() {
        String portfolioId = "ABCD1234";
        List<Holdings> holdings = new LinkedList<>();
        Holdings dummy = new Holdings();
        holdings.add(dummy);

        Mockito.when(portfolioRepo.findHoldingsByPortfolioId(portfolioId))
                .thenReturn(Optional.of(holdings));

        List<Holdings> testHoldings = new LinkedList<>();
        try {
            testHoldings = portSvc.findHoldingsByPortfolioId(portfolioId);
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(holdings, testHoldings);
    }

    @Test
    void testFindHoldingsByPortfolioIdInvalid() {
        String portfolioId = "ABCD1234";
        List<Holdings> holdings = new LinkedList<>();
        Holdings dummy = new Holdings();
        holdings.add(dummy);

        Mockito.when(portfolioRepo.findHoldingsByPortfolioId(portfolioId))
                .thenReturn(Optional.empty());

        assertThrows(PortfolioException.class,
                () -> portSvc.findHoldingsByPortfolioId(portfolioId));
    }

    @Test
    void testFindPortfolioByUserId() {
        Integer userId = 1;
        String portfolioId = "ABCD1234";

        Mockito.when(portfolioRepo.findPortfolioByUserId(userId))
                .thenReturn(Optional.of(portfolioId));

        String testPort = "";
        try {
            testPort = portSvc.findPortfolioByUserId(userId);
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(portfolioId, testPort);
    }

    @Test
    void testFindPortfolioByUserIdInvalid() {
        Integer userId = 1;
        String portfolioId = "ABCD1234";

        Mockito.when(portfolioRepo.findPortfolioByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(PortfolioException.class,
                () -> portSvc.findPortfolioByUserId(userId));

    }

    @Test
    void testRetrieveHolding() {
        String symbol = "AAPL";
        Integer holdingId = 1;

        Optional<Holdings> opt = portSvc.retrieveHolding(symbol, holdingId);

        assertEquals(true, opt.isPresent());

    }



    @Test
    void testRetrieveStockFinancialsByTicker() throws PortfolioException {
        String symbol = "AAPL";

        Optional<Stock> opt = portSvc.retrieveStockFinancialsByTicker(symbol);

        assertEquals(true, opt.isPresent());
    }

    @Test
    void testRetrieveStockPriceByTicker() {
        String symbol = "AAPL";

        Optional<Holdings> opt = portSvc.retrieveStockPriceByTicker(symbol);

        assertEquals(true, opt.isPresent());
    }

    @Test
    void testUpdateCurrPrice() {
        String symbol = "AAPL";
        Double currentPrice = 150.33;

        Mockito.when(portfolioRepo.updateCurrPrice(symbol, currentPrice))
                .thenReturn(true);
        Boolean success = portfolioRepo.updateCurrPrice(symbol, currentPrice);
        assertEquals(true, success);
    }

    @Test
    void testUpdateCurrPriceFail() {
        String symbol = "AAPL";
        Double currentPrice = 150.33;

        Mockito.when(portfolioRepo.updateCurrPrice(symbol, currentPrice))
                .thenReturn(false);

        assertThrows(PortfolioException.class,
                () -> portSvc.updateCurrPrice(symbol, currentPrice));
    }
}
