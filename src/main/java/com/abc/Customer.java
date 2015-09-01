package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        BigDecimal total = new BigDecimal(0);
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add( a.sumTransactions() );
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        BigDecimal total = new BigDecimal(0);
        for (Transaction t : a.getTransactions()) {
            s += "  " + ( t.getAmount().signum() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total =total.add( t.getAmount() );
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d){
        return String.format("$%,.2f", d.abs());
    }
}
