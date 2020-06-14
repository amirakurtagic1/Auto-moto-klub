package ba.unsa.etf.rs.zadaca5;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

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
            Validation();
        }
    }



    public void actionOkButton(ActionEvent actionEvent) {
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
    }
}
