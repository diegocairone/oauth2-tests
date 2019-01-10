package com.cairone.example.webapp.ctrls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
    	
        logger.error("Ocurrió un error durante la ejecución de la aplicación", throwable);
        
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
