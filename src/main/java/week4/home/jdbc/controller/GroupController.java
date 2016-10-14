package week4.home.jdbc.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import week4.home.jdbc.dao.impls.mysql.GroupServiceImpl;
import week4.home.jdbc.dao.services.IGroupService;
import week4.home.jdbc.entity.Group;

import java.awt.*;

public class GroupController {
    private IGroupService iGroupService = new GroupServiceImpl();
    private ObservableList<Group> listOfGroups;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private TableView tableGroups;
    @FXML
    private TableColumn<Group, String> columnGroup;
    @FXML
    private TableColumn<Group, String> columnBiology;
    @FXML
    private TableColumn<Group, String> columnPhysics;
    @FXML
    private TableColumn<Group, String> columnPsychology;
    @FXML
    private TableColumn<Group, String> columnMathematic;
    @FXML
    private TableColumn<Group, String> columnEconomy;
    @FXML
    private TableColumn<Group, String> columnLiterature;
    @FXML
    private TableColumn<Group, String> columnComputerScience;
    @FXML
    private javafx.scene.control.Label countOfGroups;
    @FXML
    private javafx.scene.control.Label totalUsedMemory;

    public void printStudentList(ActionEvent actionEvent) {

    }

    public void addGroup(ActionEvent actionEvent) {
    }
}
