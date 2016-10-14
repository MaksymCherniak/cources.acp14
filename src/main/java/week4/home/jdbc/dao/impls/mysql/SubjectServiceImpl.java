package week4.home.jdbc.dao.impls.mysql;

import week4.home.jdbc.annotations.ColumnName;
import week4.home.jdbc.dao.JDBCDriver;
import week4.home.jdbc.dao.services.ISubjectService;
import week4.home.jdbc.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import static week4.home.jdbc.main.AppStaticValues.*;

public class SubjectServiceImpl implements ISubjectService {
    private static Logger log = Logger.getLogger(SubjectServiceImpl.class.getName());
    private Connection connection;

    public void addSubject(Subject subject) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into subject values (?, ?, ?);");
            preparedStatement.setLong(1, subject.getId());
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setString(3, subject.getDescription());
            preparedStatement.execute();
            log.info(subject.toString() + LOG_OPERATION_ADD);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public void removeSubject(long id) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from subject where id=?;");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            log.info("Subject with id=" + id + LOG_OPERATION_REMOVE);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public void updateSubject(Subject subject) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE subject set name=?, description=? WHERE id=?;");
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setString(2, subject.getDescription());
            preparedStatement.setLong(3, subject.getId());
            preparedStatement.execute();
            log.info(subject.toString() + LOG_OPERATION_UPDATE);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public Subject getSubjectById(long id) {
        connection = JDBCDriver.getConnection();
        Subject subject = new Subject();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT FROM subject WHERE id=?;");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                subject.setId(resultSet.getLong(getAnnotationValue(ID)));
                subject.setName(resultSet.getString(getAnnotationValue(NAME)));
                subject.setDescription(resultSet.getString(getAnnotationValue(DESCRIPTION)));
            } else {
                log.info("Subject not found");
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

        return subject;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> listOfSubjects = new ArrayList<Subject>();
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from subject LIMIT 100");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getLong(ID));
                subject.setName(resultSet.getString(NAME));
                subject.setDescription(resultSet.getString(DESCRIPTION));
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }

        return listOfSubjects;
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.info(ERROR_CONNECTION_CLOSE);
        }
    }

    private String getAnnotationValue(String attribute) throws NoSuchFieldException {
        return Subject.class.getDeclaredField(attribute).getAnnotation(ColumnName.class).value();
    }
}
