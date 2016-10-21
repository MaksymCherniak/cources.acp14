package week4.home.study.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EntityAlreadyExistException extends Exception {

    public EntityAlreadyExistException(Object entity) {
        super(entity.toString() + "\nAlready exist");
    }
}
