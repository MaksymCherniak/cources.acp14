package week4.home.study.controllers;

import org.springframework.web.bind.annotation.*;
import week4.home.study.dao.interfaces.DaoFactory;
import week4.home.study.dao.interfaces.ISubjectDao;
import week4.home.study.entity.Subject;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@RestController
public class SubjectController {
    private ISubjectDao iSubjectDao = DaoFactory.getSubjectInstance();

    @RequestMapping(value = "/addSubject", method = RequestMethod.POST)
    @ResponseBody
    public String addSubject(@RequestBody Subject subject) throws EntityAlreadyExistException, ComingNullObjectException
            , OperationFailedException {

        iSubjectDao.addSubject(subject);

        return subject.toString() + LOG_OPERATION_ADD;
    }

    @RequestMapping(value = "/getAllSubjects", method = RequestMethod.GET)
    @ResponseBody
    public List<Subject> getAllSubjects(@RequestParam(name = "from") int from,
                                        @RequestParam(name = "quantity") int quantity) {

        return iSubjectDao.getAllSubjects(from, quantity);
    }

    @RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
    @ResponseBody
    public String updateSubject(@RequestBody Subject subject) throws ComingNullObjectException, OperationFailedException {

        iSubjectDao.updateSubject(subject);

        return subject.toString() + LOG_OPERATION_UPDATE;
    }

    @RequestMapping(value = "/removeSubject", method = RequestMethod.POST)
    @ResponseBody
    public String removeSubject(@RequestParam long id) throws EntityNotFoundException {

        Subject subject = iSubjectDao.getSubjectById(id);

        iSubjectDao.removeSubject(id);

        return subject.toString() + LOG_OPERATION_REMOVE;
    }

    @RequestMapping(value = "/getSubject", method = RequestMethod.GET)
    @ResponseBody
    public Subject getSubject(@RequestParam(name = "name") String name,
                              @RequestParam(name = "description") String description) throws EntityNotFoundException {

        return iSubjectDao.getSubject(new Subject(name, description));
    }

    @RequestMapping(value = "/getSubjectById", method = RequestMethod.GET)
    @ResponseBody
    public Subject getSubjectById(@RequestParam(name = "id") long id) throws EntityNotFoundException {

        return iSubjectDao.getSubjectById(id);
    }
}
