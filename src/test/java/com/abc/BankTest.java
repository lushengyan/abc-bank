package com.abc;

import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final BigDecimal DOUBLE_DELTA = new BigDecimal (1e-15);

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING, "C00001", john, new BigDecimal(0)));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = new Account(Account.CHECKING, "C0002", bill, new BigDecimal(0));
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(new BigDecimal(100));

        assertEquals(new BigDecimal(0.1), bank.totalInterestPaid(), DOUBLE_DELTA );
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        Account checkingAccount = new Account(Account.SAVINGS, "S00001", bill, new BigDecimal(0));
        bill.openAccount(checkingAccount);
        checkingAccount.deposit(new BigDecimal(1500.0));

        assertEquals(new BigDecimal (2.0), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        Account checkingAccount = new Account(Account.MAXI_SAVINGS, "M000001", bill, new BigDecimal(0));
        bill.openAccount(checkingAccount);

        checkingAccount.deposit(new BigDecimal(3000.0));

        assertEquals(new BigDecimal(170.0), bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
