package org.openjfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ContactsController extends PrimaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField contactsname;

    @FXML
    private TextField contactsphone;

    @FXML
    private TextField contactsemail;

    @FXML
    private TextField contactsname1;

    @FXML
    private TextField contactsphone1;

    @FXML
    private TextField contactsemail1;

    @FXML
    private TableView<ObservableList> contactsTable;

    @FXML
    private ImageView contacts;

    @FXML
    private Button searchButton;

    @FXML
    private TextField search;

    @FXML
    private Button addContact;

    @FXML
    private Button deleteContact;

    @FXML
    private ImageView backC;

    @FXML
    void initialize() {
        searchButton.setOnAction(even->{
            String searchText = search.getText().trim();
            buildDataForName(searchText);
        });

        contactsTable.setEditable(false);
        buildData(Const.CONTACTS_TABLE);

        addContact.setOnAction(even -> {
            Contacts contact = new Contacts();
            contact.setName(contactsname.getText().trim());
            contact.setPhone(contactsphone.getText().trim());
            contact.setEmail(contactsemail.getText().trim());
            DatabaseHandler dbhandler = new DatabaseHandler();
            dbhandler.addContacts(contact);
            buildData(Const.CONTACTS_TABLE);
        });

        backC.setOnMouseClicked(even -> {
            try {
                App.openNewScene("tertiary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteContact.setOnAction(even -> {
        int selectedIndex = contactsTable.getSelectionModel().getSelectedIndex();
        ObservableList del = contactsTable.getSelectionModel().getSelectedItem();
        List<String> list = new ArrayList<>();
        for (Object o : del)
            list.add(String.valueOf(o));

        DatabaseHandler dbHandler = new DatabaseHandler();
        String SQL = "DELETE FROM contacts WHERE " + Const.CONTACTS_NAME + "='" + list.get(0) + "'" + " AND " + Const.CONTACTS_PHONE + "='" + list.get(1) + "'";
        try {
            int rs = dbHandler.getDbConnection().createStatement().executeUpdate(SQL);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        buildData(Const.CONTACTS_TABLE);
        });
    }

    public ObservableList<ObservableList> buildDataForName(String name) {
        contactsTable.getColumns().clear();
        ObservableList<ObservableList> data;
        DatabaseHandler dbHandler = new DatabaseHandler();
        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM contacts WHERE name='"+name+"'";
            ResultSet rs = dbHandler.getDbConnection().createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                contactsTable.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                if(row.contains(activateUser))
                    data.add(row);
            }
            contactsTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        } return data;
    }
        public ObservableList<ObservableList> buildData(String tableName) {
            contactsTable.getColumns().clear();
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
                    contactsTable.getColumns().addAll(col);
                }
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    if(row.contains(activateUser))
                    data.add(row);
                }
                contactsTable.setItems(data);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            } return data;
    }
}

