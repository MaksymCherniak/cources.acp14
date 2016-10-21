package week4.home.study.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class OperationFailedException extends Exception {

    public OperationFailedException(String entity, String operation) {
        super(operation + " into " + entity + " failed coz of transaction error");
    }
}
