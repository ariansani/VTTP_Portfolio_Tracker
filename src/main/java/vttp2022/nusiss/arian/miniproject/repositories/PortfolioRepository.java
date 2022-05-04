package vttp2022.nusiss.arian.miniproject.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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


}
