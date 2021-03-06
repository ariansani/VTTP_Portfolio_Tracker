package vttp2022.nusiss.arian.miniproject.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;
import vttp2022.nusiss.arian.miniproject.models.Stock;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;

@Service
public class PortfolioService {

    private static final String QUOTE_URL = "https://finnhub.io/api/v1/quote";
    private static final String STOCK_URL = "https://finnhub.io/api/v1/stock/metric";

    @Value("${finnhub.api.key}")
    private String apiKey;

    private boolean hasKey;

    @PostConstruct
    private void init() {
        hasKey = null != apiKey;
    }

    @Autowired
    private PortfolioRepository portfolioRepo;

    public Optional<Holdings> retrieveHolding(String symbol, Integer holdingId) {

        String quoteUrl = UriComponentsBuilder.fromUriString(QUOTE_URL)
                .queryParam("symbol", symbol)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Finnhub-Token", apiKey);

        RequestEntity<Void> req = RequestEntity
                .get(quoteUrl)
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (resp.getStatusCodeValue() >= 400) {
            return Optional.empty();
        }

        try {

            Holdings holding = Holdings.updatePrice(resp.getBody(), symbol, holdingId);
            return Optional.of(holding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    public String findPortfolioByUserId(Integer userId) throws PortfolioException {

        if (userId < 1)
            throw new PortfolioException("Unable to find portfolio for %s".formatted(userId.toString()));

        Optional<String> optPort = portfolioRepo.findPortfolioByUserId(userId);
        if (optPort.isEmpty())
            throw new PortfolioException("Unable to find portfolio for %s".formatted(userId.toString()));

        return optPort.get();
    }

    public List<Holdings> findHoldingsByPortfolioId(String portfolioId) throws PortfolioException {

        Optional<List<Holdings>> opt = portfolioRepo.findHoldingsByPortfolioId(portfolioId);

        if (opt.isEmpty()) {
            // return Collections.emptyList();
            throw new PortfolioException("cannot find holdings");
        }
        return opt.get();
    }

    public void updateCurrPrice(String symbol, Double currentPrice) throws PortfolioException {

        if (!portfolioRepo.updateCurrPrice(symbol, currentPrice))
            throw new PortfolioException("Unable to update the current price of".formatted(symbol));

    }

    public Optional<Stock> retrieveStockFinancialsByTicker(String symbol) throws PortfolioException {

        String quoteUrl = UriComponentsBuilder.fromUriString(STOCK_URL)
                .queryParam("symbol", symbol)
                .queryParam("metric", "all")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Finnhub-Token", apiKey);

        RequestEntity<Void> req = RequestEntity
                .get(quoteUrl)
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        if (resp.getStatusCodeValue() >= 400) {
            return Optional.empty();
        }

        try {

            Stock stock = Stock.create(resp.getBody(), symbol);
          

            return stock == null ? Optional.empty() : Optional.of(stock);
           
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new PortfolioException("Something went horribly wrong");
        }

    }

    public Optional<Holdings> retrieveStockPriceByTicker(String symbol) {

        String quoteUrl = UriComponentsBuilder.fromUriString(QUOTE_URL)
                .queryParam("symbol", symbol)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Finnhub-Token", apiKey);

        RequestEntity<Void> req = RequestEntity
                .get(quoteUrl)
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (resp.getStatusCodeValue() >= 400) {
            return Optional.empty();
        }

        try {

            Holdings holding = Holdings.create(resp.getBody(), symbol);
            return Optional.of(holding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    public boolean addHoldingToPortfolio(Holdings holding) throws PortfolioException {

        boolean add = portfolioRepo.addHoldingToPortfolio(holding);
        if (!add)
            throw new PortfolioException("Cannot add %s. Please check with admin".formatted(holding.getSymbol()));

        return add;
    }

    public boolean editHolding(Holdings holding) throws PortfolioException {
        boolean edit = portfolioRepo.editHolding(holding);
        if (!edit)
            throw new PortfolioException("Cannot edit %s. Please check with admin".formatted(holding.getSymbol()));

        return edit;
    }

    public boolean deactivateHolding(Holdings holding) throws PortfolioException {
        boolean deactivate = portfolioRepo.deactivateHolding(holding);
        if (!deactivate)
            throw new PortfolioException(
                    "Cannot deactivate %s. Please check with admin".formatted(holding.getSymbol()));

        return deactivate;
    }

}
