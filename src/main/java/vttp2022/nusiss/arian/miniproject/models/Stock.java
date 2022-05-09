package vttp2022.nusiss.arian.miniproject.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Stock {
    private String stockName;
    private double marketCap;// marketCapitalization
    private double yearHigh;// 52WeekHigh
    private double yearLow;// 52WeekLow
    private double bookValuePerShareQ; // bookValuePerShareQuarterly
    private double cashPerShareQ; // cashPerSharePerShareQuarterly
    private double dividendYield; // dividendYieldIndicatedAnnual
    private double currentRatioQ; // currentRatioQuarterly
    private double ebitdaCAGR5y; // ebitdaCagr5Y
    private double epsTTM; // epsInclExtraItemsTTM in json response
    private double epsGrowth5y; // epsGrowth5Y
    private double freeCashFlowTTM; // freeCashFlowTTM
    private double longTermDebtEquityA;// longTermDebt/equityAnnual
    private double grossMarginTTM; // grossMarginTTM
    private double netProfitMarginTTM; // netProfitMarginTTM
    private double operMarginTTM; // operatingMarginTTM
    private double priceFCFTTM; // pfcfShareTTM
    private double peRatioTTM; // peInclExtraTTM
    private double psRatioTTM;// psTTM
    private double roa; // roaRfy
    private double roe; // roeRfy

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public double getYearLow() {
        return yearLow;
    }

    public void setYearLow(double yearLow) {
        this.yearLow = yearLow;
    }

    public double getBookValuePerShareQ() {
        return bookValuePerShareQ;
    }

    public void setBookValuePerShareQ(double bookValuePerShareQ) {
        this.bookValuePerShareQ = bookValuePerShareQ;
    }

    public double getCashPerShareQ() {
        return cashPerShareQ;
    }

    public void setCashPerShareQ(double cashPerShareQ) {
        this.cashPerShareQ = cashPerShareQ;
    }

    public double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public double getCurrentRatioQ() {
        return currentRatioQ;
    }

    public void setCurrentRatioQ(double currentRatioQ) {
        this.currentRatioQ = currentRatioQ;
    }

    public double getEbitdaCAGR5y() {
        return ebitdaCAGR5y;
    }

    public void setEbitdaCAGR5y(double ebitdaCAGR5y) {
        this.ebitdaCAGR5y = ebitdaCAGR5y;
    }

    public double getEpsTTM() {
        return epsTTM;
    }

    public void setEpsTTM(double epsTTM) {
        this.epsTTM = epsTTM;
    }

    public double getEpsGrowth5y() {
        return epsGrowth5y;
    }

    public void setEpsGrowth5y(double epsGrowth5y) {
        this.epsGrowth5y = epsGrowth5y;
    }

    public double getFreeCashFlowTTM() {
        return freeCashFlowTTM;
    }

    public void setFreeCashFlowTTM(double freeCashFlowTTM) {
        this.freeCashFlowTTM = freeCashFlowTTM;
    }

    public double getLongTermDebtEquityA() {
        return longTermDebtEquityA;
    }

    public void setLongTermDebtEquityA(double longTermDebtEquityA) {
        this.longTermDebtEquityA = longTermDebtEquityA;
    }

    public double getGrossMarginTTM() {
        return grossMarginTTM;
    }

    public void setGrossMarginTTM(double grossMarginTTM) {
        this.grossMarginTTM = grossMarginTTM;
    }

    public double getNetProfitMarginTTM() {
        return netProfitMarginTTM;
    }

    public void setNetProfitMarginTTM(double netProfitMarginTTM) {
        this.netProfitMarginTTM = netProfitMarginTTM;
    }

    public double getOperMarginTTM() {
        return operMarginTTM;
    }

    public void setOperMarginTTM(double operMarginTTM) {
        this.operMarginTTM = operMarginTTM;
    }

    public double getPriceFCFTTM() {
        return priceFCFTTM;
    }

    public void setPriceFCFTTM(double priceFCFTTM) {
        this.priceFCFTTM = priceFCFTTM;
    }

    public double getPeRatioTTM() {
        return peRatioTTM;
    }

    public void setPeRatioTTM(double peRatioTTM) {
        this.peRatioTTM = peRatioTTM;
    }

    public double getPsRatioTTM() {
        return psRatioTTM;
    }

    public void setPsRatioTTM(double psRatioTTM) {
        this.psRatioTTM = psRatioTTM;
    }

    public double getRoa() {
        return roa;
    }

    public void setRoa(double roa) {
        this.roa = roa;
    }

    public double getRoe() {
        return roe;
    }

    public void setRoe(double roe) {
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

            stock.yearHigh = o.getJsonNumber("52WeekHigh").doubleValue();// 52WeekHigh
            stock.yearLow = o.getJsonNumber("52WeekLow").doubleValue();// 52WeekLow
            stock.bookValuePerShareQ = o.getJsonNumber("bookValuePerShareQuarterly").doubleValue(); // bookValuePerShareQuarterly
            stock.cashPerShareQ = o.getJsonNumber("cashPerSharePerShareQuarterly").doubleValue(); // cashPerSharePerShareQuarterly
            stock.dividendYield = o.getJsonNumber("dividendYieldIndicatedAnnual").doubleValue(); // dividendYieldIndicatedAnnual
            stock.currentRatioQ = o.getJsonNumber("currentRatioQuarterly").doubleValue(); // currentRatioQuarterly
            stock.ebitdaCAGR5y = o.getJsonNumber("ebitdaCagr5Y").doubleValue(); // ebitdaCagr5Y
            stock.epsTTM = o.getJsonNumber("epsInclExtraItemsTTM").doubleValue(); // epsInclExtraItemsTTM in json
                                                                                  // response
            stock.epsGrowth5y = o.getJsonNumber("epsGrowth5Y").doubleValue(); // epsGrowth5Y
            stock.freeCashFlowTTM = o.getJsonNumber("freeCashFlowTTM").doubleValue(); // freeCashFlowTTM
            stock.longTermDebtEquityA = o.getJsonNumber("longTermDebt/equityAnnual").doubleValue();// longTermDebt/equityAnnual
            stock.grossMarginTTM = o.getJsonNumber("grossMarginTTM").doubleValue(); // grossMarginTTM
            stock.netProfitMarginTTM = o.getJsonNumber("netProfitMarginTTM").doubleValue(); // netProfitMarginTTM
            stock.operMarginTTM = o.getJsonNumber("operatingMarginTTM").doubleValue(); // operatingMarginTTM
            stock.priceFCFTTM = o.getJsonNumber("pfcfShareTTM").doubleValue(); // pfcfShareTTM
            stock.peRatioTTM = o.getJsonNumber("peInclExtraTTM").doubleValue(); // peInclExtraTTM
            stock.psRatioTTM = o.getJsonNumber("psTTM").doubleValue();// psTTM
            stock.roa = o.getJsonNumber("roaRfy").doubleValue(); // roaRfy
            stock.roe = o.getJsonNumber("roeRfy").doubleValue(); // roeRfy

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return stock;
    }

}
