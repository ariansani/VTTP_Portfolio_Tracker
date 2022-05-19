package vttp2022.nusiss.arian.miniproject.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class StockTest {

    Stock stock = new Stock();

    @Test
    void testGetBookValuePerShareQ() {
        double bv = 1.00d;
        stock.setBookValuePerShareQ(bv);
        assertEquals(bv, stock.getBookValuePerShareQ());
    }

    @Test
    void testGetCashPerShareQ() {
        double cps = 1.00d;
        stock.setCashPerShareQ(cps);
        assertEquals(cps, stock.getCashPerShareQ());
    }

    @Test
    void testGetCurrentRatioQ() {
        double currentRatio = 1.00d;
        stock.setCurrentRatioQ(currentRatio);
        assertEquals(currentRatio, stock.getCurrentRatioQ());
    }

    @Test
    void testGetDividendYield() {
        double div = 1.00d;
        stock.setDividendYield(div);
        assertEquals(div, stock.getDividendYield());

    }

    @Test
    void testGetEbitdaCAGR5y() {
        double ebitda = 1.00d;
        stock.setEbitdaCAGR5y(ebitda);
        assertEquals(ebitda, stock.getEbitdaCAGR5y());
    }

    @Test
    void testGetEpsGrowth5y() {
        double eps = 1.00d;
        stock.setEpsGrowth5y(eps);
        assertEquals(eps, stock.getEpsGrowth5y());
    }

    @Test
    void testGetEpsTTM() {
        double epsttm = 1.00d;
        stock.setEpsTTM(epsttm);
        assertEquals(epsttm, stock.getEpsTTM());
    }

    @Test
    void testGetFreeCashFlowTTM() {
        double fcf = 1.00d;
        stock.setFreeCashFlowTTM(fcf);
        assertEquals(fcf, stock.getFreeCashFlowTTM());
    }

    @Test
    void testGetGrossMarginTTM() {
        double grossMargin = 1.00d;
        stock.setGrossMarginTTM(grossMargin);
        assertEquals(grossMargin, stock.getGrossMarginTTM());
    }

    @Test
    void testGetLongTermDebtEquityA() {
        double debt = 1.00d;
        stock.setLongTermDebtEquityA(debt);
        assertEquals(debt, stock.getLongTermDebtEquityA());
    }

    @Test
    void testGetMarketCap() {
        double marketCap = 1.00d;
        stock.setMarketCap(marketCap);
        assertEquals(marketCap, stock.getMarketCap());
    }

    @Test
    void testGetNetProfitMarginTTM() {
        double profitMargin = 1.00d;
        stock.setNetProfitMarginTTM(profitMargin);
        assertEquals(profitMargin, stock.getNetProfitMarginTTM());
    }

    @Test
    void testGetOperMarginTTM() {
        double operMargin = 1.00d;
        stock.setOperMarginTTM(operMargin);
        assertEquals(operMargin, stock.getOperMarginTTM());
    }

    @Test
    void testGetPeRatioTTM() {
        double pe = 1.00d;
        stock.setPeRatioTTM(pe);
        assertEquals(pe, stock.getPeRatioTTM());

    }

    @Test
    void testGetPriceFCFTTM() {
        double pricefcf = 1.00d;
        stock.setPriceFCFTTM(pricefcf);
        assertEquals(pricefcf, stock.getPriceFCFTTM());
    }

    @Test
    void testGetPsRatioTTM() {
        double ps = 1.00d;
        stock.setPsRatioTTM(ps);
        assertEquals(ps, stock.getPsRatioTTM());
    }

    @Test
    void testGetRoa() {
        double roa = 1.00d;
        stock.setRoa(roa);
        assertEquals(roa, stock.getRoa());
    }

    @Test
    void testGetRoe() {
        double roe = 1.00d;
        stock.setRoe(roe);
        assertEquals(roe, stock.getRoe());
    }

    @Test
    void testGetStockName() {
        String name = "AAPL";
        stock.setStockName(name);
        assertEquals(name, stock.getStockName());
    }

    @Test
    void testGetYearHigh() {
        double high = 1.00d;
        stock.setYearHigh(high);
        assertEquals(high, stock.getYearHigh());
    }

    @Test
    void testGetYearLow() {
        double low = 1.00d;
        stock.setYearLow(low);
        assertEquals(low, stock.getYearLow());
    }

    @Test
    void testCreate() {
        // json, symbol

        JsonObject o = Json.createObjectBuilder()
                .add("marketCapitalization", 1.00d)
                .add("52WeekHigh", 1.00d)
                .add("52WeekLow", 1.00d)
                .add("bookValuePerShareQuarterly", 1.00d)
                .add("cashPerSharePerShareQuarterly", 1.00d)
                .add("dividendYieldIndicatedAnnual", 1.00d)
                .add("currentRatioQuarterly", 1.00d)
                .add("ebitdaCagr5Y", 1.00d)
                .add("epsInclExtraItemsTTM", 1.00d)
                .add("epsGrowth5Y", 1.00d)
                .add("freeCashFlowTTM", 1.00d)
                .add("longTermDebt/equityAnnual", 1.00d)
                .add("grossMarginTTM", 1.00d)
                .add("netProfitMarginTTM", 1.00d)
                .add("operatingMarginTTM", 1.00d)
                .add("pfcfShareTTM", 1.00d)
                .add("peInclExtraTTM", 1.00d)
                .add("psTTM", 1.00d)
                .add("roaRfy", 1.00d)
                .add("roeRfy", 1.00d)
                .build();

        JsonObject responseJson = Json.createObjectBuilder()
                .add("metric", o)
                .build();
        String json = responseJson.toString();
        String symbol = "AAPL";

        Stock testStock = Stock.create(json, symbol);
        assertEquals(1.00, testStock.getCashPerShareQ());

    }

    @Test
    void testCreateFail() {
        // json, symbol

        JsonObject o = Json.createObjectBuilder()

                .build();

        JsonObject responseJson = Json.createObjectBuilder()
                .add("metric", o)
                .build();
        String json = responseJson.toString();
        String symbol = "AAPL";

        Stock testStock = Stock.create(json, symbol);
        assertEquals(null, testStock);

    }
    @Test
    void testCreateConditional() {
        // json, symbol

        JsonObject o = Json.createObjectBuilder()
                .add("marketCapitalization", JsonValue.NULL)
                .add("52WeekHigh", JsonValue.NULL)
                .add("52WeekLow", JsonValue.NULL)
                .add("bookValuePerShareQuarterly", JsonValue.NULL)
                .add("cashPerSharePerShareQuarterly", JsonValue.NULL)
                .add("dividendYieldIndicatedAnnual", JsonValue.NULL)
                .add("currentRatioQuarterly", JsonValue.NULL)
                .add("ebitdaCagr5Y", JsonValue.NULL)
                .add("epsInclExtraItemsTTM", JsonValue.NULL)
                .add("epsGrowth5Y", JsonValue.NULL)
                .add("freeCashFlowTTM",JsonValue.NULL)
                .add("longTermDebt/equityAnnual", JsonValue.NULL)
                .add("grossMarginTTM", JsonValue.NULL)
                .add("netProfitMarginTTM", JsonValue.NULL)
                .add("operatingMarginTTM", JsonValue.NULL)
                .add("pfcfShareTTM", JsonValue.NULL)
                .add("peInclExtraTTM",JsonValue.NULL)
                .add("psTTM",JsonValue.NULL)
                .add("roaRfy", JsonValue.NULL)
                .add("roeRfy",JsonValue.NULL)
                .build();

        JsonObject responseJson = Json.createObjectBuilder()
                .add("metric", o)
                .build();
        String json = responseJson.toString();
        String symbol = "AAPL";

        Stock testStock = Stock.create(json, symbol);
        assertEquals(null, testStock.getEbitdaCAGR5y());

    }
}
