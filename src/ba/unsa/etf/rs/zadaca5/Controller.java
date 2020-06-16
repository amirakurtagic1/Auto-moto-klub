package ba.unsa.etf.rs.zadaca5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Controller {
    public Menu menuOptions;
    public RadioMenuItem menuDb;
    public RadioMenuItem menuXml;
    public Tab ownersTab;
    public TextField fldOwner;
    public Button tbAddOwner;
    public Button tbRemoveOwner;
    public Button tbEditOwner;
    public TableView<Owner> tableOwners;
    public Tab vehiclesTab;
    public TextField fldVehicle;
    public Button tbAddVehicle;
    public Button tbRemoveVehicle;
    public Button tbEditVehicle;
    public TableView<Vehicle> tableVehicles;
    public TableColumn<Owner, Integer> columnIdOwner;
    public TableColumn<Owner, String> columnNameOwner;
    public TableColumn<Owner, String> columnJmbgOwner;
    public TableColumn<Vehicle, Integer> columnIDVehicle;
    public TableColumn<Vehicle, Integer> columnManufacturer;
    public TableColumn<Vehicle, String> columnModelVehicle;
    public TableColumn<Vehicle, String> columnChasisNumberVehicle;
    public TableColumn<Vehicle, String> columnPlateNumberVehicle;
    private VehicleDAO instance;
    private VehicleDAO instanceXml;



    public void initialize(){
        instance = VehicleDAOBase.getInstance();
        ObservableList<Owner> owners = FXCollections.observableArrayList();
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        menuDb.setSelected(true);
        tableOwners.setItems(instance.getOwners());
        tableVehicles.setItems(instance.getVehicles());
        columnProperty();
            menuDb.selectedProperty().addListener((obs, oldValue, newValue)->{
                if(newValue == true){
                    menuXml.setSelected(false);
                    tableOwners.setItems(instance.getOwners());
                    tableVehicles.setItems(instance.getVehicles());
                    columnProperty();
                }
                else if(newValue == false){
                    tableOwners.setItems(owners);
                    tableVehicles.setItems(vehicles);
                }
            });
        menuXml.selectedProperty().addListener((obs, oldValue, newValue)->{
            if(newValue == true){
                menuDb.setSelected(false);
                instanceXml = new VehicleDAOXML();
                tableOwners.setItems(instanceXml.getOwners());
                tableVehicles.setItems(instanceXml.getVehicles());
                columnProperty();

            }
            else {
                tableOwners.setItems(owners);
                tableVehicles.setItems(vehicles);
            }
        });
    }

    private void columnProperty(){
        columnIdOwner.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNameOwner.setCellValueFactory(new PropertyValueFactory<>("nameAndSurname"));
        columnJmbgOwner.setCellValueFactory(new PropertyValueFactory<>("jmbg"));
        columnIDVehicle.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        columnModelVehicle.setCellValueFactory(new PropertyValueFactory<>("model"));
        columnChasisNumberVehicle.setCellValueFactory(new PropertyValueFactory<>("chasisNumber"));
        columnPlateNumberVehicle.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
    }


    private void opetAWindowAndGetOwner(Owner ownerToSend) throws IOException {
        Stage myStage = new Stage();
        System.out.println(ownerToSend);
        Owner crntOwner = null;
        VehicleDAO model = VehicleDAOBase.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/owner.fxml"));
        if(ownerToSend == null)
        loader.setController(new OwnerController());
        if(ownerToSend!= null) loader.setController(new OwnerController(instance, ownerToSend));
        Parent root = loader.load();
        if(ownerToSend== null) myStage.setTitle("Dodaj vlasnika");
        else myStage.setTitle("Uredi vlasnika");
        myStage.initOwner(tbAddOwner.getScene().getWindow());
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
    }

    private void opetAWindowAndGetVehicle(Vehicle vehicleToSend) throws IOException {
        Stage myStage = new Stage();
        Vehicle crntVehicle = null;
        VehicleDAO model = VehicleDAOBase.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehicle.fxml"));
        if(vehicleToSend == null)
            loader.setController(new VehicleController());
        if(vehicleToSend!= null) loader.setController(new VehicleController(instance, vehicleToSend));
        Parent root = loader.load();
        if(vehicleToSend== null) myStage.setTitle("Dodaj vozilo");
        else myStage.setTitle("Uredi vozilo");
        myStage.initOwner(tbAddOwner.getScene().getWindow());
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
    }
    public void actionAddOwner(ActionEvent actionEvent) throws IOException {
        opetAWindowAndGetOwner(null);
    }

    public void actionRemoveOwner(ActionEvent actionEvent) {
        if (tableOwners.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Traži se potvrda od korisnika!");
            alert.setContentText("Jeste li sigurni da želite obrisati selektovanog vlasnika?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                    try {
                        instance.deleteOwner(tableOwners.getSelectionModel().getSelectedItem());
                        instanceXml.deleteOwner(tableOwners.getSelectionModel().getSelectedItem());
                        instance.getOwners();
                    }catch (Exception e){
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error Dialog");
                        alertError.setHeaderText(null);
                        alertError.setContentText("Brisanje nije uspjelo. Dati vlasnik posjeduje vozilo!");
                        alertError.showAndWait();
                }
        }
            }
    }

    private boolean doesntHave(Owner owner){
        ObservableList<Vehicle> vehicles = instance.getVehicles();
        for(Vehicle x: vehicles){
            if(x.getOwner().equals(owner))
                return false;
        }
        return true;
    }

    public void actionEditOwner(ActionEvent actionEvent) {
        try {
            if(tableOwners.getSelectionModel().getSelectedItem() != null)
            opetAWindowAndGetOwner(tableOwners.getSelectionModel().getSelectedItem());
            System.out.println(tableOwners.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionAddVehicle(ActionEvent actionEvent) {
        try {
            opetAWindowAndGetVehicle(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveVehicle(ActionEvent actionEvent) {
        if(tableVehicles.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Jeste li sigurni da želite obrisati selektovano vozilo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try{
                    instance.deleteVehicle(tableVehicles.getSelectionModel().getSelectedItem());
                    instanceXml.deleteVehicle(tableVehicles.getSelectionModel().getSelectedItem());
                }catch (Exception e){
                    e.getMessage();
                }
            } else {
                alert.close();
            }

        }
    }

    public void actionEditVehicle(ActionEvent actionEvent) {
        try {
            if(tableVehicles.getSelectionModel().getSelectedItem()!= null) opetAWindowAndGetVehicle(tableVehicles.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

