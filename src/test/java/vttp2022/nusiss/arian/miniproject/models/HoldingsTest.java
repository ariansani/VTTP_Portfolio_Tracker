package vttp2022.nusiss.arian.miniproject.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class HoldingsTest {

    Holdings holding = new Holdings();

    @Test
    void testCostBasis() {

        Float costBasis = 1.00f;
        holding.setCostBasis(costBasis);
        assertEquals(costBasis, holding.getCostBasis());
    }

    @Test
    void testCreatedAt() {

        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        holding.setCreatedAt(createdAt);
        assertEquals(createdAt, holding.getCreatedAt());
    }

    @Test
    void testCurrentPrice() {
        Double currentPrice = 1.00d;
        holding.setCurrentPrice(currentPrice);
        assertEquals(currentPrice, holding.getCurrentPrice());
    }

    @Test
    void testId() {
        Integer id = 1;
        holding.setId(1);
        assertEquals(id, holding.getId());
    }

    @Test
    void testIsActive() {
        Boolean isActive = true;
        holding.setIsActive(isActive);
        assertEquals(isActive, holding.getIsActive());
    }

    @Test
    void testPercentageChange() {
        Double percentageChange = 1.00d;
        holding.setPercentageChange(percentageChange);
        assertEquals(percentageChange, holding.getPercentageChange());
    }

    @Test
    void testPortfolioId() {
        String portfolioId = "abc";
        holding.setPortfolioId(portfolioId);
        assertEquals(portfolioId, holding.getPortfolioId());
    }

    @Test
    void testProfitLoss() {
        Double profitLoss = 1.00d;
        holding.setProfitLoss(profitLoss);
        assertEquals(profitLoss, holding.getProfitLoss());
    }

    @Test
    void testQuantity() {
        Integer quantity = 1;
        holding.setQuantity(quantity);
        assertEquals(quantity, holding.getQuantity());
    }

    @Test
    void testSymbol() {
        String symbol = "AAPL";
        holding.setSymbol(symbol);
        assertEquals(symbol, holding.getSymbol());
    }

    @Test
    void testTotalValue() {
        Double totalValue = 1.00d;
        holding.setTotalValue(totalValue);
        assertEquals(totalValue, holding.getTotalValue());
    }

    @Test
    void testUpdatedAt() {

        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        holding.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, holding.getUpdatedAt());
    }

    @Test
    void testCreate() {
        // json, symbol
        JsonObject responseJson = Json.createObjectBuilder()
                .add("c", 140.82)
                .add("dp", -5.6419)
                .build();
        String json = responseJson.toString();
        String symbol = "AAPL";

        Holdings testHolding = Holdings.create(json, symbol);
        assertEquals(140.82, testHolding.getCurrentPrice());

    }

    @Test
    void testCreateFail() {
        // json, symbol
        JsonObject responseJson = Json.createObjectBuilder()
                .build();

        String json = responseJson.toString();
        String symbol = "AAPL";

        Holdings testHolding = Holdings.create(json, symbol);
        assertEquals(null, testHolding);

    }

    @Test
    void testUpdatePrice() {
        // json, symbol, holdingId
        JsonObject responseJson = Json.createObjectBuilder()
                .add("c", 140.82)
                .add("dp", -5.6419)
                .build();
        String json = responseJson.toString();
        String symbol = "AAPL";
        Integer holdingId = 1;

        Holdings testHolding = Holdings.updatePrice(json, symbol, holdingId);
        assertEquals(140.82, testHolding.getCurrentPrice());

    }

    @Test
    void testUpdatePriceFail() {
        // json, symbol
        JsonObject responseJson = Json.createObjectBuilder()
                .build();

        String json = responseJson.toString();
        String symbol = "AAPL";
        Integer holdingId = 1;

        Holdings testHolding = Holdings.updatePrice(json, symbol, holdingId);
        assertEquals(null, testHolding);

    }

}
