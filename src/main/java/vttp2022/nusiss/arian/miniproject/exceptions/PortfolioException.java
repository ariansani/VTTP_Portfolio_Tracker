package vttp2022.nusiss.arian.miniproject.exceptions;

public class PortfolioException extends Exception{
    private String reason;

    public PortfolioException(String reason) {
        super();
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
