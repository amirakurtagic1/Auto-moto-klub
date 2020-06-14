package ba.unsa.etf.rs.zadaca5;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Owner implements Serializable {
    private int id;
    private Place livingPlace, placeOfBirth;
    private String name, surname, parentName, livingAddress, jmbg;
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
}
