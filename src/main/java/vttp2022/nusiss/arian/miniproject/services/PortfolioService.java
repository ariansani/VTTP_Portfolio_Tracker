package vttp2022.nusiss.arian.miniproject.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.nusiss.arian.miniproject.exceptions.PortfolioException;
import vttp2022.nusiss.arian.miniproject.repositories.PortfolioRepository;

@Service
public class PortfolioService {
    
    @Autowired
    private PortfolioRepository portfolioRepo;

    public String findPortfolioByUserId(Integer userId) throws PortfolioException {

        Optional<String> optPort = portfolioRepo.findPortfolioByUserId(userId); 
        if (optPort.isEmpty())
            throw new PortfolioException("Unable to find portfolio for %s".formatted(userId));

        return optPort.get();
    }


}
