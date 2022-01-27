package com.audiostock.controller;

import com.audiostock.service.exceptions.TrackIsNotActiveException;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WymExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound() {
        return new ModelAndView("userNotFound");
    }

    @ExceptionHandler({TrackNotFoundException.class, TrackIsNotActiveException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView trackNotFound() {
        return new ModelAndView("trackNotFound");
    }

}
