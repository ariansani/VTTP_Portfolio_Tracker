package vttp2022.nusiss.arian.miniproject.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;

@SpringBootTest
public class UserTest {

    @Autowired
    private JdbcTemplate template;

    @Mock
    private SqlRowSet rs;

    User user = new User();

    @Test
    void testGetCreatedAt() {

        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        user.setCreatedAt(createdAt);
        assertEquals(createdAt, user.getCreatedAt());
    }

    @Test
    void testGetIsActive() {
        Boolean isActive = true;
        user.setIsActive(isActive);
        assertEquals(isActive, user.getIsActive());
    }

    @Test
    void testGetPassword() {
        String pw = "ari";
        user.setPassword(pw);
        assertEquals(pw, user.getPassword());
    }

    @Test
    void testGetPortfolioId() {
        String portfolioId = "asdasd";
        user.setPortfolioId(portfolioId);
        assertEquals(portfolioId, user.getPortfolioId());
    }

    @Test
    void testGetUpdatedAt() {
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        user.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, user.getUpdatedAt());

    }

    @Test
    void testGetUserId() {
        Integer userId = 1;
        user.setUserId(userId);
        assertEquals(userId, user.getUserId());
    }

    @Test
    void testGetUsername() {
        String username = "ari";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    void testCreate() {


        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("password")).thenReturn("ari");
        when(rs.getString("username")).thenReturn("ari");
        
        User createUser = User.create(rs);

        // createUser.setUserId(rs.getInt("id"));
        // createUser.setUsername(rs.getString("username"));
        // createUser.setPassword(rs.getString("password"));

        assertEquals("ari", createUser.getUsername());
    }
}
