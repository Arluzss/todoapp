package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ControllerErro implements ErrorController{
    
   private final ErrorAttributes errorAttributes;

    public ControllerErro(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<Object> handleError(WebRequest request) {
        Map<String, Object> errors = errorAttributes.getErrorAttributes(
            request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)
        );

        int status = (int) errors.getOrDefault("status", 500);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        body.put("error", errors.get("error"));
        body.put("message", errors.get("message"));
        body.put("path", errors.get("path"));

        return new ResponseEntity<>(body, HttpStatus.valueOf(status));
    }
}
