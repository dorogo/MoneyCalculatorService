package com.dorogo.MoneyCalculatorService.controllers.ui;

import com.dorogo.MoneyCalculatorService.controllers.form.ListForm;
import com.dorogo.MoneyCalculatorService.controllers.form.MemberForm;
import com.dorogo.MoneyCalculatorService.model.Member;
import com.dorogo.MoneyCalculatorService.service.CalculateService;
import com.dorogo.MoneyCalculatorService.util.MyBigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@Controller
@Slf4j
public class AppController {

    @Autowired
    CalculateService calculateService;

    @GetMapping("/calculator")
    public String showCalculator(Model model) {
        ArrayList<MemberForm> validList = new ArrayList<>();
        validList.add(new MemberForm());
        validList.add(new MemberForm());
        ListForm listForm = new ListForm();
        listForm.setList(validList);
        model.addAttribute("listForm", listForm);
        return "calculator";
    }

    @PostMapping(value = "/calculator", params = "row")
    public ModelAndView addRow(ListForm listForm, BindingResult bindingResult, @RequestParam(value = "row", required = true) String manageRow) {
        ModelAndView model = new ModelAndView("calculator");
        ArrayList<MemberForm> list = listForm.getList();
        if (manageRow.equals("add"))
            list.add(new MemberForm());
        else if (manageRow.equals("remove"))
        {
            int i = list.size() - 1;
            if (i > 1)
                list.remove(i);
        }
        model.addObject("listForm", listForm);
        return model;
    }

    @PostMapping("/calculator")
    public ModelAndView process(@Valid ListForm listForm, BindingResult bindingResult) {
        log.info("listForm = " + listForm);
        ModelAndView model = null;
        if (bindingResult.hasErrors())
        {
            model = new ModelAndView("calculator");
            model.addObject("listForm", listForm);
            return model;
        }

        ArrayList<Member> list = new ArrayList<>();
        for (MemberForm mf : listForm.getList()) {
            MyBigDecimal spent = new MyBigDecimal(mf.getSpent());
            Member m = new Member()
                    .setName(mf.getName())
                    .setSpent(spent);
            list.add(m);
        }

        model = new ModelAndView("result");
        Map<Member, Map<Member, BigDecimal>> resultMap = calculateService.process(list);
        String resultStr = calculateService.parseResultHuman(resultMap, true);
        model.addObject("listForm", listForm);
        model.addObject("resultMessage", resultStr);
        return model;
    }

}
