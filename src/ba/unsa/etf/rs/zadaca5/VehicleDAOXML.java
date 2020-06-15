package ba.unsa.etf.rs.zadaca5;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


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
            System.out.println(arrayOfOwners.get(0).getDateOfBirth());
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


    }

    @Override
    public void changeOwner(Owner owner) {

    }

    @Override
    public void deleteOwner(Owner owner) {

    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        boolean yes = false;
        yes = doesOwnerExist(vehicle.getOwner());
        addManufacturerIfDoesntExist(vehicle.getManufacturer());
        if(yes == false) throw new IllegalArgumentException("Vlasnik ne postoji");
        ObservableList<Vehicle> vehicles = getVehicles();
        vehicles.add(vehicle);
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

    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        ObservableList<Vehicle> vehicles = getVehicles();
        vehicles.remove(vehicle.getId()-1);
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

    }

    @Override
    public void close() {

    }
}
