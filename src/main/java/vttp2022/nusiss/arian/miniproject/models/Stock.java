package vttp2022.nusiss.arian.miniproject.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Stock {
    private String stockName;
    private Double marketCap;// marketCapitalization
    private Double yearHigh;// 52WeekHigh
    private Double yearLow;// 52WeekLow
    private Double bookValuePerShareQ; // bookValuePerShareQuarterly
    private Double cashPerShareQ; // cashPerSharePerShareQuarterly
    private Double dividendYield; // dividendYieldIndicatedAnnual
    private Double currentRatioQ; // currentRatioQuarterly
    private Double ebitdaCAGR5y; // ebitdaCagr5Y
    private Double epsTTM; // epsInclExtraItemsTTM in json response
    private Double epsGrowth5y; // epsGrowth5Y
    private Double freeCashFlowTTM; // freeCashFlowTTM
    private Double longTermDebtEquityA;// longTermDebt/equityAnnual
    private Double grossMarginTTM; // grossMarginTTM
    private Double netProfitMarginTTM; // netProfitMarginTTM
    private Double operMarginTTM; // operatingMarginTTM
    private Double priceFCFTTM; // pfcfShareTTM
    private Double peRatioTTM; // peInclExtraTTM
    private Double psRatioTTM;// psTTM
    private Double roa; // roaRfy
    private Double roe; // roeRfy

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(Double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public Double getYearLow() {
        return yearLow;
    }

    public void setYearLow(Double yearLow) {
        this.yearLow = yearLow;
    }

    public Double getBookValuePerShareQ() {
        return bookValuePerShareQ;
    }

    public void setBookValuePerShareQ(Double bookValuePerShareQ) {
        this.bookValuePerShareQ = bookValuePerShareQ;
    }

    public Double getCashPerShareQ() {
        return cashPerShareQ;
    }

    public void setCashPerShareQ(Double cashPerShareQ) {
        this.cashPerShareQ = cashPerShareQ;
    }

    public Double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public Double getCurrentRatioQ() {
        return currentRatioQ;
    }

    public void setCurrentRatioQ(Double currentRatioQ) {
        this.currentRatioQ = currentRatioQ;
    }

    public Double getEbitdaCAGR5y() {
        return ebitdaCAGR5y;
    }

    public void setEbitdaCAGR5y(Double ebitdaCAGR5y) {
        this.ebitdaCAGR5y = ebitdaCAGR5y;
    }

    public Double getEpsTTM() {
        return epsTTM;
    }

    public void setEpsTTM(Double epsTTM) {
        this.epsTTM = epsTTM;
    }

    public Double getEpsGrowth5y() {
        return epsGrowth5y;
    }

    public void setEpsGrowth5y(Double epsGrowth5y) {
        this.epsGrowth5y = epsGrowth5y;
    }

    public Double getFreeCashFlowTTM() {
        return freeCashFlowTTM;
    }

    public void setFreeCashFlowTTM(Double freeCashFlowTTM) {
        this.freeCashFlowTTM = freeCashFlowTTM;
    }

    public Double getLongTermDebtEquityA() {
        return longTermDebtEquityA;
    }

    public void setLongTermDebtEquityA(Double longTermDebtEquityA) {
        this.longTermDebtEquityA = longTermDebtEquityA;
    }

    public Double getGrossMarginTTM() {
        return grossMarginTTM;
    }

    public void setGrossMarginTTM(Double grossMarginTTM) {
        this.grossMarginTTM = grossMarginTTM;
    }

    public Double getNetProfitMarginTTM() {
        return netProfitMarginTTM;
    }

    public void setNetProfitMarginTTM(Double netProfitMarginTTM) {
        this.netProfitMarginTTM = netProfitMarginTTM;
    }

    public Double getOperMarginTTM() {
        return operMarginTTM;
    }

    public void setOperMarginTTM(Double operMarginTTM) {
        this.operMarginTTM = operMarginTTM;
    }

    public Double getPriceFCFTTM() {
        return priceFCFTTM;
    }

    public void setPriceFCFTTM(Double priceFCFTTM) {
        this.priceFCFTTM = priceFCFTTM;
    }

    public Double getPeRatioTTM() {
        return peRatioTTM;
    }

    public void setPeRatioTTM(Double peRatioTTM) {
        this.peRatioTTM = peRatioTTM;
    }

    public Double getPsRatioTTM() {
        return psRatioTTM;
    }

    public void setPsRatioTTM(Double psRatioTTM) {
        this.psRatioTTM = psRatioTTM;
    }

    public Double getRoa() {
        return roa;
    }

    public void setRoa(Double roa) {
        this.roa = roa;
    }

    public Double getRoe() {
        return roe;
    }

    public void setRoe(Double roe) {
        this.roe = roe;
    }

    public static Stock create(String json, String symbol) {
        Stock stock = new Stock();
        stock.setStockName(symbol);

        try (InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            
            JsonReader r = Json.createReader(is);
            JsonObject jso = r.readObject();
            JsonObject o = jso.getJsonObject("metric");
        
            stock.setMarketCap(o.getJsonNumber("marketCapitalization").doubleValue());// marketCapitalization
           
            stock.yearHigh=o.getJsonNumber("52WeekHigh").doubleValue();// 52WeekHigh
            stock.yearLow=o.getJsonNumber("52WeekLow").doubleValue();// 52WeekLow
            stock.bookValuePerShareQ=o.getJsonNumber("bookValuePerShareQuarterly").doubleValue(); // bookValuePerShareQuarterly
            stock.cashPerShareQ=o.getJsonNumber("cashPerSharePerShareQuarterly").doubleValue(); // cashPerSharePerShareQuarterly
            stock.dividendYield=o.getJsonNumber("dividendYieldIndicatedAnnual").doubleValue(); // dividendYieldIndicatedAnnual
            stock.currentRatioQ=o.getJsonNumber("currentRatioQuarterly").doubleValue(); // currentRatioQuarterly
            stock.ebitdaCAGR5y=o.getJsonNumber("ebitdaCagr5Y").doubleValue(); // ebitdaCagr5Y
            stock.epsTTM=o.getJsonNumber("epsInclExtraItemsTTM").doubleValue(); // epsInclExtraItemsTTM in json response
            stock.epsGrowth5y=o.getJsonNumber("epsGrowth5Y").doubleValue(); // epsGrowth5Y
            stock.freeCashFlowTTM=o.getJsonNumber("freeCashFlowTTM").doubleValue(); // freeCashFlowTTM
            stock.longTermDebtEquityA=o.getJsonNumber("longTermDebt/equityAnnual").doubleValue();// longTermDebt/equityAnnual
            stock.grossMarginTTM=o.getJsonNumber("grossMarginTTM").doubleValue(); // grossMarginTTM
            stock.netProfitMarginTTM=o.getJsonNumber("netProfitMarginTTM").doubleValue(); // netProfitMarginTTM
            stock.operMarginTTM=o.getJsonNumber("operatingMarginTTM").doubleValue(); // operatingMarginTTM
            stock.priceFCFTTM=o.getJsonNumber("pfcfShareTTM").doubleValue(); // pfcfShareTTM
            stock.peRatioTTM=o.getJsonNumber("peInclExtraTTM").doubleValue(); // peInclExtraTTM
            stock.psRatioTTM=o.getJsonNumber("psTTM").doubleValue();// psTTM
            stock.roa=o.getJsonNumber("roaRfy").doubleValue(); // roaRfy
            stock.roe=o.getJsonNumber("roeRfy").doubleValue(); // roeRfy

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return stock;
    }

}
