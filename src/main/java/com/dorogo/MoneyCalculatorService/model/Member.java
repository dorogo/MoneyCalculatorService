package com.dorogo.MoneyCalculatorService.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Member {

    private String name;
    //TODO переделать на целочисленный, пока устраивает double c 0.0000001.
    private double spent;
    private double change;

    public Member(String name, double spent) {
        this.name = name;
        this.spent = spent;
    }

    public String sentTo(Member mTarget) {
        double cTarget = mTarget.getChange();
        if (cTarget <= 0) return "";
        double res = this.change + cTarget;
        double delta = (res <= 0) ? cTarget : Math.abs(this.change);
        mTarget.addMoney(-delta);
        this.addMoney(delta);

        String resStr =  "'"+ this.getName() +"' должен выдать '" + mTarget.getName()
                +"' "+ delta + ".";

        System.out.println(resStr);
        return resStr;
    }

    public void addMoney(double v) {
        this.change += v;
        this.spent += v;
    }
}
