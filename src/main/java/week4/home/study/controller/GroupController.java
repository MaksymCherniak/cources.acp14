package week4.home.study.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import week4.home.study.dao.impls.mysql.GroupServiceImpl;
import week4.home.study.dao.services.IGroupService;
import week4.home.study.entity.Groups;

import java.awt.*;

public class GroupController {
    private IGroupService iGroupService = new GroupServiceImpl();
    private ObservableList<Groups> listOfGroupses;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private TableView tableGroups;
    @FXML
    private TableColumn<Groups, String> columnGroup;
    @FXML
    private TableColumn<Groups, String> columnBiology;
    @FXML
    private TableColumn<Groups, String> columnPhysics;
    @FXML
    private TableColumn<Groups, String> columnPsychology;
    @FXML
    private TableColumn<Groups, String> columnMathematic;
    @FXML
    private TableColumn<Groups, String> columnEconomy;
    @FXML
    private TableColumn<Groups, String> columnLiterature;
    @FXML
    private TableColumn<Groups, String> columnComputerScience;
    @FXML
    private javafx.scene.control.Label countOfGroups;
    @FXML
    private javafx.scene.control.Label totalUsedMemory;

    public void printStudentList(ActionEvent actionEvent) {

    }

    public void addGroup(ActionEvent actionEvent) {
    }
}
