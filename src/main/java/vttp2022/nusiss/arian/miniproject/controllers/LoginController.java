package vttp2022.nusiss.arian.miniproject.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.exceptions.UserException;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.services.LoginService;
import vttp2022.nusiss.arian.miniproject.services.PortfolioService;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginSvc;

    @Autowired
    private PortfolioService portSvc;

    @GetMapping
    public String loadIndex() {
        return "index";
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession sess) {
        sess.invalidate();
        return "index";
    }

    @GetMapping("/signup")
    public String loadSignUp() {
        return "signup";
    }

    @PostMapping(path = "/authenticate")
    public ModelAndView authenticateLogin(@RequestBody MultiValueMap<String, String> form, HttpSession sess) {

        ModelAndView mvc = new ModelAndView();

        User optUser = create(form);

        User authUser = loginSvc.authenticate(optUser);

        if (authUser == null) {
            mvc.setStatus(HttpStatus.UNAUTHORIZED);
            mvc.setViewName("error");
            return mvc;
        }


        String portfolioId="";
        try {
             portfolioId =  portSvc.findPortfolioByUserId(authUser.getUserId());
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        authUser.setPortfolioId(portfolioId);
        sess.setAttribute("userId", authUser.getUserId());
        sess.setAttribute("username", authUser.getUsername());
        sess.setAttribute("authUserSess", authUser);

        //mvc.addObject("authUser", authUser);
        mvc = new ModelAndView("redirect:/protected/dashboard");


        return mvc;

    }

    @PostMapping(path = "/createAccount")
    public ModelAndView createAccount(@RequestBody MultiValueMap<String, String> form, HttpSession sess) {

        ModelAndView mvc = new ModelAndView();

        User optUser = create(form);

        try {
            if(!loginSvc.insertUser(optUser.getUsername(),optUser.getPassword()))
            {
                mvc.setStatus(HttpStatus.UNAUTHORIZED);
                mvc.setViewName("error");
                return mvc;
            }
        } catch (UserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        User authUser = loginSvc.authenticate(optUser);

        if (authUser == null) {
            mvc.setStatus(HttpStatus.UNAUTHORIZED);
            mvc.setViewName("error");
            return mvc;
        }


        String portfolioId="";
        try {
             portfolioId =  portSvc.findPortfolioByUserId(authUser.getUserId());
        } catch (PortfolioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        authUser.setPortfolioId(portfolioId);
        sess.setAttribute("userId", authUser.getUserId());
        sess.setAttribute("username", authUser.getUsername());
        sess.setAttribute("authUserSess", authUser);

        mvc = new ModelAndView("redirect:/protected/dashboard");

        return mvc;

    }

    @GetMapping(path = "/manage")
    public ModelAndView managePortfolio(HttpSession sess) {

        return new ModelAndView("redirect:/protected/manage/portfolio");

    }

    private User create(MultiValueMap<String, String> form) {

        User user = new User();
        try {
            user.setUsername(form.getFirst("username"));
            user.setPassword(form.getFirst("password"));

        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();

        }
        return user;
    }
}
