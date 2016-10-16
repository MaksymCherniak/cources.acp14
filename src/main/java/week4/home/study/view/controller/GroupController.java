package week4.home.study.view.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import week4.home.study.dao.impls.mysql.GroupDaoImpl;
import week4.home.study.dao.interfaces.IGroupDao;
import week4.home.study.entity.Groups;

import java.awt.*;

public class GroupController {
    private IGroupDao iGroupDao = new GroupDaoImpl();
    private ObservableList<Groups> listOfGroupses;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private TableView tableGroups;
    @FXML
    private TableColumn<Groups, String> columnGroup;
    @FXML
    private TableColumn<Groups, String> columnSubjects;
    @FXML
    private javafx.scene.control.Label countOfGroups;

    public void printStudentList(ActionEvent actionEvent) {

    }

    public void addGroup(ActionEvent actionEvent) {
    }
}
