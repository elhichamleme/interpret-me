package me.interpretme.service;

import me.interpretme.entity.Interpreter;
import me.interpretme.entity.InterpreterType;
import me.interpretme.exception.InterpreterNotFoundException;
import me.interpretme.exception.InterpreterNotInstantiatiedException;
import me.interpretme.repository.InterpreterRepository;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class InterpreterServiceImpl implements InterpreterService  {
    @Autowired
    InterpreterRepository interpreterRepository;

    @Override
    public Interpreter getInterpreter(String interpreterType, String sessionId)
            throws InterpreterNotFoundException, InterpreterNotInstantiatiedException {

        Optional<Class<?>> interpreterClass = getInterpreterClass(interpreterType);
        if (!interpreterClass.isPresent())
            throw new InterpreterNotFoundException();
        else {

            if (sessionId == null) {
                try {
                    return (Interpreter) interpreterClass.get().newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new InterpreterNotInstantiatiedException();
                }
            } else {
                return interpreterRepository.findByInterpreterTypeAndSessionId(interpreterType, sessionId);
            }


        }
    }



    public Optional<Class<?>> getInterpreterClass(String interpreterType)
    {
        Reflections reflections = new Reflections();
        Set<Class<?>> interpreterTypes = reflections.getTypesAnnotatedWith(InterpreterType.class);

        Optional<Class<?>> interpreterClass = interpreterTypes.stream().filter(clazz-> {

                    return clazz.getAnnotation(InterpreterType.class).command() .equals(interpreterType) &&
                            Interpreter.class.isAssignableFrom(clazz);
                }

        ).findFirst();

        return interpreterClass;
    }
}
