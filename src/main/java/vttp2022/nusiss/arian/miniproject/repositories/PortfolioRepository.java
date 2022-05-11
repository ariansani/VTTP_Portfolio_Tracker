package vttp2022.nusiss.arian.miniproject.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.nusiss.arian.miniproject.models.Holdings;

@Repository
public class PortfolioRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean initializePortfolio(String portfolioId, Integer userId) {
        int count = template.update(Queries.SQL_INSERT_NEW_PORTFOLIO, portfolioId, userId);

        return 1 == count;
    }

    public Optional<String> findPortfolioByUserId(Integer userId){
        
        final SqlRowSet rs = template.queryForRowSet(Queries.SQL_FIND_PORTFOLIO_BY_USERID, userId);
        if (!rs.next())
            return Optional.empty();

        return Optional.of(rs.getString("portfolio_id"));
  
    }

    public Optional<List<Holdings>> findHoldingsByPortfolioId(String portfolioId){

        List<Holdings> holdings = new LinkedList<>();

        
        final SqlRowSet rs = template.queryForRowSet(Queries.SQL_SELECT_HOLDINGS_BY_PORTFOLIO_ID, portfolioId, true);

        while (rs.next()) {
            Holdings holding = new Holdings();
            holding.setId(rs.getInt("holding_id"));
            holding.setPortfolioId(rs.getString("portfolio_id"));
            holding.setSymbol(rs.getString("symbol"));
            holding.setQuantity(rs.getInt("quantity"));
            holding.setCostBasis(rs.getFloat("cost_basis"));
            holding.setCurrentPrice(rs.getDouble("current_price"));
            
            holdings.add(holding);
        }

        return Optional.of(holdings);

        
    }

    public boolean updateCurrPrice(String symbol, Double currentPrice) {
        int count = template.update(Queries.SQL_UPDATE_SYMBOLS_CURRENT_PRICE,currentPrice,symbol, true);

        return count > 0;
    }

    public boolean addHoldingToPortfolio(Holdings holding) {
        int count = template.update(Queries.SQL_INSERT_NEW_HOLDING_TO_PORTFOLIO, holding.getPortfolioId(),holding.getSymbol(),holding.getQuantity(),holding.getCostBasis(),holding.getCurrentPrice(),holding.getIsActive());

        return 1 == count;
    }

    public boolean editHolding(Holdings holding) {
        int count = template.update(Queries.SQL_UPDATE_HOLDING, holding.getQuantity(),holding.getCostBasis(),holding.getId());

        return 1 == count;
    }

    public boolean deactivateHolding(Holdings holding) {
        int count = template.update(Queries.SQL_SOFT_DELETE_HOLDING,false,holding.getId());

        return 1 == count;
    }
    

    


}
