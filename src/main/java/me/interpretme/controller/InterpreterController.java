package me.interpretme.controller;

import me.interpretme.entity.Interpreter;
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
    {
        String code = entries.get("code");
        String sessionId = entries.get("sessionId");
        Map<String, String> results = new HashMap<>();
        results.put("result", code);
        results.put("sessionId", sessionId);
        return results;
    }


}
