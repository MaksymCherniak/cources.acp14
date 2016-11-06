package week4.home.study.main;

import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.entity.Subject;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.*;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

public class Validator {

    public static boolean groupName(String name) throws ComingNullObjectException, InvalidNameFormatException {
        if (name == null) {
            throw new ComingNullObjectException(Groups.class.getSimpleName(), OPERATION_ADD);
        }

        if (name.length() < 3) {
            throw new InvalidNameFormatException(Groups.class.getSimpleName(), 3);
        }

        return true;
    }

    public static boolean studentName(String name) throws InvalidNameFormatException, ComingNullObjectException {
        if (name == null) {
            throw new ComingNullObjectException(Student.class.getSimpleName(), OPERATION_ADD);
        }

        if (name.length() < 5) {
            throw new InvalidNameFormatException(Student.class.getSimpleName(), 5);
        }

        return true;
    }

    public static boolean teacherName(String name) throws InvalidNameFormatException, ComingNullObjectException {
        if (name == null) {
            throw new ComingNullObjectException(Teacher.class.getSimpleName(), OPERATION_ADD);
        }

        if (name.length() < 5) {
            throw new InvalidNameFormatException(Teacher.class.getSimpleName(), 5);
        }

        return true;
    }

    public static boolean subjectName(String name) throws ComingNullObjectException, InvalidNameFormatException {
        if (name == null) {
            throw new ComingNullObjectException(Subject.class.getSimpleName(), OPERATION_ADD);
        }

        if (name.length() < 4) {
            throw new InvalidNameFormatException(Subject.class.getSimpleName(), 4);
        }

        return true;
    }

    public static void checkNullObject(String entity, Object object) throws EntityNotFoundException {
        if (object == null) {
            throw new EntityNotFoundException(entity);
        }
    }

    public static void checkAlreadyExist(Object object) throws EntityAlreadyExistException {
        if (object != null) {
            throw new EntityAlreadyExistException(object);
        }
    }

    public static void checkListIsEmpty(String entity, List list) throws EntityNotFoundException {
        if (list.size() == 0) {
            throw new EntityNotFoundException(entity);
        }
    }
}
