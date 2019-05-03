package me.interpretme.service;

import me.interpretme.entity.Interpreter;
import me.interpretme.exception.InterpreterNotFoundException;
import me.interpretme.exception.InterpreterNotInstantiatiedException;

public interface InterpreterService {

    Interpreter getInterpreter(String interpreterType, String sessionId)
            throws InterpreterNotFoundException, InterpreterNotInstantiatiedException;
}
