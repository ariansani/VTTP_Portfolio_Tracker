package vttp2022.nusiss.arian.miniproject.controllers;

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
import org.springframework.web.servlet.ModelAndView;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.models.Holdings;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

@Controller
@RequestMapping(path="/protected/{view}")
public class ProtectedController {
  
    
    @Autowired
    private PortfolioService portSvc;

    @GetMapping
    @PostMapping
    public ModelAndView post(@PathVariable String view,HttpSession sess){

       
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName(view);
        
        //String username = sess.getAttribute("username").toString();
        User authUser = (User) sess.getAttribute("authUserSess");
        String portfolioId = authUser.getPortfolioId();
        List<Holdings> holdings = new LinkedList<>();
        try {
             holdings =  portSvc.findHoldingsByPortfolioId(portfolioId);
        } catch (PortfolioException e) {
            e.printStackTrace();
        }


        List<Holdings> updatedPriceAndPercentage = new LinkedList<>(); 

        for (Holdings stock : holdings) {
            Optional<Holdings> optHolding = portSvc.retrieveHolding(stock.getSymbol(),stock.getId());
           
            if (optHolding.isEmpty()){
                mvc.setStatus(HttpStatus.BAD_REQUEST);
                mvc.setViewName("error");
                return mvc;
            }
            Holdings latestHolding = optHolding.get();
            latestHolding.setQuantity(stock.getQuantity());
            latestHolding.setCostBasis(stock.getCostBasis());
            //update current price
            try {
                portSvc.updateCurrPrice(stock.getSymbol(),latestHolding.getCurrentPrice());
            } catch (PortfolioException e) {
                e.printStackTrace();
            }
            
            updatedPriceAndPercentage.add(latestHolding);
            
       
        }
         //push this latest list to MVC for heatmap
        // System.out.println(updatedPriceAndPercentage.get(0).getSymbol());
        // System.out.println(updatedPriceAndPercentage.get(0).getCurrentPrice());
        // System.out.println(updatedPriceAndPercentage.get(0).getPercentageChange());
           


        mvc.addObject("holdings", updatedPriceAndPercentage);
        //mvc.addObject("username",username);
        mvc.addObject("portfolioId",portfolioId);
        mvc.addObject("authUser", authUser);
        mvc.setStatus(HttpStatus.OK);

        return mvc;

    }
    
}
