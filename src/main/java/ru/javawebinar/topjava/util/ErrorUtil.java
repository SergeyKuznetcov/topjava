package ru.javawebinar.topjava.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ErrorUtil {
    public static ResponseEntity<String> checkFieldErrorsAndDo(BindingResult result, Runnable runnable){
        if (result.hasErrors()) {
            return ResponseEntity.unprocessableEntity().body(getFieldErrorsMsg(result));
        }
        runnable.run();
        return ResponseEntity.ok().build();
    }

    private static String getFieldErrorsMsg(BindingResult result){
        return result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("<br>"));
    }
}
