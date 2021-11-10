package com.dorogo.MoneyCalculatorService.model;

import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testSentTo() {
        Member m0 = new Member("m0", new MyBigDecimal(2));
        Member m1 = new Member("m1", new MyBigDecimal(0));
        m0.setChange(new MyBigDecimal(0));
        m1.setChange(new MyBigDecimal(0));
        BigDecimal sent = m1.sentTo(m0);
        assertEquals(sent, BigDecimal.ZERO);

        m0.setChange(new MyBigDecimal(2));
        m1.setChange(new MyBigDecimal(-2));
        sent = m1.sentTo(m0);
        assertEquals(sent, new BigDecimal(2));
    }

    @Test
    void testAddMoney() {
        Member m0 = new Member("m0", new MyBigDecimal(4));
        m0.setChange(new MyBigDecimal(0));
        m0.addMoney(new MyBigDecimal("2.5"));
        assertEquals(m0.getChange(), new MyBigDecimal("2.5"));
        assertEquals(m0.getSpent(), new MyBigDecimal("6.5"));
    }
}