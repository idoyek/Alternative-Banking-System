package DTO;

import client.*;

import java.util.List;

public class AdminScreenRefresherDTO {
    private List<Client> clientsList;
    private List<Loan> loansList;
    private Yaz yaz;
    boolean isInRewindMode;

    public AdminScreenRefresherDTO(List<Client> clientsList, List<Loan> loansList, Yaz yaz, boolean isInRewindMode)
    {
        this.clientsList = clientsList;
        this.loansList = loansList;
        this.yaz = yaz;
        this.isInRewindMode = isInRewindMode;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public List<Loan> getLoansList() {
        return loansList;
    }

    public Yaz getYaz() {
        return yaz;
    }

    public boolean getIsInRewindMode() {
        return isInRewindMode;
    }

    @Override
    public String toString() {
        return "AdminScreenRefresherDTO{" +
                "clientsList=" + clientsList +
                ", loansList=" + loansList +
                ", yaz=" + yaz +
                '}';
    }
}
