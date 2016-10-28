package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.entity.Groups;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@RestController
public class StudentController {
    @Autowired
    private IStudentDao iStudentDao;
    @Autowired
    private IGroupDao iGroupDao;

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    @ResponseBody
    public String addStudent(@RequestBody Student student) throws OperationFailedException, EntityAlreadyExistException
            , ComingNullObjectException {

        iStudentDao.addStudent(student);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + student.toString() + LOG_OPERATION_ADD;
    }

    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getAllStudents(@RequestParam(name = "from") int from,
                                        @RequestParam(name = "quantity") int quantity) throws EntityNotFoundException {

        return iStudentDao.getAllStudents(from, quantity);
    }

    @RequestMapping
    @ResponseBody
    public String updateStudent(@RequestBody Student student) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        iStudentDao.updateStudent(student);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + student.toString() + LOG_OPERATION_UPDATE;
    }

    @RequestMapping(value = "/removeStudent", method = RequestMethod.POST)
    @ResponseBody
    public String removeStudent(@RequestParam long id) throws EntityNotFoundException {

        Student student = iStudentDao.getStudentById(id);

        iStudentDao.removeStudent(id);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + student.toString() + LOG_OPERATION_REMOVE;
    }

    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    @ResponseBody
    public Student getStudent(@RequestParam(name = "student") String student,
                              @RequestParam(name = "group") String group) throws EntityNotFoundException {

        Groups groups = iGroupDao.getGroup(new Groups(group));

        return iStudentDao.getStudent(new Student(student, groups));
    }

    @RequestMapping(value = "/getStudentById", method = RequestMethod.GET)
    @ResponseBody
    public Student getStudentById(@RequestParam(name = "id") long id) throws EntityNotFoundException {

        return iStudentDao.getStudentById(id);
    }

    @RequestMapping(value = "/getStudentsByGroup", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getStudentsByGroup(@RequestParam(name = "group") String group,
                                            @RequestParam(name = "quantity") int quantity) throws EntityNotFoundException {

        return iStudentDao.getStudentsByGroup(iGroupDao.getGroup(new Groups(group)), quantity);
    }
}
