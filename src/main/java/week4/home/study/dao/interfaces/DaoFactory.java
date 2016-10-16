package week4.home.study.dao.interfaces;

import week4.home.study.dao.impls.mysql.GroupDaoImpl;
import week4.home.study.dao.impls.mysql.StudentDaoImpl;
import week4.home.study.dao.impls.mysql.SubjectDaoImpl;
import week4.home.study.dao.impls.mysql.TeacherDaoImpl;

public class DaoFactory {
    private static IGroupDao iGroupDao;
    private static ITeacherDao iTeacherDao;
    private static IStudentDao iStudentDao;
    private static ISubjectDao iSubjectDao;

    private DaoFactory(){}

    public static IGroupDao getGroupInstance() {
        if (iGroupDao == null) {
            iGroupDao = new GroupDaoImpl();
        }
        return iGroupDao;
    }

    public static ITeacherDao getTeacherInstance() {
        if (iTeacherDao == null) {
            iTeacherDao = new TeacherDaoImpl();
        }
        return iTeacherDao;
    }

    public static IStudentDao getStudentInstance() {
        if (iStudentDao == null) {
            iStudentDao = new StudentDaoImpl();
        }
        return iStudentDao;
    }

    public static ISubjectDao getSubjectInstance() {
        if (iSubjectDao == null) {
            iSubjectDao = new SubjectDaoImpl();
        }
        return iSubjectDao;
    }
}
