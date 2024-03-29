package ba.unsa.etf.rs.zadaca5;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;


public class VehicleController {
    public ComboBox manufacturerCombo;
    public TextField modelField;
    public TextField chasisNumberField;
    public TextField plateNumberField;
    public ComboBox ownerCombo;
    public Button okButton;
    public Button cancelButton;
    private VehicleDAO instance = null;
    private VehicleDAO instanceXml;
    private Vehicle vehicle;
    private Vehicle newVehicle = new Vehicle();
    private Manufacturer newmanufacturer = new Manufacturer();

    public VehicleController(VehicleDAO instance, Vehicle vehicle) {
        this.instance = instance;
        this.vehicle = vehicle;
    }

    public VehicleController() {
    }

    public void initialize(){
        instanceXml = new VehicleDAOXML();
        if(instance == null) instance = VehicleDAOBase.getInstance();
        ObservableList<Owner> owners = instance.getOwners();
        Collections.sort(owners, Comparator.comparing(Owner::getSurname));
        ownerCombo.setItems(owners);
        manufacturerCombo.setItems(instance.getManufacturers());

        if(vehicle == null){
            modelField.getStyleClass().add("poljeNijeIspravno");
            chasisNumberField.getStyleClass().add("poljeNijeIspravno");
            plateNumberField.getStyleClass().add("poljeNijeIspravno");
            manufacturerCombo.getStyleClass().add("poljeNijeIspravno");
            Validation();
        }
        else if(vehicle != null){
            manufacturerCombo.setValue(vehicle.getManufacturer());
            modelField.setText(vehicle.getModel());
            chasisNumberField.setText(vehicle.getChasisNumber());
            plateNumberField.setText(vehicle.getPlateNumber());
            ownerCombo.setValue(vehicle.getOwner());

            modelField.getStyleClass().add("poljeIspravno");
            chasisNumberField.getStyleClass().add("poljeIspravno");
            plateNumberField.getStyleClass().add("poljeIspravno");
            manufacturerCombo.getStyleClass().add("poljeIspravno");
            Validation();
        }
    }


    public void actionOkButton(ActionEvent actionEvent) {
        if(everythingIsOkay() == true && vehicle == null) {
            Manufacturer manufacturer = new Manufacturer();
            if(findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString()) == null) {
                 manufacturer = new Manufacturer(0, manufacturerCombo.getSelectionModel().getSelectedItem().toString());
            } else manufacturer = findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString());
          //  Manufacturer manufacturer = findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString());
            Owner owner = findOwner(ownerCombo.getSelectionModel().getSelectedItem().toString());
              newVehicle.setId(0);
              newVehicle.setManufacturer(manufacturer);
              newVehicle.setModel(modelField.getText());
              newVehicle.setChasisNumber(chasisNumberField.getText());
              newVehicle.setPlateNumber(plateNumberField.getText());
              newVehicle.setOwner(owner);
              instance.addVehicle(newVehicle);
              instanceXml.addVehicle(newVehicle);
              instanceXml.getVehicles();
              instance.getVehicles();
              ((Stage) okButton.getScene().getWindow()).close();
        }
        else if(everythingIsOkay() && vehicle != null){
            Manufacturer manufacturer = new Manufacturer();
            if(findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString()) == null) {
                manufacturer = new Manufacturer(0, manufacturerCombo.getSelectionModel().getSelectedItem().toString());
            } else manufacturer = findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString());
            //  Manufacturer manufacturer = findManufacturer(manufacturerCombo.getSelectionModel().getSelectedItem().toString());
            Owner owner = findOwner(ownerCombo.getSelectionModel().getSelectedItem().toString());
            vehicle.setModel(modelField.getText());
            vehicle.setManufacturer(manufacturer);
            vehicle.setChasisNumber(chasisNumberField.getText());
            vehicle.setPlateNumber(plateNumberField.getText());
            vehicle.setOwner(owner);
            instance.changeVehicle(vehicle);
            instanceXml.changeVehicle(vehicle);
            instanceXml.getVehicles();
            instance.getVehicles();
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
    private boolean validationOfPlateNumber(String plateNumber){
        return Pattern.matches("[AEJKMO][1-9]{2}-[AEJKMO]-[1-9]{3}$", plateNumber);
    }

    private Manufacturer findManufacturer(String name){
        ObservableList<Manufacturer> manufacturers = instance.getManufacturers();
        for(Manufacturer x: manufacturers) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }
    private void addManufacturer(){
        instance.addManufacturer(newmanufacturer);
        instanceXml.addManufacturer(newmanufacturer);
    }

    private Owner findOwner(String name){
        ObservableList<Owner> owners = instance.getOwners();
        for(Owner x: owners){
            String nameSurname = x.getSurname() + " " + x.getName();
            System.out.println(nameSurname);
            if(nameSurname.equals(name)) {
                return x;
            }
        }
        return null;
    }
    public void actionCancelButton(ActionEvent actionEvent) {
        newVehicle = null;
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
            if(!newValue.equals("") && validationOfPlateNumber(newValue) == true){
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
