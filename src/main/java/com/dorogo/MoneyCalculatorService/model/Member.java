package com.dorogo.MoneyCalculatorService.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Member {

    @NotBlank
    private final String name;
    //TODO переделать на целочисленный, пока устраивает double c 0.0000001.
    @Min(0L)
    private double spent;
    private double change;

    public Member(String name, double spent) {
        this.name = name;
        this.spent = spent;
    }

    public void sentTo(Member mTarget) {
        double cTarget = mTarget.getChange();
        if (cTarget <= 0) return;
        double res = this.change + cTarget;
        double delta = (res <= 0) ? cTarget : Math.abs(this.change);
        mTarget.addMoney(-delta);
        this.addMoney(delta);

        System.out.println("Member.sentTo(). "+ this.getName() +" gave to " + mTarget.getName()
                +" "+ delta);
    }

    public void addMoney(double v) {
        this.change += v;
        this.spent += v;
    }
}
