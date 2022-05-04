package vttp2022.nusiss.arian.miniproject.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.nusiss.arian.miniproject.models.User;

@Repository
public class LoginRepository {
    
    @Autowired
    private JdbcTemplate template;

    public Integer insertUser(String username, String password) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(Queries.SQL_INSERT_NEW_USER_BY_USERPASS,
            Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setBoolean(3, true);
            return ps;
            
        },keyHolder);
        
        BigInteger bigint = (BigInteger) keyHolder.getKey();
        return bigint.intValue();
    }
    
    public Optional<User> findUserByUserName(String username) {
        
        final SqlRowSet rs = template.queryForRowSet(Queries.SQL_FIND_EXISTING_USER, username,true);
        if (!rs.next())
            return Optional.empty();

        return Optional.of(User.create(rs));
    }

    public User authenticate(User userInput){
        
        final SqlRowSet rs = template.queryForRowSet(
            Queries.SQL_SELECT_ACTIVE_USER_BY_USERPASS,userInput.getUsername(),userInput.getPassword(),true
        );

        
        if(!rs.next()){
            return null;

        }
        return User.create(rs);
       

    }

}
