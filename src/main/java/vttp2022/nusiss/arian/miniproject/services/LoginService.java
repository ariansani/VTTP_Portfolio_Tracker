package vttp2022.nusiss.arian.miniproject.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.nusiss.arian.miniproject.exceptions.UserException;
import vttp2022.nusiss.arian.miniproject.models.User;
import vttp2022.nusiss.arian.miniproject.repositories.LoginRepository;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private PortfolioRepository portfolioRepo;

    public User authenticate(User user) {
        return loginRepo.authenticate(user);
    }

    @Transactional(rollbackFor = UserException.class)
    public boolean insertUser(String username, String password) throws UserException {

        Optional<User> opt = loginRepo.findUserByUserName(username);

        if (opt.isPresent())
            throw new UserException("%s already exists".formatted(username));

        //inserting into user table
        Integer userId = loginRepo.insertUser(username, password);
        if (userId < 0 || userId == null)
            throw new UserException("Cannot add %s as User. Please check with admin".formatted(username));

        String generatedUUID = UUID.randomUUID().toString().substring(0, 8);


        Boolean success = portfolioRepo.initializePortfolio(generatedUUID, userId);
        //inserting into portfolio table
        return success;

    }

}
