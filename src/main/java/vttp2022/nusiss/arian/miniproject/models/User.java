package vttp2022.nusiss.arian.miniproject.models;

import java.sql.Timestamp;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {
    private Integer userId;
    private String username;
    private String password;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String portfolioId;

 
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }


    public static User create(SqlRowSet rs){
        User user = new User();

        user.userId = rs.getInt("id");
        user.username = rs.getString("username");
        user.password = rs.getString("password");


        return user;
    }

}
