package me.interpretme.repository;

import me.interpretme.entity.Interpreter;
import me.interpretme.exception.InterpreterNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class InterpreterRepositoryImpl implements InterpreterRepository {

    private Map<String, Map<String, Interpreter>> interpreters;

    @Override
    public Interpreter findByInterpreterTypeAndSessionId(String interpreterType, String sessionId)
    throws InterpreterNotFoundException{
        Map<String, Interpreter> interpretersByType = interpreters.get(interpreterType);
        if(interpretersByType == null)
            throw new InterpreterNotFoundException();
        else
        {
            Interpreter interpreter = interpretersByType.get(sessionId);
            if(interpreter == null)
                throw new InterpreterNotFoundException();
            else return interpreter;
        }


    }
}
