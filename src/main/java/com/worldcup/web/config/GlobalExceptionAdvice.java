package com.worldcup.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404() {
        return "error/404";
    }

    @ExceptionHandler(Throwable.class)
    public String globalException(HttpServletRequest request, Throwable e) {
        log.info(request.getRequestURI());
        log.error(e.getMessage(),e);
        return "error/error";
    }
}
