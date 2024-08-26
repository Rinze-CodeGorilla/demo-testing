package com.example.demotesting;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CalculationServiceTests {
    @BeforeAll
    static void before() {
        calculationService = new CalculationService();
    }
    static CalculationService calculationService;
    @Test
    void shouldAdd() {
        int result = calculationService.add(2, 2);
        assertEquals(4, result);
    }
    @Test
    void shouldSubtract() {
        int result = calculationService.subtract(2, 3);
        assertEquals(-1, result);
    }
    @Test
    void shouldAddManyInts() {
        for (int a = Short.MIN_VALUE; a < Short.MAX_VALUE; a++) {
            for (int b = Short.MIN_VALUE; b < Short.MAX_VALUE; b++) {
                assertEquals(calculationService.add(a, b), a+b);
                assertEquals(calculationService.subtract(a, b), a-b);
            }
        }
    }
}
