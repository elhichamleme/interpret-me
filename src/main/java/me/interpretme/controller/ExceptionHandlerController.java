package me.interpretme.controller;

import me.interpretme.Main;
import me.interpretme.exception.CodeCannotBeParsedException;
import me.interpretme.exception.InstructionThrowedErrorException;
import me.interpretme.exception.InterpreterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler( InterpreterNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public Map<String, String> interpreterNotFound(Exception ex, WebRequest req) {

        Map<String, String> result =new HashMap<>();
        result.put("error", "interpreter not found either it is miswritten or not supported yet");
        return result;

    }

    @ExceptionHandler( CodeCannotBeParsedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public Map<String, String> codeCannotBeParsed(Exception ex, WebRequest req) {

        Map<String, String> result =new HashMap<>();
        result.put("error", "code cannot be parsed");
        return result;

    }

    @ExceptionHandler( InstructionThrowedErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public Map<String, String> intructionThrowedException(Exception ex, WebRequest req) {

        InstructionThrowedErrorException exception = (InstructionThrowedErrorException) ex;

        Map<String, String> result =new HashMap<>();
        result.put("error", String.format("Instruction throwed the error :%s",exception.getError()));
        return result;

    }

}