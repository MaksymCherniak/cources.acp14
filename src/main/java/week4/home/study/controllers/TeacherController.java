package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import week4.home.study.entity.Teacher;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.service.interfaces.ITeacherService;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

@RestController
public class TeacherController {
    @Autowired
    private ITeacherService iTeacherService;

    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    @ResponseBody
    public String addTeacher(@RequestParam(name = TEACHER_NAME) String teacherName,
                             @RequestParam(name = EXPERIENCE) int experience) throws EntityAlreadyExistException, ComingNullObjectException {

        iTeacherService.addTeacher(teacherName, experience);

        return addOperationInfo(teacherName);
    }

    @RequestMapping(value = "/getAllTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getAllTeachers(@RequestParam(name = FROM) int from,
                                        @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iTeacherService.getAllTeachers(from, quantity);
    }

    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    @ResponseBody
    public String updateTeacher(@RequestParam(name = OLD_NAME) String oldName,
                                @RequestParam(name = NEW_NAME) String newName,
                                @RequestParam(name = EXPERIENCE) int experience,
                                @RequestParam(name = SUBJECT_NAME) String subjectName) throws ComingNullObjectException, EntityAlreadyExistException, EntityNotFoundException {

        iTeacherService.updateTeacher(oldName, newName, experience, subjectName);

        return updateOperationInfo(oldName);
    }

    @RequestMapping(value = "/removeTeacher", method = RequestMethod.POST)
    @ResponseBody
    public String removeTeacher(@RequestParam(name = TEACHER_NAME) String teacherName) throws EntityNotFoundException {

        iTeacherService.removeTeacher(teacherName);

        return removeOperationInfo(teacherName);
    }

    @RequestMapping(value = "/setSubject", method = RequestMethod.POST)
    @ResponseBody
    public String setSubject(@RequestParam(name = TEACHER_NAME) String teacherName,
                             @RequestParam(name = SUBJECT_NAME) String subjectName) throws EntityNotFoundException
            , EntityAlreadyExistException, ComingNullObjectException {

        iTeacherService.setSubject(teacherName, subjectName);

        return updateOperationInfo(teacherName);
    }

    @RequestMapping(value = "/getTeacherByNameLike", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeacherByNameLike(@RequestParam(name = TEACHER_NAME) String teacherName,
                                              @RequestParam(name = FROM) int from,
                                              @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iTeacherService.getTeacherByNameLike(teacherName, from, quantity);
    }

    @RequestMapping(value = "/getTeacherByName", method = RequestMethod.GET)
    @ResponseBody
    public Teacher getTeacherByName(@RequestParam(name = TEACHER_NAME) String teacherName) throws EntityNotFoundException {

        return iTeacherService.getTeacherByName(teacherName);
    }

    @RequestMapping(value = "/getTeacherBySubject", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeacherBySubject(@RequestParam(name = SUBJECT_NAME) String subjectName,
                                             @RequestParam(name = FROM) int from,
                                             @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iTeacherService.getAllTeachersBySubject(subjectName, from, quantity);
    }

    @RequestMapping(value = "/getMaxExperiencedTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getMaxExperiencedTeachers(@RequestParam(name = FROM) int from,
                                                   @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iTeacherService.getMaxExperiencedTeachers(from, quantity);
    }

    @RequestMapping(value = "/getMinExperiencedTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getMinExperiencedTeachers(@RequestParam(name = FROM) int from,
                                                   @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iTeacherService.getMinExperiencedTeachers(from, quantity);
    }

    @RequestMapping(value = "/getTeachersMoreThanExperience", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachersMoreThanExperience(@RequestParam(name = EXPERIENCE) int experience,
                                                       @RequestParam(name = FROM) int from,
                                                       @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iTeacherService.getTeachersMoreThanExperience(experience, from, quantity);
    }
}
