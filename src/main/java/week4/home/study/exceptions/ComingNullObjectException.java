package week4.home.study.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ComingNullObjectException extends Exception {

    public ComingNullObjectException(String entity, String operation) {
        super("Coming null object of " + entity + " in the " + operation + " operation");
    }
}
