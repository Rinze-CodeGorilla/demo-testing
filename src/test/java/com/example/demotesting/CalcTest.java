package com.example.demotesting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
public class CalcTest {

    @MockBean
    CalculationService calculationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void it_should_calculate() throws Exception {
        when(calculationService.add(anyInt(), anyInt())).thenReturn(3);
        mockMvc.perform(
                        post("/calc")
                                .formField("a", "1")
                                .formField("b", "2")
                                .formField("op", "+"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", 3));
    }

    @Test
    void whenMissingOperator_thenShouldReturnBadRequest() throws Exception {
        var r = mockMvc.perform(post("/calc").formField("a", "1").formField("b", "1"))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue(r.getResolvedException() instanceof NullPointerException);
    }

    @Test
    void whenMissingIntA_thenShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/calc").formField("op", "+").formField("b", "1")).andExpect(status().isBadRequest());
    }

    @Test
    void whenMissingIntB_thenShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/calc").formField("op", "+").formField("a", "1")).andExpect(status().isBadRequest());
    }
}

@ControllerAdvice
class ExceptionHandlers {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    void handleIllegaleStateException(IllegalStateException e) throws Exception {
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    void handleNullPointerException(NullPointerException e) throws Exception {
    }
}
