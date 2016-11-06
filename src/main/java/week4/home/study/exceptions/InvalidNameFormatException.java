package week4.home.study.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidNameFormatException extends Exception {

    public InvalidNameFormatException(String entity, int size) {
        super("Invalid " + entity + " name format. Name size must be more than " + size + " symbols");
    }
}
