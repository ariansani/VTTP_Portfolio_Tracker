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
        String portfolioId = authUser.getPortfolioId();
        List<Holdings> holdings = new LinkedList<>();

        try {
            holdings = portSvc.findHoldingsByPortfolioId(portfolioId);
        } catch (PortfolioException e) {
            e.printStackTrace();
            mvc.addObject("errorMessage", e.getReason());
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            return mvc;
        }

        for (Holdings holding : holdings) {

            holding.setProfitLoss(holding.getCurrentPrice() * holding.getQuantity()
                    - holding.getCostBasis() * holding.getQuantity());


            holding.setTotalValue(holding.getCurrentPrice() * holding.getQuantity());
        }

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

        // String username = sess.getAttribute("username").toString();
        User authUser = (User) sess.getAttribute("authUserSess");
        String portfolioId = authUser.getPortfolioId();
        List<Holdings> holdings = new LinkedList<>();
        try {
            holdings = portSvc.findHoldingsByPortfolioId(portfolioId);
        } catch (PortfolioException e) {
            e.printStackTrace();
            mvc.addObject("errorMessage", e.getReason());
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            return mvc;
        }

        List<Holdings> updatedPriceAndPercentage = new LinkedList<>();

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
            
            // update current price
            try {
                portSvc.updateCurrPrice(stock.getSymbol(), latestHolding.getCurrentPrice());
            } catch (PortfolioException e) {
                e.printStackTrace();
                mvc.addObject("errorMessage", e.getReason());
                mvc.setStatus(HttpStatus.BAD_REQUEST);
                mvc.setViewName("error");
                return mvc;
            }

            updatedPriceAndPercentage.add(latestHolding);

        }

        mvc.addObject("holdings", updatedPriceAndPercentage);
        mvc.addObject("treeMapData", updatedPriceAndPercentage);
        mvc.addObject("portfolioId", portfolioId);
        mvc.addObject("authUser", authUser);
        mvc.setStatus(HttpStatus.OK);

        return mvc;

    }

}
