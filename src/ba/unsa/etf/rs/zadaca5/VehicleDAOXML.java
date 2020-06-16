package ba.unsa.etf.rs.zadaca5;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class VehicleDAOXML implements VehicleDAO {
    @Override
    public ObservableList<Owner> getOwners() {
        ObservableList<Owner> owners = FXCollections.observableArrayList();
        XMLDecoder d = null;
        try {
            d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream("owners.xml")));
                Object object = d.readObject();
                ArrayList<Owner> arrayOfOwners = (ArrayList<Owner>) object;
                for(int i = 0; i < arrayOfOwners.size(); i++){
                    owners.add(arrayOfOwners.get(i));
                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        d.close();

        return owners;
    }

    @Override
    public ObservableList<Vehicle> getVehicles() {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        XMLDecoder d = null;
        try {
            d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream("vehicles.xml")));
            Object object =d.readObject();
            ArrayList<Vehicle> arrayOfVehicles = (ArrayList<Vehicle>) object;
            for(int i = 0; i < arrayOfVehicles.size(); i++){
                vehicles.add(arrayOfVehicles.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        d.close();
        return vehicles;
    }

    @Override
    public ObservableList<Place> getPlaces() {
        ObservableList<Place> places = FXCollections.observableArrayList();
        XMLDecoder d = null;
        try {
            d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream("places.xml")));
            Object place =d.readObject();
            ArrayList<Place> objects = (ArrayList<Place>) place;
            for(int i = 0; i < objects.size(); i++){
                places.add(objects.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        d.close();
        return places;
    }

    @Override
    public ObservableList<Manufacturer> getManufacturers() {
        ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
        XMLDecoder d = null;
        try {
            d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream("manufacturers.xml")));
            Object object =d.readObject();
            ArrayList<Manufacturer> arrayOfManufacturer = (ArrayList<Manufacturer>) object;
            Collections.sort(arrayOfManufacturer, Comparator.comparing(Manufacturer::getName));
            for(int i = 0; i < arrayOfManufacturer.size(); i++){
                manufacturers.add(arrayOfManufacturer.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        d.close();
        return manufacturers;
    }

    @Override
    public void addOwner(Owner owner) {
        addPlaceIfDoesntExist(owner.getLivingPlace());
        addPlaceIfDoesntExist(owner.getPlaceOfBirth());
        ObservableList<Owner> owners = getOwners();
        int id = owners.size() + 1;
        owner.setId(id);
        owners.add(owner);
        addToXmlFileOwners(owners);

    }
    private void addPlaceIfDoesntExist(Place place){
        ObservableList<Place> places = getPlaces();
        for(Place x: places) {
            if (x.getPostalNumber().equals(place.getPostalNumber())) return;
        }
        addPlace(place);
    }

    @Override
    public void changeOwner(Owner owner) {
        ObservableList<Owner> owners  = getOwners();
        for(int i = 0; i < owners.size(); i++){
            if(owners.get(i).getId() == owner.getId()){
                addPlaceIfDoesntExist(owner.getPlaceOfBirth());
                addPlaceIfDoesntExist(owner.getLivingPlace());
                owners.get(i).setName(owner.getName());
                owners.get(i).setSurname(owner.getSurname());
                owners.get(i).setParentName(owner.getParentName());
                owners.get(i).setJmbg(owner.getJmbg());
                owners.get(i).setLivingPlace(owner.getLivingPlace());
                owners.get(i).setPlaceOfBirth(owner.getPlaceOfBirth());
                owners.get(i).setLivingAddress(owner.getLivingAddress());
                owners.get(i).setDateOfBirth(owner.getDateOfBirth());
            }
        }
       addToXmlFileOwners(owners);
    }

    @Override
    public void deleteOwner(Owner owner) {
        ObservableList<Owner> owners = getOwners();
        boolean yes = false;
        yes = vehicleOwner(owner);
        if(yes == true) throw new IllegalArgumentException("Nemoguce obrisati vlasnika, posjeduje vozilo.");
        else{
            owners.remove(owner.getId() - 1);
           addToXmlFileOwners(owners);
        }

    }

    private void addToXmlFileOwners(ObservableList<Owner> owners){
        ArrayList<Owner> arrayOfOwners = new ArrayList<>();
        for(int i = 0; i < owners.size();i++){
            arrayOfOwners.add(owners.get(i));
        }
        Object object = arrayOfOwners;
        XMLEncoder e = null;
        try {
            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("owners.xml")));
            e.writeObject(object);
            e.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public boolean vehicleOwner(Owner owner){
        ObservableList<Vehicle> vehicles = getVehicles();
        for(Vehicle x: vehicles){
            if(x.getOwner().getId() == owner.getId()) return true;
        }
        return false;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        boolean yes = false;
        yes = doesOwnerExist(vehicle.getOwner());
        addManufacturerIfDoesntExist(vehicle.getManufacturer());
        if(yes == false) throw new IllegalArgumentException("Vlasnik ne postoji");
        ObservableList<Vehicle> vehicles = getVehicles();
        int id = vehicles.size() + 1;
        vehicle.setId(id);
        vehicles.add(vehicle);
       addToXmlFileVehicles(vehicles);
    }

    private boolean doesOwnerExist(Owner owner){
        ObservableList<Owner> owners = getOwners();
        for(Owner x : owners){
            if(x.getJmbg().equals(owner.getJmbg())) return true;
        }
        return false;
    }

    private void addManufacturerIfDoesntExist(Manufacturer manufacturer){
        ObservableList<Manufacturer> manufacturers = getManufacturers();
        for(Manufacturer x: manufacturers) {
            if(x.getName().equals(manufacturer.getName())) return;
        }
        addManufacturer(manufacturer);
    }


    @Override
    public void changeVehicle(Vehicle vehicle) {
        ObservableList<Vehicle> vehicles = getVehicles();
        for(int i = 0; i < vehicles.size(); i++) {
            if(vehicles.get(i).getId() == vehicle.getId()){
                addManufacturerIfDoesntExist(vehicle.getManufacturer());
                vehicles.get(i).setManufacturer(vehicle.getManufacturer());
                vehicles.get(i).setModel(vehicle.getModel());
                vehicles.get(i).setPlateNumber(vehicle.getPlateNumber());
                vehicles.get(i).setChasisNumber(vehicle.getChasisNumber());
                vehicles.get(i).setOwner(vehicle.getOwner());
            }
        }
      addToXmlFileVehicles(vehicles);
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        ObservableList<Vehicle> vehicles = getVehicles();
        vehicles.remove(vehicle.getId()-1);
        addToXmlFileVehicles(vehicles);
    }

    private void addToXmlFileVehicles(ObservableList<Vehicle> vehicles){
        ArrayList<Vehicle> arrayOfVehicles = new ArrayList<>();
        for(int i = 0; i < vehicles.size();i++){
            arrayOfVehicles.add(vehicles.get(i));
        }
        Object object = arrayOfVehicles;
        XMLEncoder e = null;
        try {
            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("vehicles.xml")));
            e.writeObject(object);
            e.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        int maxId = 0;
            ObservableList<Manufacturer> manufacturers = getManufacturers();
            Collections.sort(manufacturers, Comparator.comparing(Manufacturer::getId));
        for(int i = 0; i < manufacturers.size(); i++){
                if(i == manufacturers.size() - 1) maxId = manufacturers.get(i).getId();
            }
            manufacturer.setId(maxId + 1);
            manufacturers.add(manufacturer);
            ArrayList<Manufacturer> arrayOfManufacturer = new ArrayList<>();
            for(int i = 0; i < manufacturers.size();i++) {
                arrayOfManufacturer.add(manufacturers.get(i));
            }
        Object object = arrayOfManufacturer;
        XMLEncoder e = null;
        try {
            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("manufacturers.xml")));
            e.writeObject(object);
            e.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }


    @Override
    public void addPlace(Place place) {
        int maxId = 0;
        ObservableList<Place> places = getPlaces();
        for(int i = 0; i < places.size();i++){
            if(i == places.size() - 1) maxId = i+1;
        }
        place.setId(maxId+1);
        places.add(place);
        ArrayList<Place> arrayOfPlace = new ArrayList<>();
        for(int i = 0; i < places.size();i++){
            arrayOfPlace.add(places.get(i));
        }
        Object object = arrayOfPlace;
        XMLEncoder e = null;
        try {
            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("places.xml")));
            e.writeObject(object);
            e.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


    }

    @Override
    public void close() {

    }

    @Override
    public void searchOwners(String value) {

    }

    @Override
    public void searchVehicles(String value) {

    }
}
