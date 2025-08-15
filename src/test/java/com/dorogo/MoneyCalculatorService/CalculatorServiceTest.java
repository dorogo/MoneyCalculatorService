package com.dorogo.MoneyCalculatorService;

import com.dorogo.MoneyCalculatorService.model.Member;
import com.dorogo.MoneyCalculatorService.service.CalculateService;
import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CalculatorServiceTest {

    @Autowired
    CalculateService calculateService;

    private MathContext mathContext = MyBigDecimal.context;

    @Test
    public void testCalculate() {
        Member a = new Member("a", new MyBigDecimal(10));
        Member b = new Member("b", new MyBigDecimal(2));
        Member c = new Member("c", new MyBigDecimal(0));
        List<Member> memberList = Stream.of(a, b, c).collect(Collectors.toList());
        Map<Member, Map<Member, BigDecimal>> result = calculateService.process(memberList);
        //res
        Map<Member, Map<Member, BigDecimal>> sampleResult = new HashMap<>();
        //b
        Map<Member, BigDecimal> map = new HashMap<>();
        map.put(a, new BigDecimal(2));
        sampleResult.put(b, map);
        //c
        map = new HashMap<>();
        map.put(a, new BigDecimal(4));
        sampleResult.put(c, map);
        assertThat(result, equalTo(sampleResult));
        /*--------------------------------------------------------------------------*/
        a = new Member("a", new MyBigDecimal(10));
        Member d = new Member("d", new MyBigDecimal(8));
        b = new Member("b", new MyBigDecimal(2));
        c = new Member("c", new MyBigDecimal(0));
        memberList = Stream.of(a, b, c, d).collect(Collectors.toList());
        result = calculateService.process(memberList);
        //res
        sampleResult = new HashMap<>();
        //b
        map = new HashMap<>();
        map.put(d, new BigDecimal(3, mathContext));
        sampleResult.put(b, map);
        //c
        map = new HashMap<>();
        map.put(a, new BigDecimal(5, mathContext));
        sampleResult.put(c, map);
        assertThat(result, equalTo(sampleResult));
        /*--------------------------------------------------------------------------*/
        a = new Member("a", new MyBigDecimal(5000));
        b = new Member("b", new MyBigDecimal(7789));
        c = new Member("c", new MyBigDecimal(0));
        memberList = Stream.of(a, b, c).collect(Collectors.toList());
        result = calculateService.process(memberList);
        //res
        sampleResult = new HashMap<>();
        //c
        map = new HashMap<>();
        map.put(b, new BigDecimal(3526, mathContext));
        //a
        map.put(a, new BigDecimal(737, mathContext));
        sampleResult.put(c, map);
        assertThat(result, equalTo(sampleResult));
        /*--------------------------------------------------------------------------*/
        a = new Member("a", new MyBigDecimal(1));
        b = new Member("b", new MyBigDecimal(0));
        c = new Member("c", new MyBigDecimal(0));
        memberList = Stream.of(a, b, c).collect(Collectors.toList());
        result = calculateService.process(memberList);
        //res
        sampleResult = new HashMap<>();
        //b
        map = new HashMap<>();
        map.put(a, new BigDecimal(1.0/3, mathContext));
        sampleResult.put(b, map);
        //c
        map = new HashMap<>();
        map.put(a, new BigDecimal(1.0/3, mathContext));
        sampleResult.put(c, map);
        assertThat(result, equalTo(sampleResult));
        /*--------------------------------------------------------------------------*/
        a = new Member("a", new MyBigDecimal(0));
        b = new Member("b", new MyBigDecimal(0));
        c = new Member("c", new MyBigDecimal(0));
        memberList = Stream.of(a, b, c).collect(Collectors.toList());
        result = calculateService.process(memberList);
        //res
        sampleResult = new HashMap<>();
        assertThat(result, equalTo(sampleResult));
        /*--------------------------------------------------------------------------*/
    }

    @Test
    public void testCalculate_WITH_ARG_EXCEPTION() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Member a = new Member("a", new MyBigDecimal(-3));
                Member b = new Member("b", new MyBigDecimal(0));
                Member c = new Member("c", new MyBigDecimal(0));
                List<Member> memberList = Stream.of(a, b, c).collect(Collectors.toList());
                Map<Member, Map<Member, BigDecimal>> result = calculateService.process(memberList);
            }
        });

    }

    @Test
    public void testParseResultHuman() {
        Member a = new Member("a", new MyBigDecimal(10));
        Member b = new Member("b", new MyBigDecimal(2));
        Member c = new Member("c", new MyBigDecimal(0));
        Map<Member, Map<Member, BigDecimal>> resultForParse = new HashMap<>();
        //b
        Map<Member, BigDecimal> map = new HashMap<>();
        map.put(a, new BigDecimal(2));
        resultForParse.put(b, map);
        //c
        map = new HashMap<>();
        map.put(a, new BigDecimal(4));
        resultForParse.put(c, map);

        String s1 = "'b' → 'a' 2";
        String s2 = "'c' → 'a' 4";
        //normal
        String s = calculateService.parseResultHuman(resultForParse);
        assertThat(s, containsString(s1));
        assertThat(s, containsString(s2));
        //html version
        s = calculateService.parseResultHuman(resultForParse, true);
        assertThat(s, containsString(s1));
        assertThat(s, containsString(s2));
        s = calculateService.parseResultHuman(null);
        String s4 = "Все и так ок %)";
        assertThat(s, containsString(s4));
        Map<Member, Map<Member, BigDecimal>> emptyResultMapForParse = new HashMap<>();
        s = calculateService.parseResultHuman(emptyResultMapForParse);
        assertThat(s, containsString(s4));

    }

}
