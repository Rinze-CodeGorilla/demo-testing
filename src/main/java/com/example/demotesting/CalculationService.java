package com.example.demotesting;

import org.springframework.stereotype.Service;

@Service
public class CalculationService {
    public int add(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }
}
