package me.interpretme.entity;

import me.interpretme.exception.InstructionNotExecutedException;
import me.interpretme.exception.InterpreterNotInstantiatiedException;

import java.io.*;

@InterpreterType(command="python")
public class PythonInterpreter extends Interpreter {

    private BufferedReader stdout;
    private BufferedWriter stdin;
    private BufferedReader stderr;

    public PythonInterpreter() throws InterpreterNotInstantiatiedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            this.process =processBuilder.command("python", "-u", "-i").start();
            this.stdout = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
            this.stdin = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
            this.stderr = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));
        }
        catch (IOException ex){
            throw new InterpreterNotInstantiatiedException();
        }


    }

    @Override
    public String execute(String instruction) throws InstructionNotExecutedException {
        try{

        stdin.write(instruction + System.lineSeparator());
        stdin.write("print '**MARKER**'"+ System.lineSeparator());
        //   stdin.write(instruction);
        stdin.flush();

        String line;
        StringBuilder result = new StringBuilder();

            while (!(line = stdout.readLine() ).equals("**MARKER**"))
            {
                result = result.append(line).append(System.lineSeparator());
            }
            return result.toString();

        }catch(IOException ex){

            throw new InstructionNotExecutedException();
        }



    }
}
