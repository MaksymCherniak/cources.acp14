package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.dao.interfaces.ITeacherDao;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@RestController
public class TeacherController {
    @Autowired
    private ITeacherDao iTeacherDao;
    @Autowired
    private ISubjectDao iSubjectDao;

    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    @ResponseBody
    public String addTeacher(@RequestBody Teacher teacher) throws EntityAlreadyExistException, ComingNullObjectException
            , OperationFailedException {

        iTeacherDao.addTeacher(teacher);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + teacher.toString() + LOG_OPERATION_ADD;
    }

    @RequestMapping(value = "/getAllTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getAllTeachers(@RequestParam(name = "from") int from,
                                        @RequestParam(name = "quantity") int quantity) throws EntityNotFoundException {

        return iTeacherDao.getAllTeachers(from, quantity);
    }

    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    @ResponseBody
    public String updateTeacher(@RequestBody Teacher teacher) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        iTeacherDao.updateTeacher(teacher);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + teacher.toString() + LOG_OPERATION_UPDATE;
    }

    @RequestMapping(value = "/removeTeacher", method = RequestMethod.POST)
    @ResponseBody
    public String removeTeacher(@RequestParam long id) throws EntityNotFoundException {

        Teacher teacher = iTeacherDao.getTeacherById(id);

        iTeacherDao.removeTeacher(id);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + teacher.toString() + LOG_OPERATION_REMOVE;
    }

    @RequestMapping(value = "/getTeacher", method = RequestMethod.GET)
    @ResponseBody
    public Teacher getTeacher(@RequestParam(name = "name") String name,
                              @RequestParam(name = "experience") int experience,
                              @RequestParam(name = "subject") String subjectName) throws EntityNotFoundException {

        return iTeacherDao.getTeacher(new Teacher(name, experience, iSubjectDao.getSubjectByName(subjectName)));
    }

    @RequestMapping(value = "/getTeacherByName", method = RequestMethod.GET)
    @ResponseBody
    public Teacher getTeacherByName(@RequestParam(name = "name") String name) throws EntityNotFoundException {

        return iTeacherDao.getTeacherByName(name);
    }

    @RequestMapping(value = "/getTeacherById", method = RequestMethod.GET)
    @ResponseBody
    public Teacher getTeacherById(@RequestParam(name = "id") long id) throws EntityNotFoundException {

        return iTeacherDao.getTeacherById(id);
    }

    @RequestMapping(value = "/getTeacherBySubject", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeacherBySubject(@RequestParam(name = "subject") String subject) {

        return iTeacherDao.getTeachersBySubject(subject);
    }

    @RequestMapping(value = "/getMaxExperiencedTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getMaxExperiencedTeachers(@RequestParam(name = "from") int from,
                                                   @RequestParam(name = "quantity") int quantity) {

        return iTeacherDao.getMaxExperiencedTeachers(from, quantity);
    }

    @RequestMapping(value = "/getMinExperiencedTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getMinExperiencedTeachers(@RequestParam(name = "from") int from,
                                                   @RequestParam(name = "quantity") int quantity) {

        return iTeacherDao.getMinExperiencedTeachers(from, quantity);
    }

    @RequestMapping(value = "/getTeachersMoreThanExperience", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachersMoreThanExperience(@RequestParam(name = "experience") int experience,
                                                       @RequestParam(name = "from") int from,
                                                       @RequestParam(name = "quantity") int quantity) {

        return iTeacherDao.getTeacherByExperience(experience, from, quantity);
    }
}
