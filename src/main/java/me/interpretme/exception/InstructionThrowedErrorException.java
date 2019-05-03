package me.interpretme.exception;

public class InstructionThrowedErrorException extends Exception {
    String error  = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
