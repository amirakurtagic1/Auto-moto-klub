package ba.unsa.etf.rs.zadaca5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VehicleController {
    public ComboBox manufacturerCombo;
    public TextField modelField;
    public TextField chasisNumberField;
    public TextField plateNumberField;
    public ComboBox ownerCombo;
    public Button okButton;
    public Button cancelButton;
    private VehicleDAO instance = null;
    private Vehicle vehicle;
    private Vehicle newVehicle;

    public VehicleController(VehicleDAO instance, Vehicle vehicle) {
        this.instance = instance;
        this.vehicle = vehicle;
    }

    public VehicleController() {
    }

    public void initialize(){
        if(instance == null) instance = VehicleDAOBase.getInstance();
        ownerCombo.setItems(instance.getOwners());
        manufacturerCombo.setItems(instance.getManufacturers());

        if(vehicle == null){
            modelField.getStyleClass().add("poljeNijeIspravno");
            chasisNumberField.getStyleClass().add("poljeNijeIspravno");
            plateNumberField.getStyleClass().add("poljeNijeIspravno");
            manufacturerCombo.getStyleClass().add("poljeNijeIspravno");
            Validation();
        }
    }


    public void actionOkButton(ActionEvent actionEvent) {
        if(everythingIsOkay() == true) {
            Manufacturer manufacturer = findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString());
            Owner owner = findOwner(ownerCombo.getSelectionModel().getSelectedItem().toString());
              newVehicle.setManufacturer(manufacturer);
              newVehicle.setModel(modelField.getText());
              newVehicle.setChasisNumber(chasisNumberField.getText());
              newVehicle.setPlateNumber(plateNumberField.getText());
              newVehicle.setOwner(owner);
              ((Stage) okButton.getScene().getWindow()).close();
        }
    }

    private boolean everythingIsOkay(){
        if(modelField.getStyle().equals("poljeNijeIspravno")) return false;
        if(plateNumberField.getStyle().equals("poljeNijeIspravno")) return false;
        if(chasisNumberField.getStyle().equals("poljeNijeIpsravno")) return false;
        if(ownerCombo.getSelectionModel().getSelectedItem() == null) return false;
        if(manufacturerCombo.getStyle().equals("poljeNijeIspravno")) return false;
        return true;
    }

    private Manufacturer findManufacturer(String name){
        ObservableList<Manufacturer> manufacturers = instance.getManufacturers();
        for(Manufacturer x: manufacturers) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        instance.addManufacturer(name);
        for(Manufacturer x: manufacturers) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    private Owner findOwner(String name){
        ObservableList<Owner> owners = instance.getOwners();
        for(Owner x: owners){
            String nameSurname = x.getName() + " " + x.getSurname();
            if(nameSurname.equals(name))
                return x;
        }
        return null;
    }
    public void actionCancelButton(ActionEvent actionEvent) {
        ((Stage)okButton.getScene().getWindow()).close();
    }

    public Vehicle getVehicle(){
        return newVehicle;
    }

    public void Validation(){

        modelField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                modelField.getStyleClass().removeAll("poljeIspravno");
                modelField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                modelField.getStyleClass().removeAll("poljeNijeIspravno");
                modelField.getStyleClass().add("poljeIspravno");
            }
        }));


        chasisNumberField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                chasisNumberField.getStyleClass().removeAll("poljeIspravno");
                chasisNumberField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                chasisNumberField.getStyleClass().removeAll("poljeNijeIspravno");
                chasisNumberField.getStyleClass().add("poljeIspravno");
            }
        }));
        plateNumberField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                plateNumberField.getStyleClass().removeAll("poljeIspravno");
                plateNumberField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                plateNumberField.getStyleClass().removeAll("poljeNijeIspravno");
                plateNumberField.getStyleClass().add("poljeIspravno");
            }
        }));
        manufacturerCombo.valueProperty().addListener((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                manufacturerCombo.getStyleClass().removeAll("poljeIspravno");
                manufacturerCombo.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                manufacturerCombo.getStyleClass().removeAll("poljeNijeIspravno");
                manufacturerCombo.getStyleClass().add("poljeIspravno");
            }
        });

    }
}
