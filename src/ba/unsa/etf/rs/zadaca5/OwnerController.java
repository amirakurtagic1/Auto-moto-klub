package ba.unsa.etf.rs.zadaca5;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

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
    private Owner newOwner = new Owner();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/mm/yyyy");
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
            placeOfBirth.setValue(owner.getPlaceOfBirth());
            addressPlace.setValue(owner.getLivingPlace());
            postalNumberField.setText(owner.getLivingPlace().getPostalNumber());

            nameField.getStyleClass().add("poljeIspravno");
            surnameField.getStyleClass().add("poljeIspravno");
            parentNameField.getStyleClass().add("poljeIspravno");
            addressField.getStyleClass().add("poljeIspravno");
            jmbgField.getStyleClass().add("poljeIspravno");
            dateField.getStyleClass().add("poljeIspravno");
            placeOfBirth.getStyleClass().add("poljeIspravno");
            addressPlace.getStyleClass().add("poljeIspravno");
            postalNumberField.getStyleClass().add("poljeIspravno");
            Validation();
        }
        else{

            nameField.getStyleClass().add("poljeNijeIspravno");
            surnameField.getStyleClass().add("poljeNijeIspravno");
            parentNameField.getStyleClass().add("poljeNijeIspravno");
            addressField.getStyleClass().add("poljeNijeIspravno");
            jmbgField.getStyleClass().add("poljeNijeIspravno");
            dateField.getStyleClass().add("poljeNijeIspravno");
            placeOfBirth.getStyleClass().add("poljeNijeIspravno");
            addressPlace.getStyleClass().add("poljeNijeIspravno");
            postalNumberField.getStyleClass().add("poljeNijeIspravno");
            Validation();
        }
    }

    public void actionOkButton(ActionEvent actionEvent) {
        if(everythingIsOkay()==true){
                newOwner.setName(nameField.getText());
                newOwner.setSurname(surnameField.getText());
                newOwner.setParentName(parentNameField.getText());
                newOwner.setLivingAddress(addressField.getText());
                newOwner.setJmbg(jmbgField.getText());
                newOwner.setDateOfBirth(dateField.getValue());
                newOwner.setLivingPlace(findPlace(addressPlace.getSelectionModel().getSelectedItem().toString()));
                newOwner.setPlaceOfBirth(findPlace(placeOfBirth.getSelectionModel().getSelectedItem().toString()));
            ((Stage) okButton.getScene().getWindow()).close();
        }
    }

    private boolean everythingIsOkay(){
        if(nameField.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(surnameField.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(parentNameField.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(addressField.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(jmbgField.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(dateField.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(placeOfBirth.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(addressPlace.getStyleClass().contains("poljeNijeIspravno")) return false;
        else if(postalNumberField.getStyleClass().contains("poljeNijeIspravno")) return false;

        return true;
    }


    public void actionCacelButton(ActionEvent actionEvent) {
        newOwner=null;
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public Owner getOwner() {
        return  newOwner;
    }
    public void Validation(){

        nameField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(oldValue == null || oldValue.equals("")){
                nameField.getStyleClass().removeAll("poljeIspravno");
                nameField.getStyleClass().add("poljeNijeIspravno");
            }
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
        postalNumberField.textProperty().addListener(((obs, oldValue, newValue)->{
            if(newValue == null || newValue.equals("")){
                postalNumberField.getStyleClass().removeAll("poljeIspravno");
                postalNumberField.getStyleClass().add("poljeNijeIspravno");
            }
            if(!newValue.equals("")){
                postalNumberField.getStyleClass().removeAll("poljeNijeIspravno");
                postalNumberField.getStyleClass().add("poljeIspravno");
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
                postalNumberField.setText(findPlace(addressPlace.getSelectionModel().getSelectedItem().toString()).getPostalNumber());
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
    private Place findPlace(String name){
        ObservableList<Place> places = instance.getPlaces();
        Place place = new Place();
        place = null;
        for(Place x: places){
            if(x.getName().equals(name)){
                return x;
            }
        }
        if(postalNumberField.getText()!= null){
             place = new Place(0, name, postalNumberField.getText());
            instance.addPlace(place);
        }
        return place;
    }
}
