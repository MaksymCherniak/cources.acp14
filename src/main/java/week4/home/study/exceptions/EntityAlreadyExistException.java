package week4.home.study.exceptions;

public class EntityAlreadyExistException extends Exception {

    public EntityAlreadyExistException(Object entity) {
        super(entity.toString() + "\nAlready exist");
    }
}
