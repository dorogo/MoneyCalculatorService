package com.dorogo.MoneyCalculatorService.service;

import com.dorogo.MoneyCalculatorService.model.Member;
import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalculateServiceImpl implements CalculateService{

    public Map<Member, Map<Member, BigDecimal>> process(List<Member> list) {
        setupInitial(list);
        Map<Member, Map<Member, BigDecimal>> resultMap = calculate(list);
        return resultMap;
    }

    public String parseResultHuman(Map<Member, Map<Member, BigDecimal>> resultMap) {
        return parseResultHuman(resultMap, false);
    }

    public String parseResultHuman(Map<Member, Map<Member, BigDecimal>> resultMap, boolean isHtml) {
        if (resultMap == null || resultMap.isEmpty())
            return "Все и так ок %)";
        StringBuilder sb = new StringBuilder();
        resultMap.forEach(
            (member, mMap) ->
            {
                for (Map.Entry<Member, BigDecimal> entry : mMap.entrySet()) {
                    String s = String.format("'%s' должен '%s' %s",
                            member.getName(),
                            entry.getKey().getName(),
                            entry.getValue().setScale(2, RoundingMode.HALF_UP));
                    sb.append(s).append(isHtml ? "<br/>" : '\n');
                }
            }
        );
        return sb.toString();
    }

    /**
     * считаем кто кому должен и вохвращаем в виде мапы
     * */
    private Map<Member, Map<Member, BigDecimal>> calculate(List<Member> list) {
        //TODO sort list by change and spent
        //temp
        List<Member> listTarget = new ArrayList<>(list);
        Collections.reverse(listTarget);
        listTarget = refreshListTarget(listTarget);
        //end temp
        Map<Member, Map<Member, BigDecimal>> resultMap = new HashMap<>();
        for (Member m : list) {
            if (m.getChange().compareTo(BigDecimal.ZERO) >= 0) continue;
            while (m.getChange().compareTo(BigDecimal.ZERO) < 0 && listTarget.size() > 0) {
                Member mTarget = listTarget.get(0);
                BigDecimal sent = m.sentTo(mTarget);
                putToResultMap(resultMap, m, mTarget, sent);
                printCurrentState(list);
                listTarget = refreshListTarget(listTarget);
            }
        }
        return resultMap;
    }

    /**
     * m - кто
     * mTarget - кому
     * sent - сколько
     * */
    private void putToResultMap(Map<Member, Map<Member, BigDecimal>> resultMap, Member m, Member mTarget, BigDecimal sent) {
        if (resultMap == null || sent.compareTo(BigDecimal.ZERO) == 0)
            return;
        Map<Member, BigDecimal> map = resultMap.get(m);
        if (map == null)
            map = new HashMap<>();
        map.put(mTarget, sent);
        resultMap.put(m, map);
    }

    /**
     * устанавливаем начальные знеачения объектов перед вычислением
     * */
    private void setupInitial(List<Member> list) {
        MyBigDecimal amount = list
                .stream()
                .map(Member::getSpent)
                .reduce(MyBigDecimal::add)
                .get();

        MyBigDecimal countPerOne = amount.divide(BigDecimal.valueOf(list.size()));
        log.info("amount = " + amount+ " : per one = " +countPerOne);
        list.forEach(member -> member.setChange(member.getSpent().subtract(countPerOne)));
        printCurrentState(list);
    }

    private List<Member> refreshListTarget(List<Member> list) {
        List<Member> res = list.stream().filter(m -> m.getChange().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
        log.info("res=" + res);
        return res;
    }

    private void printCurrentState(List<Member> list) {
        list.forEach(member -> {
            log.info(member.getName()+"=" +member.getSpent()+":" + member.getChange());
        });
        log.info("________________");
    }



}
