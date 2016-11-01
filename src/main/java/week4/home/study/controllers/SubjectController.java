package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.service.interfaces.ISubjectService;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@RestController
public class SubjectController {
    @Autowired
    private ISubjectService iSubjectService;

    @RequestMapping(value = "/addSubject", method = RequestMethod.POST)
    @ResponseBody
    public String addSubject(@RequestParam(name = SUBJECT_NAME) String subjectName,
                             @RequestParam(name = DESCRIPTION) String description) throws EntityAlreadyExistException, ComingNullObjectException {

        iSubjectService.addSubject(subjectName, description);

        return addOperationInfo(subjectName);
    }

    @RequestMapping(value = "/getAllSubjects", method = RequestMethod.GET)
    @ResponseBody
    public List<Subject> getAllSubjects(@RequestParam(name = FROM) int from,
                                        @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iSubjectService.getAllSubjects(from, quantity);
    }

    @RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
    @ResponseBody
    public String updateSubject(@RequestParam(name = OLD_NAME) String oldName,
                                @RequestParam(name = NEW_NAME) String newName,
                                @RequestParam(name = DESCRIPTION) String description) throws ComingNullObjectException, EntityAlreadyExistException, EntityNotFoundException {

        iSubjectService.updateSubject(oldName, newName, description);

        return updateOperationInfo(oldName);
    }

    @RequestMapping(value = "/removeSubject", method = RequestMethod.POST)
    @ResponseBody
    public String removeSubject(@RequestParam(name = SUBJECT_NAME) String subjectName) throws EntityNotFoundException {

        iSubjectService.removeSubject(subjectName);

        return removeOperationInfo(subjectName);
    }

    @RequestMapping(value = "/getSubjectByTeacher", method = RequestMethod.GET)
    @ResponseBody
    public Subject getSubjectByTeacher(@RequestParam(name = TEACHER_NAME) String teacherName) throws EntityNotFoundException {

        return iSubjectService.getSubjectByTeacher(teacherName);
    }

    @RequestMapping(value = "/getAllSubjectsByGroupName", method = RequestMethod.GET)
    @ResponseBody
    public List<Subject> getAllSubjectsByGroupName(@RequestParam(name = GROUP_NAME) String name,
                                                   @RequestParam(name = FROM) int from,
                                                   @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iSubjectService.getAllSubjectsByGroupName(name, from, quantity);
    }

    @RequestMapping(value = "/getAllSubjectsByNameLike", method = RequestMethod.GET)
    @ResponseBody
    public List<Subject> getAllSubjectsByNameLike(@RequestParam(name = SUBJECT_NAME) String subjectName,
                                                  @RequestParam(name = FROM) int from,
                                                  @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iSubjectService.getAllSubjectsByNameLike(subjectName, from, quantity);
    }
}
