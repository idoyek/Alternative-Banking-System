package DTO;

import client.Loan;

import java.util.ArrayList;
import java.util.List;

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
