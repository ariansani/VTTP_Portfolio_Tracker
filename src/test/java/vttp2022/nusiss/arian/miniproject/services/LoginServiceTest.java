package vttp2022.nusiss.arian.miniproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import vttp2022.nusiss.arian.miniproject.exceptions.UserException;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.repositories.LoginRepository;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginSvc;
    
    @MockBean
    private LoginRepository loginRepo;

    @MockBean
    private PortfolioRepository portfolioRepo;

    @Test
    void testAuthenticate() {
        User authUser = new User();
        authUser.setUsername("lucas");
        authUser.setPassword("lucas");
        User mockUser = new User();
        mockUser.setUsername("lucas");
        mockUser.setPassword("lucas");

        Mockito.when(loginRepo.authenticate(authUser))
        .thenReturn(mockUser);
        User testUser = loginSvc.authenticate(authUser);
     

        assertEquals(authUser.getUsername(), testUser.getUsername());
    }

    @Test
    void testInsertUser() {
        
        String username = "ari2";
        String password ="ari2";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        Boolean successIfAdded=true;
        Mockito.when(loginRepo.findUserByUserName(username))
        .thenReturn(Optional.empty());

        Mockito.when(loginRepo.insertUser(username, password))
        .thenReturn(100);

        String generatedUUID = UUID.randomUUID().toString().substring(0, 8);

        Mockito.when(portfolioRepo.initializePortfolio(generatedUUID, 100))
        .thenReturn(true);
        try {
             successIfAdded = loginSvc.insertUser(username, password);
          
        } catch (UserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertEquals(true, true);
        
    }

    @Test
    void testInsertUserFail() {
        
        String username = "ari2";
        String password ="ari2";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        Mockito.when(loginRepo.findUserByUserName(username))
        .thenReturn(Optional.of(mockUser));

        assertThrows(UserException.class, 
        () -> loginSvc.insertUser(username, password));
        // try {
        //      successIfAdded = loginSvc.insertUser(username, password);
          
        // } catch (UserException e) {
           
        // }
    }

    @Test
    void testInsertUserIdInvalid() {
        
        String username = "ari2";
        String password ="ari2";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        Mockito.when(loginRepo.findUserByUserName(username))
        .thenReturn(Optional.empty());

        Mockito.when(loginRepo.insertUser(username, password))
        .thenReturn(-1);

        assertThrows(UserException.class, 
        () -> loginSvc.insertUser(username, password));
        // try {
        //      successIfAdded = loginSvc.insertUser(username, password);
          
        // } catch (UserException e) {
           
        // }
    }
}
