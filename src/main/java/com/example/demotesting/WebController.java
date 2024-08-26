package com.example.demotesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.function.IntBinaryOperator;

@Controller
public class WebController {
    CalculationService calculationService;

    WebController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }
    @PostMapping("calc")
    String calc(int a, int b, String op, Model model) {
        IntBinaryOperator method = switch (op) {
            case "+" -> calculationService::add;
            case "-" -> calculationService::subtract;
            default -> throw new IllegalArgumentException();
        };
        model.addAttribute("result", method.applyAsInt(a, b));
        return "result";
    }
}
