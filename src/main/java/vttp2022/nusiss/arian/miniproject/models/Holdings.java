package vttp2022.nusiss.arian.miniproject.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Holdings {
    private Integer id;
    private String symbol;
    private Integer quantity;
    private Float costBasis;
    private Double currentPrice;
    private Double percentageChange;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String portfolioId;
    private Double totalValue;
    private Double profitLoss;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCostBasis() {
        return costBasis;
    }

    public void setCostBasis(Float costBasis) {
        this.costBasis = costBasis;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(Double percentageChange) {
        this.percentageChange = percentageChange;
    }
    
    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }


    public static Holdings create(String json,String symbol){
        Holdings holding = new Holdings();
        holding.setSymbol(symbol);

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            Double floatVal =  o.getJsonNumber("c").doubleValue();
            Double percentageChange =  o.getJsonNumber("dp").doubleValue();
            holding.setCurrentPrice(floatVal);
            holding.setPercentageChange(percentageChange);
            

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return holding;
    }

    public static Holdings updatePrice(String json,String symbol,Integer holdingId){
        Holdings holding = new Holdings();
        holding.setSymbol(symbol);
        holding.setId(holdingId);

        try (InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            Double floatVal =  o.isNull("c") ? null: o.getJsonNumber("c").doubleValue();
            Double percentageChange =  o.isNull("dp") ? null : o.getJsonNumber("dp").doubleValue();
            holding.setCurrentPrice(floatVal);
            holding.setPercentageChange(percentageChange);
            

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return holding;
    }

}
