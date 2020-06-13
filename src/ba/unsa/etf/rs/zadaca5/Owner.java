package ba.unsa.etf.rs.zadaca5;

import java.util.Date;

public class Owner {
    private int id, placeOfBirth, livingPlace;
    private String name, surname, parentName, livingAddress, jmbg;
    private Date dateOfBirth;

    public Owner(int id, String name, String surname, String parentName,Date dateOfBirth, int placeOfBirth,String livingAddress,int livingPlace,String jmbg) {
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

    public int getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(int placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public int getLivingPlace() {
        return livingPlace;
    }

    public void setLivingPlace(int livingPlace) {
        this.livingPlace = livingPlace;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
