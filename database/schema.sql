DROP SCHEMA IF EXISTS portfoliotracker;

CREATE SCHEMA portfoliotracker;

USE portfoliotracker;


CREATE TABLE user(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(256) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at timestamp DEFAULT current_timestamp(),
    updated_at timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE portfolio(
    portfolio_id CHAR(8) NOT NULL,
    user_id INT,
    PRIMARY KEY (portfolio_id),
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE holdings(
    holding_id INT NOT NULL AUTO_INCREMENT,
    portfolio_id CHAR(8) NOT NULL,
    symbol CHAR(10) NOT NULL,
    quantity INT NOT NULL,
    cost_basis DECIMAL(19, 2) NOT NULL,
    current_price DECIMAL(10, 2),
    is_active BOOLEAN NOT NULL,
    created_at timestamp DEFAULT current_timestamp(),
    updated_at timestamp,
  
    PRIMARY KEY (holding_id),
    constraint fk_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolio(portfolio_id)
);