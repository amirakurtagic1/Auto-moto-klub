package ba.unsa.etf.rs.zadaca5;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private void initialize(){
        if(owner != null){
            nameField.setText(owner.getName());
            surnameField.setText(owner.getSurname());
            parentNameField.setText(owner.getParentName());
            addressField.setText(owner.getLivingAddress());
            jmbgField.setText(owner.getJmbg());
            dateField.setValue(owner.getDateOfBirth());
            placeOfBirth.setItems(instance.getPlaces());
            addressPlace.setItems(instance.getPlaces());
        }
    }

    public void actionOkButton(ActionEvent actionEvent) {
    }

    public void actionCacelButton(ActionEvent actionEvent) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public Owner getOwner() {
        return  newOwner;
    }
}
