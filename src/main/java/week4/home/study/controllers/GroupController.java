package week4.home.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import week4.home.study.entity.Groups;
import week4.home.study.exceptions.ComingNullObjectException;
import week4.home.study.exceptions.EntityAlreadyExistException;
import week4.home.study.exceptions.EntityNotFoundException;
import week4.home.study.exceptions.InvalidNameFormatException;
import week4.home.study.service.interfaces.IGroupService;

import java.util.List;

import static week4.home.study.main.AppStaticValues.*;

@RestController
public class GroupController {
    @Autowired
    private IGroupService iGroupService;

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(@RequestParam(name = GROUP_NAME) String groupName) throws EntityAlreadyExistException
            , ComingNullObjectException, InvalidNameFormatException, EntityNotFoundException {

        iGroupService.addGroup(groupName);

        return addOperationInfo(groupName);
    }

    @RequestMapping(value = "/getAllGroups", method = RequestMethod.GET)
    @ResponseBody
    public List<Groups> getAllGroups(@RequestParam(name = FROM) int from,
                                     @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iGroupService.getAllGroups(from, quantity);
    }

    @RequestMapping(value = "/getGroupsBySubject", method = RequestMethod.GET)
    @ResponseBody
    public List<Groups> getGroupsBySubject(@RequestParam(name = SUBJECT_NAME) String subjectName,
                                           @RequestParam(name = FROM) int from,
                                           @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iGroupService.getGroupsBySubject(subjectName, from, quantity);
    }

    @RequestMapping(value = "/groupSetSubjects", method = RequestMethod.POST)
    @ResponseBody
    public String groupSetSubjects(@RequestParam(name = GROUP_NAME) String groupName,
                                   @RequestParam(name = SUBJECT_NAME) String subjectName)
            throws ComingNullObjectException, EntityAlreadyExistException, EntityNotFoundException, InvalidNameFormatException {

        iGroupService.setSubject(groupName, subjectName);

        return updateOperationInfo(groupName);
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    @ResponseBody
    public String updateGroup(@RequestParam(name = OLD_NAME) String oldName,
                              @RequestParam(name = NEW_NAME) String newName) throws ComingNullObjectException
            , EntityAlreadyExistException, EntityNotFoundException, InvalidNameFormatException {

        iGroupService.updateGroup(oldName, newName);

        return updateOperationInfo(oldName);
    }

    @RequestMapping(value = "/removeGroup", method = RequestMethod.POST)
    @ResponseBody
    public String removeGroup(@RequestParam(name = GROUP_NAME) String groupName) throws EntityNotFoundException {

        iGroupService.removeGroup(groupName);

        return removeOperationInfo(groupName);
    }

    @RequestMapping(value = "/getGroupByName", method = RequestMethod.GET)
    @ResponseBody
    public Groups getGroup(@RequestParam(name = GROUP_NAME) String groupName) throws EntityNotFoundException
            , ComingNullObjectException, InvalidNameFormatException {

        return iGroupService.getGroupByName(groupName);
    }

    @RequestMapping(value = "/getGroupsLike", method = RequestMethod.GET)
    @ResponseBody
    public List<Groups> getGroupsLike(@RequestParam(name = GROUP_NAME) String groupName,
                                      @RequestParam(name = FROM) int from,
                                      @RequestParam(name = QUANTITY) int quantity) throws EntityNotFoundException {

        return iGroupService.getGroupsLike(groupName, from, quantity);
    }

    @RequestMapping(value = "/getGroupByStudent", method = RequestMethod.GET)
    @ResponseBody
    public Groups getGroupByStudent(@RequestParam(name = STUDENT_NAME) String studentName) throws EntityNotFoundException {

        return iGroupService.getGroupByStudentName(studentName);
    }
}
