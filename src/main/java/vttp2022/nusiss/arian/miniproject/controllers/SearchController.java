package vttp2022.nusiss.arian.miniproject.controllers;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.nusiss.arian.miniproject.models.Stock;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private PortfolioService portSvc;

    @PostMapping
    public ResponseEntity<String> getStockInfoViaAjax(
            @RequestBody String searchSymbol) {
                
        Stock stock = new Stock();
        try {
            Optional<Stock> optStock = portSvc.retrieveStockByTicker(searchSymbol);
            if (optStock.isEmpty()) {

                JsonObject errJson = Json.createObjectBuilder()
                        .add("message", "Cannot find : %s".formatted(searchSymbol)).build();
                return ResponseEntity.status(400).body(errJson.toString());

            }
            stock = optStock.get();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObject errJson = Json.createObjectBuilder()
                    .add("message", "Cannot find : %s".formatted(searchSymbol)).build();
            return ResponseEntity.status(400).body(errJson.toString());
        }
       
       
        JsonObject response = Json.createObjectBuilder()
                    .add("message", "found it").build();
        return ResponseEntity.ok("lol");
    }

}
