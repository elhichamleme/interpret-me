package me.interpretme.entity;

import me.interpretme.exception.InstructionNotExecutedException;
import me.interpretme.exception.InstructionThrowedErrorException;
import me.interpretme.exception.InterpreterNotInstantiatiedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

@InterpreterType(command="python")
public class PythonInterpreter extends Interpreter {

    private BufferedReader stdout;
    private BufferedWriter stdin;
    private BufferedReader stderr;

    public PythonInterpreter() throws InterpreterNotInstantiatiedException {

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
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
    public String execute(String instruction) throws InstructionNotExecutedException,
            InstructionThrowedErrorException {

        try{
        stdin.write(instruction + System.lineSeparator());
        stdin.write("print '**MARKER**'"+ System.lineSeparator());
        stdin.flush();
        // Not Implemented yet method
        // processError();
        return  processOutput();

        }catch(IOException ex){

            throw new InstructionNotExecutedException();
        }


    }

    private void processError()
    {
        throw new NotImplementedException();

    }
    private String processOutput() throws InstructionNotExecutedException{
        try{
            String line;
            StringBuilder result = new StringBuilder();

            while (!(line = stdout.readLine() ).equals("**MARKER**"))
            {
                if(!result.toString().equals(""))
                    result = result.append(System.lineSeparator());
                result = result.append(line);
            }
            return result.toString();

        }catch(IOException ex){

            throw new InstructionNotExecutedException();
        }
    }
}
