package ba.unsa.etf.rs.zadaca5;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.naming.Binding;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Owner implements Serializable {
    private int id;
    private Place livingPlace, placeOfBirth;
    private String name, surname, parentName, livingAddress, jmbg, nameAndSurname;
    private LocalDate dateOfBirth;

    public Owner(int id, String name, String surname, String parentName,LocalDate dateOfBirth, Place placeOfBirth,String livingAddress,Place livingPlace,String jmbg) {
        this.id = id;
        this.placeOfBirth = placeOfBirth;
        this.livingPlace = livingPlace;
        this.name = name;
        this.surname = surname;
        this.parentName = parentName;
        this.livingAddress = livingAddress;
        this.jmbg = jmbg;
        this.dateOfBirth = dateOfBirth;
    }

    public Owner() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Place getLivingPlace() {
        return livingPlace;
    }

    public void setLivingPlace(Place livingPlace) {
        this.livingPlace = livingPlace;
    }

    public Place getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(Place placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(String livingAddress) {
        this.livingAddress = livingAddress;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getDateOfBirthLong(LocalDate dateOfBirth){
        LocalDate date = dateOfBirth;
        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long epoch = date.atStartOfDay(zoneId).toEpochSecond();
        return epoch;
    }
    public LocalDate getDateOfBirthDays(Long dateOfBirth) {
        LocalDate date =
                Instant.ofEpochMilli(dateOfBirth).atZone(ZoneId.systemDefault()).toLocalDate();
        return  date;
    }

    public void setDateOfBirthDays(Long dateOfBirth) {
        LocalDate date =
                Instant.ofEpochMilli(dateOfBirth*100000000).atZone(ZoneId.systemDefault()).toLocalDate();
        this.dateOfBirth = date;
    }

    public void setDateOfBirthDays(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getNameAndSurname() {
        return name + " " + surname;
    }

    /*

    private int id;
    private SimpleObjectProperty<Place> livingPlace, placeOfBirth;
    private SimpleStringProperty name, surname, parentName, livingAddress, jmbg;
    private SimpleObjectProperty<LocalDate> dateOfBirth;
    private String nameAndSurname;

    public Owner() {
    }

    public Owner(int id, String name, String surname, String parentName, LocalDate dateOfBirth, Place placeOfBirth, String livingAddress, Place livingPlace, String jmbg) {
        this.id = id;
        this.placeOfBirth = new SimpleObjectProperty<Place>(placeOfBirth);
        this.livingPlace = new SimpleObjectProperty<Place>(livingPlace);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.parentName = new SimpleStringProperty(parentName);
        this.livingAddress = new SimpleStringProperty(livingAddress);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.dateOfBirth = new SimpleObjectProperty<LocalDate>(dateOfBirth);
    }

    public Owner(int id, SimpleObjectProperty<Place> livingPlace, SimpleObjectProperty<Place> placeOfBirth, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty parentName, SimpleStringProperty livingAddress, SimpleStringProperty jmbg, SimpleObjectProperty<LocalDate> dateOfBirth) {
        this.id = id;
        this.livingPlace = livingPlace;
        this.placeOfBirth = placeOfBirth;
        this.name = name;
        this.surname = surname;
        this.parentName = parentName;
        this.livingAddress = livingAddress;
        this.jmbg = jmbg;
        this.dateOfBirth = dateOfBirth;
    }

    public String getNameAndSurname() {
        return name.get() + " " + surname.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Place getLivingPlace() {
        return livingPlace.get();
    }

    public SimpleObjectProperty<Place> livingPlaceProperty() {
        return livingPlace;
    }

    public void setLivingPlace(Place livingPlace) {
        this.livingPlace.set(livingPlace);
    }

    public Place getPlaceOfBirth() {
        return placeOfBirth.get();
    }

    public SimpleObjectProperty<Place> placeOfBirthProperty() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(Place placeOfBirth) {
        this.placeOfBirth.set(placeOfBirth);
    }

    public String  getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getParentName() {
        return parentName.get();
    }

    public SimpleStringProperty parentNameProperty() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName.set(parentName);
    }

    public String getLivingAddress() {
        return livingAddress.get();
    }

    public SimpleStringProperty livingAddressProperty() {
        return livingAddress;
    }

    public void setLivingAddress(String livingAddress) {
        this.livingAddress.set(livingAddress);
    }

    public String getJmbg() {
        return jmbg.get();
    }

    public SimpleStringProperty jmbgProperty() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg.set(jmbg);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }*/

    @Override
    public String toString() {
        return surname + " " + name;
    }
}
