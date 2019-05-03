package me.interpretme.repository;

import me.interpretme.entity.Interpreter;
import me.interpretme.exception.InterpreterNotFoundException;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InterpreterRepositoryImpl implements InterpreterRepository {

    private Map<String, Interpreter> interpreters = new HashMap<>();

    @Override
    public Interpreter findInterpreterBySessionId(@NotNull String sessionId)
    throws InterpreterNotFoundException{

            Interpreter interpreter = interpreters.get(sessionId);
            if(interpreter == null)
                throw new InterpreterNotFoundException();
            else return interpreter;



    }

    @Override
    public void persistInterpreter(@NotNull  Interpreter interpreter) {
        interpreters.put(interpreter.getProcess().hashCode()+"", interpreter);
    }
}
