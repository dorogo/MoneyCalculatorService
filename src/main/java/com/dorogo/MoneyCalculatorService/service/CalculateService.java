package com.dorogo.MoneyCalculatorService.service;

import com.dorogo.MoneyCalculatorService.model.Member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CalculateService {

    Map<Member, Map<Member, BigDecimal>> process(List<Member> list);

    String parseResultHuman(Map<Member, Map<Member, BigDecimal>> resultMap);

    String parseResultHuman(Map<Member, Map<Member, BigDecimal>> resultMap, boolean isHtml);
}
