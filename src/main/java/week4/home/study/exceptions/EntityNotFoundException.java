package week4.home.study.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entity) {
        super(entity + " not found");
    }
}
