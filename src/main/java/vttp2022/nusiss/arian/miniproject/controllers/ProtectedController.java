package vttp2022.nusiss.arian.miniproject.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.nusiss.arian.miniproject.models.User;

@Controller
@RequestMapping(path="/protected/{view}")
public class ProtectedController {
  
    @GetMapping
    @PostMapping
    public ModelAndView post(@PathVariable String view,HttpSession sess){

       
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName(view);
        
        //String username = sess.getAttribute("username").toString();
        User authUser = (User) sess.getAttribute("authUserSess");
        String portfolioId = (String) sess.getAttribute("portfolioId");
        
        //mvc.addObject("username",username);
        
        mvc.addObject("portfolioId",portfolioId);
        mvc.addObject("authUser", authUser);
        mvc.setStatus(HttpStatus.OK);   

        return mvc;

    }
    
}
