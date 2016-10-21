package week4.home.study.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entity) {
        super(entity + " not found");
    }
}
