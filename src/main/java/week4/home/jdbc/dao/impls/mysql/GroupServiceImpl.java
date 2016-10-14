package week4.home.jdbc.dao.impls.mysql;

import week4.home.jdbc.annotations.ColumnName;
import week4.home.jdbc.dao.JDBCDriver;
import week4.home.jdbc.dao.services.IGroupService;
import week4.home.jdbc.entity.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import static week4.home.jdbc.main.AppStaticValues.*;

public class GroupServiceImpl implements IGroupService {
    private static Logger log = Logger.getLogger(GroupServiceImpl.class.getName());
    private Connection connection;

    public void addGroup(Group group) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into groups values (?, ?);");
            preparedStatement.setLong(1, group.getId());
            preparedStatement.setString(2, group.getName());
            preparedStatement.execute();
            log.info(group.toString() + LOG_OPERATION_ADD);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public void removeGroup(long id) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from groups where id=?;");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            log.info("Group with id=" + id + LOG_OPERATION_REMOVE);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public void updateGroup(Group group) {
        connection = JDBCDriver.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE groups set name=? WHERE id=?;");
            preparedStatement.setString(1, group.getName());
            preparedStatement.setLong(2, group.getId());
            preparedStatement.execute();
            log.info(group.toString() + LOG_OPERATION_UPDATE);

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } finally {
            closeConnection();
        }
    }

    public Group getGroupById(long id) {
        connection = JDBCDriver.getConnection();
        Group group = new Group();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT FROM groups WHERE id=?;");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                group.setId(resultSet.getLong(getAnnotationValue(ID)));
                group.setName(resultSet.getString(getAnnotationValue(NAME)));
            } else {
                log.info("Group not found");
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

        return group;
    }

    public List<Group> getAllGroups() {
        List<Group> listOfGroups = new ArrayList<Group>();
        connection = JDBCDriver.getConnection();

        try {
            ResultSet resultSet = connection.prepareStatement("select * from groups LIMIT 100").executeQuery();

            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getLong(getAnnotationValue(ID)));
                group.setName(resultSet.getString(getAnnotationValue(NAME)));
                listOfGroups.add(group);
            }

        } catch (SQLException e) {
            log.info(ERROR_QUERY_PROBLEM);
        } catch (NoSuchFieldException e) {
            log.info(ERROR_FIELD_NOT_FOUND);
        } finally {
            closeConnection();
        }

        return listOfGroups;
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.info(ERROR_CONNECTION_CLOSE);
        }
    }

    private String getAnnotationValue(String attribute) throws NoSuchFieldException {
        return Group.class.getDeclaredField(attribute).getAnnotation(ColumnName.class).value();
    }
}
