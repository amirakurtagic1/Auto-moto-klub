package ba.unsa.etf.rs.zadaca5;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;

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
    private VehicleDAO instanceXml;
    private Owner owner;
    private Owner newOwner = new Owner();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/mm/yyyy");
    public OwnerController(VehicleDAO instance, Owner owner) {
        this.owner = owner;
        this.instance = instance;
    }

    public OwnerController() {
    }

    public void initialize(){
        instanceXml = new VehicleDAOXML();
        if(instance == null) instance = VehicleDAOBase.getInstance();

        dateField.setConverter(new StringConverter<LocalDate>() {
            String pattern = "M/d/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dateField.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
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

    public void actionOkButton(ActionEvent actionEvent) throws IOException {
        if(addressPlace.getSelectionModel().getSelectedItem() != null && postalNumberField.getText() != null && !postalNumberField.getText().equals("")) {
            if (findPlace(addressPlace.getSelectionModel().getSelectedItem().toString()) == null) {
                addPlace(addressPlace.getSelectionModel().getSelectedItem().toString());
            }
        }
        if(everythingIsOkay()==true && owner == null) {
            if (validationOfPostalNumber(postalNumberField.getText()).equals("OK")) {
                newOwner.setId(0);
                newOwner.setName(nameField.getText());
                newOwner.setSurname(surnameField.getText());
                newOwner.setParentName(parentNameField.getText());
                newOwner.setLivingAddress(addressField.getText());
                newOwner.setJmbg(jmbgField.getText());
                newOwner.setDateOfBirth(dateField.getValue());
                newOwner.setLivingPlace(findPlace(addressPlace.getSelectionModel().getSelectedItem().toString()));
                newOwner.setPlaceOfBirth(findPlace(placeOfBirth.getSelectionModel().getSelectedItem().toString()));
                instance.addOwner(newOwner);
                instanceXml.addOwner(newOwner);
                instanceXml.getOwners();
                instance.getOwners();
                ((Stage) okButton.getScene().getWindow()).close();
            }
        }
        else if(everythingIsOkay() == true && owner != null){
            owner.setName(nameField.getText());
            owner.setSurname(surnameField.getText());
            owner.setParentName(parentNameField.getText());
            owner.setLivingAddress(addressField.getText());
            owner.setJmbg(jmbgField.getText());
            owner.setDateOfBirth(dateField.getValue());
            owner.setLivingPlace(findPlace(addressPlace.getSelectionModel().getSelectedItem().toString()));
            owner.setPlaceOfBirth(findPlace(placeOfBirth.getSelectionModel().getSelectedItem().toString()));
            instance.changeOwner(owner);
            instanceXml.changeOwner(owner);
            instanceXml.getOwners();
            instance.getOwners();
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

    private String validationOfPostalNumber(String broj) throws IOException {
        URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + broj);
        Scanner sc = new Scanner(url.openStream());
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }
        String result = sb.toString();
        return result;
    }

    public void validationOfJmbg(){
        if(jmbgField.getText().length() != 13){
            jmbgField.getStyleClass().removeAll("poljeIspravno");
            jmbgField.getStyleClass().add("poljeNijeIspravno");
        }
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
            }if(newValue.length() != 13){
                jmbgField.getStyleClass().removeAll("poljeIspravno");
                jmbgField.getStyleClass().add("poljeNijeIspravno");
            } if(newValue.length() == 13){
                if(dateField.getValue() == null || dateField.getValue().equals("")){
                    jmbgField.getStyleClass().removeAll("poljeIspravno");
                    jmbgField.getStyleClass().add("poljeNijeIspravno");
                } else if(dateField.getValue() != null && !dateField.getValue().equals("")){
                    jmbgField.getStyleClass().removeAll("poljeIspravno");
                    jmbgField.getStyleClass().add("poljeNijeIspravno");
                    String[] jmbg = newValue.split("");
                    String day = jmbg[0] + jmbg[1];
                    String month = jmbg[2] + jmbg[3];
                    String year = jmbg[6] + jmbg[5] + jmbg[4];
                    int toIntDay;
                    int toIntMonth;
                    int toIntYear;
                    toIntDay  = Integer.parseInt(day);
                    toIntMonth = Integer.parseInt(month);
                    toIntYear = Integer.parseInt(year);

                    LocalDate localDate = dateField.getValue();
                    int localYear = localDate.getYear();
                    int newNumber = 0, x;
                    while (localYear != 0){
                        x = localYear%10;
                        newNumber = newNumber*10 + x;
                        if(localYear/100 == 0) break;
                        localYear/=10;
                    }
                    if(toIntDay == localDate.getDayOfMonth() && toIntMonth == localDate.getMonth().getValue() && newNumber == toIntYear ) {
                        if(number(jmbg)> 0 && number(jmbg)< 10 && number(jmbg) == Integer.parseInt(jmbg[12])) {
                            jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                            jmbgField.getStyleClass().add("poljeIspravno");
                        }else if(number(jmbg) > 9 && number(jmbg) < 12 && number(jmbg) == Integer.parseInt(jmbg[12])){
                            jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                            jmbgField.getStyleClass().add("poljeIspravno");
                        }
                    }
                }
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
                if(findPlace(addressPlace.getSelectionModel().getSelectedItem().toString()) != null)
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

    private int number(String[] jmbg){
        int a = Integer.parseInt(jmbg[0]);
        int g = Integer.parseInt(jmbg[6]);
        int b = Integer.parseInt(jmbg[1]);
        int c = Integer.parseInt(jmbg[2]);
        int d = Integer.parseInt(jmbg[3]);
        int e = Integer.parseInt(jmbg[4]);
        int f = Integer.parseInt(jmbg[5]);
        int h = Integer.parseInt(jmbg[7]);
        int i = Integer.parseInt(jmbg[8]);
        int j = Integer.parseInt(jmbg[9]);
        int k = Integer.parseInt(jmbg[10]);
        int l = Integer.parseInt(jmbg[11]);
        int m = Integer.parseInt(jmbg[12]);

        int n = (( 7*(a+g) + 6*(b+h) + 5*(c+i) + 4*(d+j) + 3*(e+k) + 2*(f+l) ) % 11);
        n = 11 - n;
        return n;

    }

    private void addPlace(String name){
        Place place = new Place(0, name, postalNumberField.getText());
        instance.addPlace(place);
        instanceXml.addPlace(place);
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
     return place;
    }
    /*
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
            System.out.println(postalNumberField.getText());
             place = new Place(0, name, postalNumberField.getText());
            instance.addPlace(place);
        }
        return place;
    }*/
}
