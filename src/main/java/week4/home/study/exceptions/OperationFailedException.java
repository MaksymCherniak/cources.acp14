package week4.home.study.exceptions;

public class OperationFailedException extends Exception {

    public OperationFailedException(String entity, String operation) {
        super(operation + " into " + entity + " failed coz of transaction error");
    }
}
