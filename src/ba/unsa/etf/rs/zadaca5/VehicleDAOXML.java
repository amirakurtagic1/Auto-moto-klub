package ba.unsa.etf.rs.zadaca5;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


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
                    System.out.println(arrayOfOwners.get(i).getName() + " " + arrayOfOwners.get(i).getLivingPlace() + " " + arrayOfOwners.get(i).getDateOfBirth() );
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

    }

    @Override
    public void changeVehicle(Vehicle vehicle) {

    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {

    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {

    }


    @Override
    public void addPlace(Place place) {

    }

    @Override
    public void close() {

    }
}
