package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class EventsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private ChoiceBox<String> category;

    @FXML
    private TableView<ObservableList> table;

    @FXML
    private PieChart diagram;

    @FXML
    private Button delete;

    @FXML
    private Button add;

    @FXML
    private ImageView events;

    @FXML
    private ImageView backId;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        buildPie();
        ObservableList<String> items = FXCollections.observableArrayList("Продукты", "Развлечения", "ЖКХ", "Одежда", "Прочее");
        category.setItems(items);
        buildData("finance");
        add.setOnAction(even->{
            DatabaseHandler db = new DatabaseHandler();
            String s1 = name.getText().trim();
            String s2 = price.getText().trim();
            String s3 = category.getValue();
            try {
                Double.parseDouble(s2);
                String SQL = "INSERT INTO finance(product, price, category, ulogin) VALUES(?,?,?,?)";

                try {
                    PreparedStatement prSt = db.getDbConnection().prepareStatement(SQL);
                    prSt.setString(1, s1);
                    prSt.setString(2, s2);
                    prSt.setString(3, s3);
                    prSt.setString(4, PrimaryController.activateUser);
                    prSt.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }
                buildData("finance");
                try {
                    buildPie();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный тип данных");
                alert.setContentText("Убедитесь в правильности ввода данных. Сумма должна быть числом!");
                alert.showAndWait();
            }});



        table.setEditable(false);
        buildData("finance");

        backId.setOnMouseClicked(even -> {
                    try {
                        App.openNewScene("tertiary");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        delete.setOnAction(even -> {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            ObservableList del = table.getSelectionModel().getSelectedItem();
            List<String> list = new ArrayList<>();
            for (Object o : del)
                list.add(String.valueOf(o));
            DatabaseHandler dbHandler = new DatabaseHandler();
            String SQL = "DELETE FROM finance WHERE product='"+list.get(0)+"' AND "+"price='"+list.get(1)+"' AND "+
                    "category='"+list.get(2)+"' AND "+"ulogin='"+PrimaryController.activateUser+"'";
            try {
                int rs = dbHandler.getDbConnection().createStatement().executeUpdate(SQL);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            buildData("finance");
            try {
                buildPie();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public ObservableList<ObservableList> buildData(String tableName) {
        table.getColumns().clear();
        ObservableList<ObservableList> data;
        DatabaseHandler dbHandler = new DatabaseHandler();
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM "+tableName;
            ResultSet rs = dbHandler.getDbConnection().createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                if(row.contains(PrimaryController.activateUser))
                    data.add(row);
            }
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        } return data;
    }

    private void buildPie() throws SQLException, ClassNotFoundException {
        diagram.getData().clear();
        DatabaseHandler db = new DatabaseHandler();

        String sql1 = "SELECT price FROM finance WHERE category='Продукты' AND ulogin='"+PrimaryController.activateUser+"'";
        String sql2 = "SELECT price FROM finance WHERE category='ЖКХ'AND ulogin='"+PrimaryController.activateUser+"'";
        String sql3 = "SELECT price FROM finance WHERE category='Развлечения'AND ulogin='"+PrimaryController.activateUser+"'";
        String sql4 = "SELECT price FROM finance WHERE category='Прочее'AND ulogin='"+PrimaryController.activateUser+"'";
        String sql5 = "SELECT price FROM finance WHERE category='Одежда'AND ulogin='"+PrimaryController.activateUser+"'";

        ResultSet rs1 = db.getDbConnection().createStatement().executeQuery(sql1);
        ResultSet rs2 = db.getDbConnection().createStatement().executeQuery(sql2);
        ResultSet rs3 = db.getDbConnection().createStatement().executeQuery(sql3);
        ResultSet rs4 = db.getDbConnection().createStatement().executeQuery(sql4);
        ResultSet rs5 = db.getDbConnection().createStatement().executeQuery(sql5);

        double sum1=0;
        double sum2=0;
        double sum3=0;
        double sum4=0;
        double sum5=0;

        while(rs1.next())
            sum1+=Double.parseDouble(rs1.getString(1));

        while(rs2.next())
            sum2+=Double.parseDouble(rs2.getString(1));

        while(rs3.next())
            sum3+=Double.parseDouble(rs3.getString(1));

        while(rs4.next())
            sum4+=Double.parseDouble(rs4.getString(1));

        while(rs5.next())
            sum5+=Double.parseDouble(rs5.getString(1));


        PieChart.Data slice1 = new PieChart.Data("Продукты", sum1);
        PieChart.Data slice2 = new PieChart.Data("Одежда", sum5);
        PieChart.Data slice3 = new PieChart.Data("ЖКХ", sum2);
        PieChart.Data slice4 = new PieChart.Data("Развлечения",sum3);
        PieChart.Data slice5 = new PieChart.Data("Прочее", sum4);
        diagram.getData().add(slice1);
        diagram.getData().add(slice2);
        diagram.getData().add(slice3);
        diagram.getData().add(slice4);
        diagram.getData().add(slice5);
        diagram.setLabelsVisible(false);
        diagram.setLegendVisible(true);
        diagram.setLegendSide(Side.BOTTOM);
    }
}
