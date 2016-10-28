package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.OperationFailedException;

import java.util.List;

import static week4.home.study.start.AppStaticValues.*;

@RestController
public class GroupController {
    @Autowired
    private IGroupDao iGroupDao;

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(@RequestBody Groups groups) throws EntityAlreadyExistException, ComingNullObjectException
            , OperationFailedException {

        iGroupDao.addGroup(groups);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + groups.toString() + LOG_OPERATION_ADD;
    }

    @RequestMapping(value = "/getAllGroups", method = RequestMethod.GET)
    @ResponseBody
    public List<Groups> getAllGroups(@RequestParam(name = "from") int from,
                                     @RequestParam(name = "quantity") int quantity) throws EntityNotFoundException {

        return iGroupDao.getAllGroups(from, quantity);
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    @ResponseBody
    public String updateGroup(@RequestBody Groups groups) throws ComingNullObjectException, OperationFailedException
            , EntityAlreadyExistException {

        iGroupDao.updateGroup(groups);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + groups.toString() + LOG_OPERATION_UPDATE;
    }

    @RequestMapping(value = "/removeGroup", method = RequestMethod.POST)
    @ResponseBody
    public String removeGroup(@RequestParam long id) throws EntityNotFoundException {

        Groups groups = iGroupDao.getGroupById(id);

        iGroupDao.removeGroup(id);

        return String.valueOf(new ResponseEntity<String>(HttpStatus.ACCEPTED)) +
                "\n" + groups.toString() + LOG_OPERATION_REMOVE;
    }

    @RequestMapping(value = "/getGroupByName", method = RequestMethod.GET)
    @ResponseBody
    public Groups getGroup(@RequestParam(name = "name") String name) throws EntityNotFoundException {

        Groups groups = new Groups(name);

        return iGroupDao.getGroup(groups);
    }

    @RequestMapping(value = "/getGroupById", method = RequestMethod.GET)
    @ResponseBody
    public Groups getGroupById(@RequestParam(name = "id") long id) throws EntityNotFoundException {

        return iGroupDao.getGroupById(id);
    }
}
