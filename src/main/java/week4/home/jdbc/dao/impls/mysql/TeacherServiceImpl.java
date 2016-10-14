package week4.home.jdbc.dao.impls.mysql;

import week4.home.jdbc.annotations.ColumnName;
import week4.home.jdbc.dao.JDBCDriver;
import week4.home.jdbc.dao.services.ITeacherService;
import week4.home.jdbc.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import static week4.home.jdbc.main.AppStaticValues.*;

public class TeacherServiceImpl implements ITeacherService {
    private static Logger log = Logger.getLogger(TeacherServiceImpl.class.getName());
    private Connection connection;

    public void addTeacher(Teacher teacher) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into teacher values (?, ?, ?, ?);");
            preparedStatement.setLong(1, teacher.getId());
            preparedStatement.setString(2, teacher.getName());
            preparedStatement.setInt(3, teacher.getExperience());
            preparedStatement.setLong(4, teacher.getSubject().getId());
            preparedStatement.execute();
            log.info(teacher.toString() + LOG_OPERATION_ADD);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public void removeTeacher(long id) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from teacher where id=?;");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            log.info("Teacher with id=" + id + LOG_OPERATION_REMOVE);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public void updateTeacher(Teacher teacher) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update teacher set name=?, experience=?, subject_id=? WHERE id=?;");
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setInt(2, teacher.getExperience());
            preparedStatement.setLong(3, teacher.getSubject().getId());
            preparedStatement.setLong(4, teacher.getId());
            preparedStatement.execute();
            log.info(teacher.toString() + LOG_OPERATION_UPDATE);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public Teacher getTeacherById(long id) {
        connection = JDBCDriver.getConnection();
        Teacher teacher = new Teacher();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select from teacher WHERE id=?;");
            preparedStatement.setLong(1, teacher.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacher.setId(resultSet.getLong(getAnnotationValue(ID)));
                teacher.setName(resultSet.getString(getAnnotationValue(NAME)));
                teacher.setExperience(resultSet.getInt(getAnnotationValue(EXPERIENCE)));
            } else {
                log.info("Teacher not found");
                closeConnection();
                return null;
            }

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } catch (NoSuchFieldException e) {
            log.info(ERROR_FIELD_NOT_FOUND);
        } finally {
            closeConnection();
        }

        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        return getTeachers("");
    }

    public List<Teacher> getTeacherByExperience() {
        return getTeachers("exp");
    }

    public List<Teacher> getMinExperienced() {
        return getTeachers("min");
    }

    public List<Teacher> getMaxExperienced() {
        return getTeachers("max");
    }

    private List<Teacher> getTeachers(String operation) {
        List<Teacher> listOfTeachers = new ArrayList<Teacher>();
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            switch (operation) {
                case "exp":
                    preparedStatement = connection.prepareStatement("SELECT * from teacher where experience > 3 LIMIT 100");
                    resultSet = preparedStatement.executeQuery();
                    break;
                case "min":
                    preparedStatement = connection.prepareStatement("select * from teacher WHERE experience=" +
                            "(SELECT MIN(experience) FROM teacher) LIMIT 100;");
                    resultSet = preparedStatement.executeQuery();
                    break;
                case "max":
                    preparedStatement = connection.prepareStatement("select * from teacher WHERE experience=" +
                            "(SELECT MAX(experience) FROM teacher) LIMIT 100;");
                    resultSet = preparedStatement.executeQuery();
                    break;
                case "all":
                    preparedStatement = connection.prepareStatement("select * from teacher LIMIT 100");
                    resultSet = preparedStatement.executeQuery();
                    break;
                default:
                    resultSet = null;
            }

            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getLong(getAnnotationValue(ID)));
                teacher.setName(resultSet.getString(getAnnotationValue(NAME)));
                teacher.setExperience(resultSet.getInt(getAnnotationValue(EXPERIENCE)));
                listOfTeachers.add(teacher);
            }

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } catch (NoSuchFieldException e) {
            log.info(ERROR_FIELD_NOT_FOUND);
        } finally {
            closeConnection();
        }

        return listOfTeachers;
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.info(ERROR_CONNECTION_CLOSE);
        }
    }

    private String getAnnotationValue(String attribute) throws NoSuchFieldException {
        return Teacher.class.getDeclaredField(attribute).getAnnotation(ColumnName.class).value();
    }
}
