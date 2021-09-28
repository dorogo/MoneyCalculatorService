package com.dorogo.MoneyCalculatorService.controllers.form;

import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;

@Data
public class ListForm {

    @Valid
    private ArrayList<MemberForm> list;

}
