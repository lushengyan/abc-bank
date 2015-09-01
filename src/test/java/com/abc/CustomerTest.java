package com.abc;

import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

    	Customer henry = new Customer("Henry");
        Account checkingAccount = new Account(Account.CHECKING, "C0001", henry, new BigDecimal(0));
        Account savingsAccount = new Account(Account.SAVINGS,"S0001", henry, new BigDecimal(0));

        henry.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(new BigDecimal(100.0));
        savingsAccount.deposit( new BigDecimal (4000.0));
        savingsAccount.withdraw(new BigDecimal (200.0));

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS, "S00002", oscar, new BigDecimal(0)));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS, "S00004", oscar, new BigDecimal(0)));
        oscar.openAccount(new Account(Account.CHECKING, "C00004", oscar, new BigDecimal(0)));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS, "S00001", oscar, new BigDecimal(0)));
        oscar.openAccount(new Account(Account.CHECKING, "C00001", oscar, new BigDecimal(0)));
        oscar.openAccount(new Account(Account.CHECKING, "C00002", oscar, new BigDecimal(0)));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
