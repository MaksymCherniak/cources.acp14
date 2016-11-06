package week4.home.study.main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppStaticValues {
    public static final int DEFAULT_FROM_VALUE = 0;
    public static final int DEFAULT_QUANTITY_VALUE = 50;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ALL = "all";
    public static final String GROUP = "group";
    public static final String TEACHER = "teacher";
    public static final String SUBJECT = "subject";
    public static final String DESCRIPTION = "description";
    public static final String EXPERIENCE = "experience";
    public static final String MAX = "max";
    public static final String MIN = "min";

    public static final String OLD_NAME = "oldName";
    public static final String NEW_NAME = "newName";
    public static final String GROUP_NAME = "groupName";
    public static final String STUDENT_NAME = "studentName";
    public static final String TEACHER_NAME = "teacherName";
    public static final String SUBJECT_NAME = "subjectName";
    public static final String FROM = "from";
    public static final String QUANTITY = "quantity";

    public static final String OPERATION_ADD = "add";
    public static final String OPERATION_REMOVE = "remove";
    public static final String OPERATION_UPDATE = "update";
    public static final String OPERATION_GET = "get";

    public static final String ERROR_STUDENT_NOT_FOUND = "Student not found";
    public static final String ERROR_GROUP_NOT_FOUND = "Groups not found";
    public static final String ERROR_SUBJECT_NOT_FOUND = "Subject not found";
    public static final String ERROR_TEACHER_NOT_FOUND = "Teacher not found";

    public static final String LOG_OPERATION_UPDATE = "\nUpdated in database";
    public static final String LOG_OPERATION_REMOVE = "\nRemoved from database";
    public static final String LOG_OPERATION_ADD = "\nAdded into database";
    public static final String LOG_ALREADY_EXIST = "\nAlready exist";

    public static String addOperationInfo(String name) {
        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + name + LOG_OPERATION_ADD;
    }

    public static String updateOperationInfo(String name) {
        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + name + LOG_OPERATION_UPDATE;
    }

    public static String removeOperationInfo(String name) {
        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + name + LOG_OPERATION_REMOVE;
    }
}
