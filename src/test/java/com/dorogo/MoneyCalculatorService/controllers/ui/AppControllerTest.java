package com.dorogo.MoneyCalculatorService.controllers.ui;

import com.dorogo.MoneyCalculatorService.controllers.form.ListForm;
import com.dorogo.MoneyCalculatorService.controllers.form.MemberForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowCalculator() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/calculator"))
        .andExpect(status().isOk())
        .andExpect(view().name("calculator"))
        .andExpect(model().attributeExists("listForm"))
        .andExpect(model().attribute("listForm", hasProperty("list", hasSize(2))));
    }

    @Test
    public void testAddRow() throws Exception {
        ArrayList<MemberForm> list = new ArrayList<>();
        list.add(new MemberForm());
        list.add(new MemberForm());
        ListForm listForm = new ListForm();
        listForm.setList(list);
        //это вариант без сторонней библиотеки
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/calculator")
//                .param("row","add")
//                .param("list[0].spent","0")
//                .param("list[1].spent","0"))
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/calculator", listForm).param("row","add"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attributeExists("listForm"))
                .andExpect(model().attribute("listForm", hasProperty("list", hasSize(3))));
    }
    @Test
    public void testRemoveRow() throws Exception {
        ArrayList<MemberForm> list = new ArrayList<>();
        list.add(new MemberForm());
        list.add(new MemberForm());
        list.add(new MemberForm());
        ListForm listForm = new ListForm();
        listForm.setList(list);
        //это вариант без сторонней библиотеки
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/calculator")
//                .param("row","remove")
//                .param("list[0].spent","0")
//                .param("list[1].spent","0")
//                .param("list[2].spent","0"))
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/calculator", listForm).param("row","remove"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attributeExists("listForm"))
                .andExpect(model().attribute("listForm", hasProperty("list", hasSize(2))));
    }

    @Test
    public void testProcess() throws Exception {
        ArrayList<MemberForm> list = new ArrayList<>();
        list.add(new MemberForm("m0", 0));
        list.add(new MemberForm("m1", 0));
        ListForm listForm = new ListForm();
        listForm.setList(list);

        String resultMsg = "Все и так ок %)";
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/calculator", listForm))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("listForm"))
                .andExpect(model().attribute("listForm", hasProperty("list", hasSize(2))))
                .andExpect(model().attributeExists("resultMessage"))
                .andExpect(model().attribute("resultMessage", equalTo(resultMsg)));

        list = new ArrayList<>();
        list.add(new MemberForm("m0", 10));
        list.add(new MemberForm("m1", 2));
        list.add(new MemberForm("m2", 0));
        listForm = new ListForm();
        listForm.setList(list);

        String resultMsg1 = "'m1' → 'm0' 2.00";
        String resultMsg2 = "'m2' → 'm0' 4.00";
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/calculator", listForm))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("listForm"))
                .andExpect(model().attribute("listForm", hasProperty("list", hasSize(3))))
                .andExpect(model().attributeExists("resultMessage"))
                .andExpect(model().attribute("resultMessage", containsString(resultMsg1)))
                .andExpect(model().attribute("resultMessage", containsString(resultMsg2)));

    }

    @Test
    public void testRootRedirect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.redirectedUrl("/calculator"))
                .andExpect(status().isFound());
    }

}