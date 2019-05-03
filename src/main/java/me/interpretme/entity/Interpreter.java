package me.interpretme.entity;

import me.interpretme.exception.InstructionNotExecutedException;

public abstract class Interpreter {
     protected Process process;
     public abstract String execute(String instruction) throws InstructionNotExecutedException;

    public Process getProcess() {
        return process;
    }
}
