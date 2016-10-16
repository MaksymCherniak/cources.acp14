package week4.home.study.exceptions;

public class ComingNullObjectException extends Exception {

    public ComingNullObjectException(String entity, String operation) {
        super("Coming null object of " + entity + " in the " + operation + " operation");
    }
}
