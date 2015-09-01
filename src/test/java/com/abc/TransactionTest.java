package com.abc;

import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
	private static final BigDecimal DOUBLE_DELTA = new BigDecimal (1e-15);
    @Test
    public void transaction() {
        Transaction t = new Transaction(new BigDecimal(5));
        //assertTrue(t instanceof Transaction);
        assertEquals(new BigDecimal(5), t.getAmount(), DOUBLE_DELTA);
    }
}
