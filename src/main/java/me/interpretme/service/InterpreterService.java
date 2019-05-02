package me.interpretme.service;

import me.interpretme.entity.InterpreterType;

public interface InterpreterService {

    InterpreterType getInterpreter(String interpreterType, String sessionId);
}
