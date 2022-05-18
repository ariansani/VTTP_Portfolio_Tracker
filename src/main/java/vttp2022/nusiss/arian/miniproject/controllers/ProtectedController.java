package vttp2022.nusiss.arian.miniproject.controllers;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;
import vttp2022.nusiss.arian.miniproject.models.Stock;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

@Controller
@RequestMapping
public class ProtectedController {

    @Autowired
    private PortfolioService portSvc;

    @GetMapping(path = "/protected/manage/{view}")
    public ModelAndView managePortfolio(@PathVariable String view, HttpSession sess) {
        
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName(view);
        
        User authUser = (User) sess.getAttribute("authUserSess");
        if (authUser == null) {
            mvc.setStatus(HttpStatus.FORBIDDEN);
            mvc.addObject("errorMessage","ACCESS FORBIDDEN. Only registered users can access this feature.");
            mvc.setViewName("error");
            return mvc;
        }
        String portfolioId = authUser.getPortfolioId();
        List<Holdings> holdings = new LinkedList<>();

        try {
            holdings = portSvc.findHoldingsByPortfolioId(portfolioId);
        } catch (PortfolioException e) {
            e.printStackTrace();
            mvc.addObject("errorMessage", "Something went wrong!");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            return mvc;
        }

        Double assetsUnderMgmt = 0.0d;
        Double profitLoss = 0.0d;

        for (Holdings holding : holdings) {

            holding.setProfitLoss(holding.getCurrentPrice() * holding.getQuantity()
                    - holding.getCostBasis() * holding.getQuantity());


            holding.setTotalValue(holding.getCurrentPrice() * holding.getQuantity());
            assetsUnderMgmt += holding.getTotalValue();
            profitLoss += holding.getProfitLoss();
        }

        mvc.addObject("aum", assetsUnderMgmt);
        mvc.addObject("profitLoss", profitLoss);
        mvc.addObject("portfolioId", portfolioId);
        mvc.addObject("holdings", holdings);
        mvc.setStatus(HttpStatus.OK);

        return mvc;

    }

    @GetMapping(path = "/protected/{view}")
    @PostMapping(path = "/protected/{view}")
    public ModelAndView post(@PathVariable String view, HttpSession sess) {

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName(view);

        User authUser = (User) sess.getAttribute("authUserSess");

         if (authUser == null) {
            mvc.setStatus(HttpStatus.UNAUTHORIZED);
            mvc.addObject("errorMessage","ACCESS FORBIDDEN. Only registered users can access this feature.");
            mvc.setViewName("error");
            return mvc;
        }
        String portfolioId = authUser.getPortfolioId();
        List<Holdings> holdings = new LinkedList<>();
        try {
            holdings = portSvc.findHoldingsByPortfolioId(portfolioId);
        } catch (PortfolioException e) {
            e.printStackTrace();
            mvc.addObject("errorMessage", "Unable to retrieve portfolio. Please try again.");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            return mvc;
        }

        List<Holdings> updatedPriceAndPercentage = new LinkedList<>();
        Double assetsUnderMgmt = 0.0d;
        Double profitLoss = 0.0d;
        for (Holdings stock : holdings) {
            Optional<Holdings> optHolding = portSvc.retrieveHolding(stock.getSymbol(), stock.getId());

            if (optHolding.isEmpty()) {
                mvc.setStatus(HttpStatus.BAD_REQUEST);
                mvc.setViewName("error");
                return mvc;
            }
            Holdings latestHolding = optHolding.get();
            latestHolding.setQuantity(stock.getQuantity());
            latestHolding.setCostBasis(stock.getCostBasis());
            latestHolding.setProfitLoss(stock.getCurrentPrice() * stock.getQuantity() - stock.getCostBasis() * stock.getQuantity());
            latestHolding.setTotalValue(stock.getCurrentPrice() * stock.getQuantity());
            assetsUnderMgmt+=latestHolding.getTotalValue();
            profitLoss+=latestHolding.getProfitLoss();
            // update current price
            try {
                portSvc.updateCurrPrice(stock.getSymbol(), latestHolding.getCurrentPrice());
            } catch (PortfolioException e) {
                e.printStackTrace();
                mvc.addObject("errorMessage", "Unable to get current price. The system is down.");
                mvc.setStatus(HttpStatus.BAD_REQUEST);
                mvc.setViewName("error");
                return mvc;
            }

            updatedPriceAndPercentage.add(latestHolding);

        }
        mvc.addObject("profitLoss", profitLoss);
        mvc.addObject("aum", assetsUnderMgmt);
        mvc.addObject("holdings", updatedPriceAndPercentage);
        mvc.addObject("treeMapData", updatedPriceAndPercentage);
        mvc.addObject("portfolioId", portfolioId);
        mvc.addObject("authUser", authUser);
        mvc.setStatus(HttpStatus.OK);

        return mvc;

    }

    @GetMapping(path = "/protected/search")
    public ModelAndView searchStockFinancials(@RequestParam (name="stock") String symbol) {

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("stockinfo");
       
        Stock stock = new Stock();
        try {
            Optional<Stock> optStock = portSvc.retrieveStockFinancialsByTicker(symbol);

            if (optStock.isEmpty()) {
                mvc.setStatus(HttpStatus.BAD_REQUEST);
                mvc.setViewName("error");
                return mvc;
            }

            stock = optStock.get();

        } catch (Exception e) {
            e.printStackTrace();
            mvc.addObject("errorMessage", "Stock not found");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            return mvc;
        }
        
       
        mvc.addObject("stock", stock);
        mvc.setStatus(HttpStatus.OK);

        return mvc;

    }

}
