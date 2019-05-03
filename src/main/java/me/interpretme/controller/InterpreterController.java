package me.interpretme.controller;

import me.interpretme.entity.Interpreter;
import me.interpretme.exception.*;
import me.interpretme.service.InterpreterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("interpreter")
public class InterpreterController {

    @Autowired
    InterpreterService interpreterService;

    @PostMapping("/execute")
    public Map<String, String> execute(@RequestBody Map<String, String> entries)
            throws InterpreterNotInstantiatiedException, InterpreterNotFoundException,
            InstructionNotExecutedException, CodeCannotBeParsedException,
            InstructionThrowedErrorException
    {
        String code = entries.get("code");
        String sessionId = entries.get("sessionId");

        if(!validateCode(code))
            throw new CodeCannotBeParsedException();

        String[] tokens = tokenize(code);
        String interpreterType = tokens[0];
        String instruction = tokens[1];

        Interpreter interpreter= interpreterService.getInterpreter(interpreterType, sessionId);

        Map<String, String> results = new HashMap<>();
        results.put("result", interpreter.execute(instruction));
        results.put("sessionId", interpreter.getProcess().hashCode()+"");
        return results;
    }

    String[] tokenize(String code){
        code = code.trim();
        String interpreter = code.substring(1, code.indexOf(" "));
        String instruction =  code.substring(code.indexOf(" ")+1, code.length());



        String[] tokens = {interpreter, instruction};
        return  tokens;
    }

    boolean validateCode(String code){
        code = code.trim();
        return code.length() > 3 & code.charAt(0) == '%' & code.contains(" ");
    }


}
