package me.interpretme.entity;

import me.interpretme.exception.InstructionNotExecutedException;
import me.interpretme.exception.InstructionThrowedErrorException;

public abstract class Interpreter {
     protected Process process;
     public abstract String execute(String instruction) throws InstructionNotExecutedException,
             InstructionThrowedErrorException;

    public Process getProcess() {
        return process;
    }
}
