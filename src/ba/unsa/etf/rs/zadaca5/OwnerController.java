package ba.unsa.etf.rs.zadaca5;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class OwnerController {
    public TextField nameField;
    public TextField surnameField;
    public TextField parentNameField;
    public TextField addressField;
    public TextField jmbgField;
    public DatePicker dateField;
    public ComboBox placeOfBirth;
    public ComboBox addressPlace;
    public TextField postalNumberField;
    public Button okButton;
    public Button cancelButton;
    private VehicleDAO instance;
    private Owner owner;
    private Owner newOwner = null;
    public OwnerController(VehicleDAO instance, Owner owner) {
        this.owner = owner;
        this.instance = instance;
    }

    public OwnerController() {
    }

    public void initialize(){
        if(instance == null) instance = VehicleDAOBase.getInstance();
        placeOfBirth.setItems(instance.getPlaces());
        addressPlace.setItems(instance.getPlaces());
        if(owner != null){
            nameField.setText(owner.getName());
            surnameField.setText(owner.getSurname());
            parentNameField.setText(owner.getParentName());
            addressField.setText(owner.getLivingAddress());
            jmbgField.setText(owner.getJmbg());
            dateField.setValue(owner.getDateOfBirth());
            Validation();
        }
        else {

            nameField.getStyleClass().add("poljeNijeIspravno");
            surnameField.getStyleClass().add("poljeNijeIspravno");
            parentNameField.getStyleClass().add("poljeNijeIspravno");
            addressField.getStyleClass().add("poljeNijeIspravno");
            jmbgField.getStyleClass().add("poljeNijeIspravno");
            dateField.getStyleClass().add("poljeNijeIspravno");
            placeOfBirth.getStyleClass().add("poljeNijeIspravno");
            addressPlace.getStyleClass().add("poljeNijeIspravno");
            Validation();
        }
    }

    public void actionOkButton(ActionEvent actionEvent) {
      //  ((Stage) okButton.getScene().getWindow()).close();
    }

    public void actionCacelButton(ActionEvent actionEvent) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public Owner getOwner() {
        return  newOwner;
    }
    public void Validation(){

        nameField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                nameField.getStyleClass().removeAll("poljeIspravno");
                nameField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                nameField.getStyleClass().removeAll("poljeNijeIspravno");
                nameField.getStyleClass().add("poljeIspravno");
            }
        }));


        surnameField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                surnameField.getStyleClass().removeAll("poljeIspravno");
                surnameField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                surnameField.getStyleClass().removeAll("poljeNijeIspravno");
                surnameField.getStyleClass().add("poljeIspravno");
            }
        }));
        parentNameField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                parentNameField.getStyleClass().removeAll("poljeIspravno");
                parentNameField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                parentNameField.getStyleClass().removeAll("poljeNijeIspravno");
                parentNameField.getStyleClass().add("poljeIspravno");
            }
        }));

        addressField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                addressField.getStyleClass().removeAll("poljeIspravno");
                addressField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                addressField.getStyleClass().removeAll("poljeNijeIspravno");
                addressField.getStyleClass().add("poljeIspravno");
            }
        }));

        jmbgField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                jmbgField.getStyleClass().removeAll("poljeIspravno");
                jmbgField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                jmbgField.getStyleClass().add("poljeIspravno");
            }
        }));

        dateField.valueProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue.isAfter(LocalDate.now().plusDays(1))){
                dateField.getStyleClass().removeAll("poljeIspravno");
                dateField.getStyleClass().add("poljeNijeIspravno");
            } else if(newValue.equals("") && oldValue.equals("")) {
                dateField.setValue(LocalDate.now());
                dateField.getStyleClass().removeAll("poljeNijeIspravno");
                dateField.getStyleClass().add("poljeIspravno");
            }
            else{
                dateField.getStyleClass().removeAll("poljeNijeIspravno");
                dateField.getStyleClass().add("poljeIspravno");
            }
        }));

        addressPlace.valueProperty().addListener((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                addressPlace.getStyleClass().removeAll("poljeIspravno");
                addressPlace.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                addressPlace.getStyleClass().removeAll("poljeNijeIspravno");
                addressPlace.getStyleClass().add("poljeIspravno");
            }
        });

        placeOfBirth.valueProperty().addListener((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                placeOfBirth.getStyleClass().removeAll("poljeIspravno");
                placeOfBirth.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                placeOfBirth.getStyleClass().removeAll("poljeNijeIspravno");
                placeOfBirth.getStyleClass().add("poljeIspravno");
            }
        });

    }
}
