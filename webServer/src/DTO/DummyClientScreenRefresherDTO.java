package DTO;

import client.Loan;
import client.Notification;
import client.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DummyClientScreenRefresherDTO {
    private ClientScreenRefresherDTO clientScreenRefresherDTO;
    private List<Loan> lenderLoansList;

    public DummyClientScreenRefresherDTO(ClientScreenRefresherDTO clientScreenRefresherDTO, List<Loan> lenderLoansList)
    {
        this.clientScreenRefresherDTO = clientScreenRefresherDTO;
        this.lenderLoansList = new ArrayList<>(lenderLoansList);
    }

    public ClientScreenRefresherDTO getClientScreenRefresherDTO() {
        return clientScreenRefresherDTO;
    }

    public List<Loan> getLenderLoansList() {
        return lenderLoansList;
    }
}
