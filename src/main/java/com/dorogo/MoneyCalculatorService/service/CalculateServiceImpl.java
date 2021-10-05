package com.dorogo.MoneyCalculatorService.service;

import com.dorogo.MoneyCalculatorService.model.Member;
import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalculateServiceImpl implements CalculateService{

    public String process(List<Member> list) {
        StringBuilder resultSb = new StringBuilder();
        //calculate all amount
        MyBigDecimal amount = list
                .stream()
                .map(Member::getSpent)
                .reduce(MyBigDecimal::add)
                .get();

        MyBigDecimal countPerOne = amount.divide(BigDecimal.valueOf(list.size()));
        log.info("amount = " + amount+ " : per one = " +countPerOne);


        list.forEach(member -> member.setChange(member.getSpent().subtract(countPerOne)));
        printCurrentState(list);

        //TODO sort list by change and spent

        //temp
        List<Member> listTarget = new ArrayList<>(list);
        Collections.reverse(listTarget);
        listTarget = refreshListTarget(listTarget);

        //end temp

        for (Member m : list) {
            m.getChange().setScale(2, RoundingMode.HALF_DOWN);
            if (m.getChange().compareTo(BigDecimal.ZERO) >= 0) continue;
            while (m.getChange().compareTo(BigDecimal.ZERO) < 0 && listTarget.size() > 0) {
                String s = m.sentTo(listTarget.get(0));
                resultSb.append(s).append('\n');
                printCurrentState(list);
                listTarget = refreshListTarget(listTarget);
            }
        }
        String resultStr = resultSb.toString().trim();
        if (resultStr.isEmpty())
            resultStr = "Все и так ок %)";
        return resultStr;
    }
    private List<Member> refreshListTarget(List<Member> list) {
        List<Member> res = list.stream().filter(m -> m.getChange().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
        log.info("res=" + res);
        return res;
//        return list.stream().filter(m -> m.getChange() > 0).collect(Collectors.toList());
    }

    private void printCurrentState(List<Member> list) {
        list.forEach(member -> {
            log.info(member.getName()+"=" +member.getSpent()+":" + member.getChange());
        });
        log.info("________________");
    }



}
