package week4.home.study.dao.services;

import week4.home.study.dao.impls.mysql.GroupServiceImpl;
import week4.home.study.dao.impls.mysql.StudentServiceImpl;
import week4.home.study.dao.impls.mysql.SubjectServiceImpl;
import week4.home.study.dao.impls.mysql.TeacherServiceImpl;

public class ServiceFactory {
    private static IGroupService iGroupService;
    private static ITeacherService iTeacherService;
    private static IStudentService iStudentService;
    private static ISubjectService iSubjectService;

    private ServiceFactory(){}

    public static IGroupService getGroupInstance() {
        if (iGroupService == null) {
            iGroupService = new GroupServiceImpl();
        }
        return iGroupService;
    }

    public static ITeacherService getTeacherInstance() {
        if (iTeacherService == null) {
            iTeacherService = new TeacherServiceImpl();
        }
        return iTeacherService;
    }

    public static IStudentService getStudentInstance() {
        if (iStudentService == null) {
            iStudentService = new StudentServiceImpl();
        }
        return iStudentService;
    }

    public static ISubjectService getSubjectInstance() {
        if (iSubjectService == null) {
            iSubjectService = new SubjectServiceImpl();
        }
        return iSubjectService;
    }
}
