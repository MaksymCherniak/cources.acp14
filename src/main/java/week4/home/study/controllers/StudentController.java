package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import week4.home.study.dao.interfaces.IStudentDao;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private IStudentDao iStudentDao;

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    @ResponseBody
    public String addStudent(@RequestBody Student student) throws OperationFailedException, EntityAlreadyExistException
            , ComingNullObjectException {

        iStudentDao.addStudent(student);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED));
    }

    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getAllStudents(@RequestParam(name = "from") int from, @RequestParam(name = "quantity") int quantity)
            throws EntityNotFoundException {

        return iStudentDao.getAllStudents(from, quantity);
    }

    @RequestMapping
    @ResponseBody
    public String updateStudent(@RequestBody Student student) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        iStudentDao.updateStudent(student);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED));
    }
}
