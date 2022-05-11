package vttp2022.nusiss.arian.miniproject.repositories;

import org.springframework.jdbc.core.PreparedStatementCreator;

public interface Queries {

    public static final String SQL_SELECT_ACTIVE_USER_BY_USERPASS = "SELECT * FROM user WHERE username = ? AND password = sha1(?) AND is_active = ?";
    public static final String SQL_INSERT_NEW_USER_BY_USERPASS = "INSERT INTO user (username, password, is_active) VALUES (?,sha1(?), ?)";
    public static final String SQL_FIND_EXISTING_USER = "SELECT * FROM user WHERE username LIKE ? AND is_active = ?";
    public static final String SQL_INSERT_NEW_PORTFOLIO = "INSERT INTO portfolio (portfolio_id, user_id) VALUES (?,?)";
    public static final String SQL_FIND_PORTFOLIO_BY_USERID =  "SELECT * FROM portfolio WHERE user_id = ?";
    public static final String SQL_SELECT_HOLDINGS_BY_PORTFOLIO_ID = "SELECT * FROM holdings WHERE portfolio_id = ? AND is_active = ?";
    public static final String SQL_UPDATE_SYMBOLS_CURRENT_PRICE = "UPDATE holdings SET current_price = ? WHERE symbol = ? AND is_active = ?";
    public static final String SQL_INSERT_NEW_HOLDING_TO_PORTFOLIO = "INSERT INTO holdings(portfolio_id, symbol, quantity, cost_basis, current_price, is_active) VALUES(?,?,?,?,?,?);";
    public static final String SQL_UPDATE_HOLDING = "UPDATE holdings SET quantity= ?, cost_basis=?, updated_at=current_timestamp() WHERE holding_id=?";
    public static final String SQL_SOFT_DELETE_HOLDING = "UPDATE holdings SET is_active= ?,  updated_at=current_timestamp() WHERE holding_id=?";

}
