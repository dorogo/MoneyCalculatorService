package com.dorogo.MoneyCalculatorService.service;

import com.dorogo.MoneyCalculatorService.model.Member;

import java.util.List;

public interface CalculateService {

    String process(List<Member> list);
}
