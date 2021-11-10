package com.dorogo.MoneyCalculatorService.controllers.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {
    @NotBlank
    private String name;

    @Min(0L)
    private int spent;

}
