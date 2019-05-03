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
        System.out.println("interpreterType : " + interpreterType);
        System.out.println("sessionId : "+sessionId );


        Optional<Class<?>> interpreterClass = getInterpreterClass(interpreterType);
        if (!interpreterClass.isPresent()) {
            System.out.println(interpreterClass.isPresent());
            throw new InterpreterNotFoundException();

        }
        else {
            Interpreter interpreter;

            if (sessionId == null) {
                try {
                    interpreter = (Interpreter) interpreterClass.get().newInstance();
                    interpreterRepository.persistInterpreter(interpreter);

                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new InterpreterNotInstantiatiedException();
                }
            } else {
                interpreter = interpreterRepository.findInterpreterBySessionId(sessionId);
            }

            return interpreter;


        }
    }



    private Optional<Class<?>> getInterpreterClass(String interpreterType)
    {
        Reflections reflections = new Reflections("me.interpretme");
        Set<Class<?>> interpreterTypes = reflections.getTypesAnnotatedWith(InterpreterType.class);
        System.out.println(interpreterTypes.size());

        Optional<Class<?>> interpreterClass = interpreterTypes.stream().filter(clazz-> {

            System.out.println(clazz.getAnnotation(InterpreterType.class).command());
                    return clazz.getAnnotation(InterpreterType.class).command() .equals(interpreterType) ;

                          //  && Interpreter.class.isAssignableFrom(clazz);
                }

        ).findFirst();

        return interpreterClass;
    }
}
