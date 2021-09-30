package com.dorogo.MoneyCalculatorService.controllers.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class MemberForm {
    @NotBlank
    private String name;

    @Min(0L)
    private int spent;

}
