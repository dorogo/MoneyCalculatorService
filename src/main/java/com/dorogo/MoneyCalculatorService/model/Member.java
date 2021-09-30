package com.dorogo.MoneyCalculatorService.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Member {

    private String name;
    private BigDecimal spent;
    private BigDecimal change;

    public Member(String name, BigDecimal spent) {
        this.name = name;
        this.spent = spent;
    }

    public String sentTo(Member mTarget) {
        BigDecimal cTarget = mTarget.getChange();
        if (cTarget.compareTo(BigDecimal.ZERO) <= 0)
            return "";
        BigDecimal res = this.change.add(cTarget);
        BigDecimal delta = (res.compareTo(BigDecimal.ZERO) <= 0) ? cTarget : this.change.abs();
        mTarget.addMoney(delta.negate());
        this.addMoney(delta);

        String resStr =  "'"+ this.getName() +"' должен выдать '" + mTarget.getName()
                +"' "+ delta + ".";

        System.out.println(resStr);
        return resStr;
    }

    public void addMoney(BigDecimal v) {
        this.change = this.change.add(v);
        this.spent = this.spent.add(v);
    }
}
