package com.dorogo.MoneyCalculatorService.model;

import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Slf4j
public class Member {

    private String name;
    private MyBigDecimal spent;
    private MyBigDecimal change;

    public Member(String name, MyBigDecimal spent) {
        if (spent.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("spent cant be negative");
        this.name = name;
        this.spent = spent;
    }

    public BigDecimal sentTo(Member mTarget) {
        MyBigDecimal cTarget = mTarget.getChange();
        if (cTarget.compareTo(MyBigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        MyBigDecimal res = this.change.add(cTarget);
        MyBigDecimal delta = (res.compareTo(MyBigDecimal.ZERO) <= 0) ? cTarget : this.change.abs();
        mTarget.addMoney(delta.negate());
        this.addMoney(delta);
        return delta;
    }

    public void addMoney(MyBigDecimal v) {
        this.change = this.change.add(v);
        this.spent = this.spent.add(v);
    }
}
