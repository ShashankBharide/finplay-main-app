CREATE TABLE stock (
    stock_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    date DATETIME NOT NULL,
    open FLOAT,
    high FLOAT,
    low FLOAT,
    close FLOAT,
    volume BIGINT,
    day_change FLOAT,
    day_change_percent FLOAT,
    dividends FLOAT,
    stock_splits FLOAT,
    sector VARCHAR(255),
    description TEXT,
    UNIQUE (symbol, date)
);
CREATE TABLE portfolios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_investment DOUBLE NOT NULL,
    current_value DOUBLE NOT NULL,
    total_pnl DOUBLE NOT NULL
);
CREATE TABLE portfolio_stocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
    stock_symbol VARCHAR(255) NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    quantity DOUBLE NOT NULL,
    avg_cost DOUBLE NOT NULL,
    current_value DOUBLE NOT NULL,
    pnl DOUBLE NOT NULL,
    net_change DOUBLE,
    day_change DOUBLE,
    FOREIGN KEY (portfolio_id) REFERENCES portfolios(id) ON DELETE CASCADE
);
CREATE TABLE trades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    stock_symbol VARCHAR(255) NOT NULL,
    trade_type VARCHAR(50) NOT NULL, -- BUY or SELL
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    total_amount DOUBLE NOT NULL,
    trade_date DATETIME NOT NULL
);
CREATE TABLE wallets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    balance DOUBLE NOT NULL
);
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    transaction_type VARCHAR(50) NOT NULL, -- CREDIT or DEBIT
    amount DOUBLE NOT NULL,
    transaction_date DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL, -- SUCCESS, FAILED, etc.
    FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE
);
