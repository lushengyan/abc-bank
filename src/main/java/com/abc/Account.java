package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    //interest rates and balance thresholds need to be pre-declared
    public static final BigDecimal CHECKING_RATE = new BigDecimal(0.001);
    public static final BigDecimal SAVING_BASE_RATE = new BigDecimal(0.001);
    public static final BigDecimal SAVING_PREMIUM_RATE = new BigDecimal(0.002);
    public static final BigDecimal MAXI_BASE_RATE = new BigDecimal(0.02);
    public static final BigDecimal MAXI_PREFERRED_RATE = new BigDecimal(0.05);
    public static final BigDecimal MAXI_PREMIUM_RATE = new BigDecimal(0.1);
    public static final BigDecimal PREFERRED_ACCOUNT_BALANCE = new BigDecimal(1000);
    public static final BigDecimal PREMIUM_ACCOUNT_BALANCE = new BigDecimal(2000);
    
    //precalculate the interest rates when balances are over 1000, 2000
    public static BigDecimal SAVING_BASE_INTEREST = SAVING_BASE_RATE.multiply(PREFERRED_ACCOUNT_BALANCE);
    public static BigDecimal MAXI_BASE_INTEREST = MAXI_BASE_RATE.multiply(PREFERRED_ACCOUNT_BALANCE);
    public static BigDecimal MAXI_PREFERRED_INTEREST = MAXI_BASE_RATE.add(MAXI_PREFERRED_RATE.multiply(PREMIUM_ACCOUNT_BALANCE));

    private final int accountType;
    private String accountNumber;
    private Customer owner;
    private volatile BigDecimal balance;
    private List<Transaction> transactions;

    private Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    public Account(int accountType, String accountNumber, Customer owner, BigDecimal initialBalance)
    {
    	this(accountType);
    	this.accountNumber  = accountNumber;
    	this.balance = initialBalance;
    	this.owner = owner;
    	
    }
    public void accrueInterestDaily()
    {
    	BigDecimal interest;
    	synchronized (this)
    	{
    		 interest = interestEarned();
    		transactions.add(new Transaction(interest));
    	}
    	balance =( balance.add(interest) ).divide(new BigDecimal(365));
    }
    public void deposit(BigDecimal amount) {
        if (amount.signum() == 0 || amount.signum() == -1) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	balance = balance.add(amount);
        	synchronized(this)
        	{
        		transactions.add(new Transaction(amount));
        	}
        }
    }

public void withdraw(BigDecimal amount) {
    if (amount.signum() == 0 || amount.signum() ==-1) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
    	balance= balance.subtract(amount);
    	synchronized (this)
    	{
    		transactions.add(new Transaction(amount.negate()));
    	}
    }
}

    public BigDecimal interestEarned() {
        BigDecimal amount = getBalance();
        switch(accountType){
            case SAVINGS:
                if ( amount.compareTo(PREFERRED_ACCOUNT_BALANCE) <=0 )
                    return amount.multiply( SAVING_BASE_RATE);
                else {
                	BigDecimal over = (amount.subtract(PREFERRED_ACCOUNT_BALANCE)).multiply(SAVING_PREMIUM_RATE);
                    return SAVING_BASE_INTEREST.add(over);
                }
      
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount.compareTo(PREFERRED_ACCOUNT_BALANCE) <= 0)
                    return amount.multiply(MAXI_BASE_RATE);
                else if (amount.compareTo(PREMIUM_ACCOUNT_BALANCE) <= 0)
                {
                    BigDecimal over = (amount.subtract(PREFERRED_ACCOUNT_BALANCE)).multiply(MAXI_PREFERRED_RATE);
                    return MAXI_BASE_INTEREST.add(over);
                }else
                {
                	return MAXI_PREFERRED_INTEREST.add( (amount.subtract(PREMIUM_ACCOUNT_BALANCE)).multiply(MAXI_PREMIUM_RATE) );
                }
        
            default:
                return amount.multiply(CHECKING_RATE);
        }
    }

    public synchronized BigDecimal sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = new BigDecimal(0);
        for (Transaction t: transactions)
            amount.add(t.getAmount());
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
	public String getAccountNumber() {
		return accountNumber;
	}
	public Customer getOwner() {
		return owner;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}


}
