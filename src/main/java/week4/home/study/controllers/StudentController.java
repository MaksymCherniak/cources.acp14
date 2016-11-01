package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import week4.home.study.entity.Student;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.service.interfaces.IStudentService;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@RestController
public class StudentController {
    @Autowired
    private IStudentService iStudentService;

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    @ResponseBody
    public String addStudent(@RequestParam(name = STUDENT_NAME) String studentName,
                             @RequestParam(name = GROUP_NAME) String groupName) throws EntityAlreadyExistException
            , ComingNullObjectException, EntityNotFoundException {

        iStudentService.addStudent(studentName, groupName);

        return addOperationInfo(studentName);
    }

    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getAllStudents(@RequestParam(name = FROM) int from,
                                        @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iStudentService.getAllStudents(from, quantity);
    }

    @RequestMapping
    @ResponseBody
    public String updateStudent(@RequestParam(name = OLD_NAME) String oldName,
                                @RequestParam(name = NEW_NAME) String newName,
                                @RequestParam(name = GROUP_NAME) String groupName) throws ComingNullObjectException
            , EntityAlreadyExistException, EntityNotFoundException {

        iStudentService.updateStudent(oldName, newName, groupName);

        return updateOperationInfo(oldName);
    }

    @RequestMapping(value = "/removeStudent", method = RequestMethod.POST)
    @ResponseBody
    public String removeStudent(@RequestParam(name = STUDENT_NAME) String studentName) throws EntityNotFoundException {

        iStudentService.removeStudent(studentName);

        return removeOperationInfo(studentName);
    }

    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    @ResponseBody
    public Student getStudent(@RequestParam(name = STUDENT_NAME) String studentName,
                              @RequestParam(name = GROUP_NAME) String groupName) throws EntityNotFoundException {

        return iStudentService.getStudent(studentName, groupName);
    }

    @RequestMapping(value = "/getStudentsByNameLike", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getStudentsByNameLike(@RequestParam(name = STUDENT_NAME) String studentName,
                                               @RequestParam(name = FROM) int from,
                                               @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iStudentService.getAllStudentsLike(studentName, from, quantity);
    }

    @RequestMapping(value = "/getStudentsByGroup", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getStudentsByGroup(@RequestParam(name = GROUP_NAME) String groupName,
                                            @RequestParam(name = FROM) int from,
                                            @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iStudentService.getAllStudentsByGroupName(groupName, from, quantity);
    }

    @RequestMapping(value = "/getStudentByName", method = RequestMethod.GET)
    @ResponseBody
    public Student getStudentByName(@RequestParam(name = STUDENT_NAME) String studentNmae) throws EntityNotFoundException {

        return iStudentService.getStudentByName(studentNmae);
    }

    @RequestMapping(value = "/setGroup", method = RequestMethod.POST)
    @ResponseBody
    public String setGroup(@RequestParam(name = STUDENT_NAME) String studentName,
                           @RequestParam(name = GROUP_NAME) String groupName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        iStudentService.setGroup(studentName, groupName);

        return updateOperationInfo(studentName);
    }
}
