package vttp2022.nusiss.arian.miniproject.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;

import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

@RestController
@RequestMapping
public class PortfolioRestController {

    @Autowired
    private PortfolioService portSvc;

    @PostMapping(path = "/search", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStockInfoViaAjax(
            @RequestBody String searchSymbol) {

        Holdings holding = new Holdings();
        try {
            Optional<Holdings> optHolding = portSvc.retrieveStockPriceByTicker(searchSymbol);
            if (optHolding.isEmpty()) {

                JsonObject errJson = Json.createObjectBuilder()
                        .add("errorMessage", "Cannot find : %s".formatted(searchSymbol)).build();
                return ResponseEntity.status(400).body(errJson.toString());

            }
            holding = optHolding.get();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObject errJson = Json.createObjectBuilder()
                    .add("errorMessage", "Cannot find : %s".formatted(searchSymbol)).build();
            return ResponseEntity.status(400).body(errJson.toString());
        }

        // JsonObject response = Json.createObjectBuilder()
        // .add("message", "found it").build();
        return ResponseEntity.ok(holding.getCurrentPrice().toString());
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHoldingToPortfolio(
            @RequestBody String payload) {

        JsonObject responseJson;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8))) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();
            
            //let Java handle the type safety instead of parsing datatypes in Javascript
            Holdings holding = new Holdings();
            holding.setPortfolioId(o.getString("portfolioId"));
            holding.setSymbol(o.getString("symbol"));
                String quantity =  o.getString("quantity");
            holding.setQuantity(Integer.valueOf(quantity));
                
            holding.setCostBasis(Float.valueOf(o.getString("costBasis")) );
            holding.setCurrentPrice(Double.valueOf(o.getString("currentPrice")));
            holding.setIsActive(true);

            
            boolean addedSuccessfully;
            try {
                addedSuccessfully=portSvc.addHoldingToPortfolio(holding);
            } catch (PortfolioException e) {
                //TODO: handle exception
                JsonObject errJson = Json.createObjectBuilder()
                .add("errorMessage",e.getReason()).build();
                return ResponseEntity.status(400).body(errJson.toString());
            }


            responseJson = Json.createObjectBuilder()
                    .add("status", addedSuccessfully)
                    .build();

        } catch (Exception e) {
            // TODO: handle exception
            JsonObject errJson = Json.createObjectBuilder()
                    .add("errorMessage", e.getMessage()).build();
            return ResponseEntity.status(400).body(errJson.toString());
        }

        return ResponseEntity.ok(responseJson.toString());
    }


    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editHolding(
            @RequestBody String payload) {

        JsonObject responseJson;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8))) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();
            
            //let Java handle the type safety instead of parsing datatypes in Javascript
            Holdings holding = new Holdings();
            holding.setId(Integer.valueOf(o.getString("holdingId")));
            holding.setQuantity(Integer.valueOf(o.getString("quantity")));
            holding.setSymbol(o.getString("symbol"));
            holding.setCostBasis(Float.valueOf(o.getString("costBasis")));

            
            boolean editSuccess;
            try {
                editSuccess=portSvc.editHolding(holding);
            } catch (PortfolioException e) {
                //TODO: handle exception
                JsonObject errJson = Json.createObjectBuilder()
                .add("errorMessage", e.getReason()).build();
                return ResponseEntity.status(400).body(errJson.toString());
            }


            responseJson = Json.createObjectBuilder()
                    .add("status", editSuccess)
                    .build();

        } catch (Exception e) {
            // TODO: handle exception
            JsonObject errJson = Json.createObjectBuilder()
                    .add("errorMessage", e.getMessage()).build();
            return ResponseEntity.status(400).body(errJson.toString());
        }

        return ResponseEntity.ok(responseJson.toString());
    }

    @PutMapping(path = "/softdelete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deactivateHolding(
            @RequestBody String payload) {

        JsonObject responseJson;
        try (InputStream is = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8))) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();
            
            //let Java handle the type safety instead of parsing datatypes in Javascript
            Holdings holding = new Holdings();
            holding.setId(Integer.valueOf(o.getString("holdingId")));
            holding.setSymbol(o.getString("symbol"));

            
            boolean deactivateSuccess;
            try {
                deactivateSuccess=portSvc.deactivateHolding(holding);
            } catch (PortfolioException e) {
                //TODO: handle exception
                JsonObject errJson = Json.createObjectBuilder()
                .add("errorMessage", e.getReason()).build();
                return ResponseEntity.status(400).body(errJson.toString());
            }


            responseJson = Json.createObjectBuilder()
                    .add("status", deactivateSuccess)
                    .build();

        } catch (Exception e) {
            // TODO: handle exception
            JsonObject errJson = Json.createObjectBuilder()
                    .add("errorMessage", e.getMessage()).build();
            return ResponseEntity.status(400).body(errJson.toString());
        }

        return ResponseEntity.ok(responseJson.toString());
    }
}
