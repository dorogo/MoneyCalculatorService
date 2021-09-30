package com.dorogo.MoneyCalculatorService.model;

import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Member {

    private String name;
    private MyBigDecimal spent;
    private MyBigDecimal change;

    public Member(String name, MyBigDecimal spent) {
        this.name = name;
        this.spent = spent;
    }

    public String sentTo(Member mTarget) {
        MyBigDecimal cTarget = mTarget.getChange();
        if (cTarget.compareTo(MyBigDecimal.ZERO) <= 0)
            return "";
        MyBigDecimal res = this.change.add(cTarget);
        MyBigDecimal delta = (res.compareTo(MyBigDecimal.ZERO) <= 0) ? cTarget : this.change.abs();
        mTarget.addMoney(delta.negate());
        this.addMoney(delta);

        String resStr =  "'"+ this.getName() +"' должен выдать '" + mTarget.getName()
                +"' "+ delta + ".";

        System.out.println(resStr);
        return resStr;
    }

    public void addMoney(MyBigDecimal v) {
        this.change = this.change.add(v);
        this.spent = this.spent.add(v);
    }
}
