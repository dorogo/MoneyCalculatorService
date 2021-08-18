package com.dorogo.MoneyCalculatorService.controllers;

import com.dorogo.MoneyCalculatorService.model.Member;
import com.dorogo.MoneyCalculatorService.model.ValidList;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    @GetMapping("/")
    public String index() {
        return "hello";
    }

    @PostMapping("/process")
    public String process(@Valid @RequestBody ValidList<Member> list) {
        System.out.println("MainController.process(). member = " + list);
        
        return "ok, calculated! here is...";
    }

    @PostMapping("/test")
    public String test(@Valid @RequestBody Member m) {
        System.out.println("MainController.process(). member = " + m);

        return "test valid.";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
