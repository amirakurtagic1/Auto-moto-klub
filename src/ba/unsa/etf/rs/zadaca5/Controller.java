package ba.unsa.etf.rs.zadaca5;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

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



    public void initialize(){
            instance = VehicleDAOBase.getInstance();
            tableOwners.setItems(instance.getOwners());
            tableVehicles.setItems(instance.getVehicles());
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
        OwnerController controller = loader.getController();
        if(ownerToSend == null) {
            if (!myStage.isShowing()) {
                System.out.println("ovdje");
                Owner owner = controller.getOwner();
                if (owner != null && ownerToSend == null) {
                    instance.addOwner(owner);
                    instance.getOwners();
                }
                else if(owner != null && ownerToSend != null){
                    owner.setId(ownerToSend.getId());
                    instance.changeOwner(owner);
                    instance.getOwners();
                }
            }
        }
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
        VehicleController controller = loader.getController();
        System.out.println(controller.getVehicle());
            if (!myStage.isShowing()) {
                System.out.println("Doslo je ovdje");
                Vehicle vehicle = controller.getVehicle();
                if (vehicle != null && vehicleToSend == null) {
                    instance.addVehicle(vehicle);
                    instance.getVehicles();
                }
                else if(vehicle != null && vehicleToSend != null){
                    instance.changeVehicle(vehicle);
                    instance.getVehicles();
            }
        }
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
            if (result.get() == ButtonType.OK && doesntHave(tableOwners.getSelectionModel().getSelectedItem()) == true) {
                    try {
                        instance.deleteOwner(tableOwners.getSelectionModel().getSelectedItem());
                        instance.getOwners();
                    }catch (Exception e){
                        Alert alertForError = new Alert(Alert.AlertType.ERROR);
                        alertForError.setTitle("Error Dialog");
                        alertForError.setHeaderText("Došlo je do greške");
                        alertForError.setContentText("Ne možete obrisati selektovanu osobu jer posjeduje vozilo!");
                        alert.showAndWait();
                }
        }
            else {
                Alert alertForError = new Alert(Alert.AlertType.ERROR);
                alertForError.setTitle("Error Dialog");
                alertForError.setHeaderText("Došlo je do greške");
                alertForError.setContentText("Ne možete obrisati selektovanu osobu jer posjeduje vozilo!");
                alert.showAndWait();
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
    }

    public void actionEditVehicle(ActionEvent actionEvent) {
        try {
            opetAWindowAndGetVehicle(tableVehicles.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

