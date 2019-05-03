package me.interpretme.repository;

import me.interpretme.entity.Interpreter;
import me.interpretme.exception.InterpreterNotFoundException;

public interface InterpreterRepository {

    Interpreter findByInterpreterTypeAndSessionId(String interpreterType, String sessionId)
            throws InterpreterNotFoundException;
}
