package me.interpretme.repository;

import me.interpretme.entity.Interpreter;
import me.interpretme.exception.InterpreterNotFoundException;

public interface InterpreterRepository {

    Interpreter findInterpreterBySessionId(String sessionId)
            throws InterpreterNotFoundException;
    void persistInterpreter(Interpreter interpreter);
}
