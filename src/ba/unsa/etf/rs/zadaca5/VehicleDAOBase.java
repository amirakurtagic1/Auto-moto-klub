package ba.unsa.etf.rs.zadaca5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class VehicleDAOBase implements VehicleDAO{
    private static VehicleDAO instance = null;
    private Connection connection;
    private ObservableList<Owner> owners = FXCollections.observableArrayList();
    private PreparedStatement getOwnersQuery, getPlaceQuery, addOwnerQuery, getMaxIdForOwnerQuery, getMaxIfForPlaceQuery, addPlaceQuery, getPlacesQuery,
                              changeOwnerQuery, deleteVehicleQuery, getManufacturersQuery, getVehiclesQuery, getManufacturerQuery, getOwnerQuery;

    public VehicleDAOBase(){

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:vehicles.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try{
            getOwnersQuery = connection.prepareStatement("select * from owner");
            getOwnersQuery.executeQuery();
        } catch (SQLException throwables) {
            resetDatabase();
        }
        try{
            getOwnersQuery = connection.prepareStatement("select * from owner");
            getPlaceQuery = connection.prepareStatement("select * from place where id=?");
            addOwnerQuery = connection.prepareStatement("insert into owner(id, name, surname, parent_name,date_of_birth, place_of_birth, living_address, living_place, jmbg) values(?,?,?,?,?,?,?,?,?)");
            getMaxIdForOwnerQuery = connection.prepareStatement("select MAX(id) from owner");
            getMaxIfForPlaceQuery = connection.prepareStatement("select MAX(id) from place");
            addPlaceQuery = connection.prepareStatement("insert into place(id, name, postal_number) values(?,?,?)");
            getPlacesQuery = connection.prepareStatement("select * from place order by name");
            changeOwnerQuery = connection.prepareStatement("update owner set name = ?, surname=?,parent_name=?, date_of_birth=?, place_of_birth=?, living_address=?, living_place=?, jmbg=? where id=?");
            deleteVehicleQuery = connection.prepareStatement("delete from vehicle where id=?");
            getManufacturersQuery = connection.prepareStatement("select * from manufacturer ORDER BY name ASC");
            getVehiclesQuery = connection.prepareStatement("select * from vehicle");
            getManufacturerQuery = connection.prepareStatement("select * from manufacturer where id=?");
            getOwnerQuery = connection.prepareStatement("select * from owner where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static VehicleDAO getInstance(){
        if(instance == null) instance = new VehicleDAOBase();
        return instance;
    }


    public void resetDatabase() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("books.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka… nastavljam sa praznom bazom");
        }

    }


    @Override
    public ObservableList<Owner> getOwners() {
        owners.clear();
        try{
            ResultSet rs = getOwnersQuery.executeQuery();
            while(rs.next()){
                Owner owner = getOwnerFromResultSet(rs);
                owners.add(owner);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return owners;
    }

    private Owner getOwnerFromResultSet(ResultSet rs) throws SQLException {
        Owner owner = new Owner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), LocalDate.parse(rs.getDate(5).toString()), null, rs.getString(7), null, rs.getString(9));
        owner.setPlaceOfBirth(getPlace(rs.getInt(6)));
        owner.setLivingPlace(getPlace(rs.getInt(8)));
        return owner;
    }
    private Place getPlace(int id) {
        try {
            getPlaceQuery.setInt(1, id);
            ResultSet rs = getPlaceQuery.executeQuery();
            return placeFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    private Place placeFromResultSet(ResultSet rs) throws SQLException {
        return new Place(rs.getInt(1), rs.getString(2), rs.getString(3));

    }
    
    @Override
    public ObservableList<Vehicle> getVehicles() {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        try{
            ResultSet rs = getVehiclesQuery.executeQuery();
            while(rs.next()){
                Vehicle vehicle = getVehicleFromResultSet(rs);
                vehicles.add(vehicle);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vehicles;
    }

    private Vehicle getVehicleFromResultSet(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle(rs.getInt(1), null, rs.getString(3), rs.getString(4), rs.getString(5), null);
        vehicle.setManufacturer(getManufacturer(rs.getInt(2)));
        vehicle.setOwner(getOwner(rs.getInt(6)));
        return vehicle;
    }

    private Manufacturer getManufacturer(int id){
        try {
            getManufacturerQuery.setInt(1, id);
            ResultSet rs = getManufacturerQuery.executeQuery();
            if(rs.next()) {
                return getManufacturerFromResultSet(rs);
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    private Owner getOwner(int id){
        try {
            getOwnerQuery.setInt(1, id);
            ResultSet rs = getOwnerQuery.executeQuery();
            if(rs.next()) {
                return getOwnerFromResultSet(rs);
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ObservableList<Place> getPlaces() {
        ObservableList<Place> places = FXCollections.observableArrayList();
        try{
            ResultSet rs = getPlacesQuery.executeQuery();
            while(rs.next()){
                Place place = getPlaceFromResultSet(rs);
                places.add(place);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return places;
    }
    private Place getPlaceFromResultSet(ResultSet rs) throws SQLException {
        return new Place(rs.getInt(1), rs.getString(2), rs.getString(3));
    }

    @Override
    public ObservableList<Manufacturer> getManufacturers() {
        ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
        try{
            ResultSet rs = getManufacturersQuery.executeQuery();
            while(rs.next()){
                Manufacturer manufacturer = getManufacturerFromResultSet(rs);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return manufacturers;
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet rs) throws SQLException {
        return new Manufacturer(rs.getInt(1), rs.getString(2));
    }
    @Override
    public void addOwner(Owner owner) {
        Place livingPlace = owner.getLivingPlace();
        Place placeOfBirth = owner.getPlaceOfBirth();
        ObservableList<Place> places = getPlaces();
        boolean ifExists = false;
        for(int i = 0; i < places.size(); i++){
            if(livingPlace.equals(places.get(i))) ifExists = true;
        }
        if(ifExists == false) addPlace(livingPlace);
        ifExists = false;
        for(int i = 0; i < places.size(); i++){
            if(placeOfBirth.equals(places.get(i))) ifExists = true;
        }
        if(ifExists == false) addPlace(livingPlace);

        try{
            int id = getMaxIdForOwner();
            addOwnerQuery.setInt(1, id);
            addOwnerQuery.setString(2, owner.getName());
            addOwnerQuery.setString(3, owner.getSurname());
            addOwnerQuery.setString(4, owner.getParentName());
            addOwnerQuery.setDate(5, Date.valueOf(owner.getDateOfBirth()));
            addOwnerQuery.setInt(6, owner.getPlaceOfBirth().getId());
            addOwnerQuery.setString(7, owner.getLivingAddress());
            addOwnerQuery.setInt(8, owner.getLivingPlace().getId());
            addOwnerQuery.setString(9, owner.getJmbg());
            addOwnerQuery.executeUpdate();
            owner.setId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getMaxIdForOwner(){
        int id = 1;
        try {
            ResultSet rs = getMaxIdForOwnerQuery.executeQuery();
            if(rs.next()) {
                id = rs.getInt(1) + 1;
                return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public void addPlace(Place place){
        try{
            int id = getMaxIdForPlace();
            addPlaceQuery.setInt(1,id);
            addPlaceQuery.setString(2, place.getName());
            addPlaceQuery.setString(3, place.getPostalNumber());
            addPlaceQuery.executeUpdate();
            place.setId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private int getMaxIdForPlace(){
        int id = 1;
        try {
            ResultSet rs = getMaxIfForPlaceQuery.executeQuery();
            if(rs.next()) {
                id = rs.getInt(1) + 1;
                return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }


    @Override
    public void changeOwner(Owner owner) {
        try{
            changeOwnerQuery.setString(1, owner.getName());
            changeOwnerQuery.setString(2, owner.getSurname());
            changeOwnerQuery.setString(3, owner.getParentName());
            changeOwnerQuery.setDate(4, Date.valueOf(owner.getDateOfBirth()));
            changeOwnerQuery.setInt(5, owner.getPlaceOfBirth().getId());
            changeOwnerQuery.setString(6, owner.getLivingAddress());
            changeOwnerQuery.setInt(7, owner.getLivingPlace().getId());
            changeOwnerQuery.setString(8, owner.getJmbg());
            changeOwnerQuery.setInt(9, owner.getId());
            changeOwnerQuery.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
        try{
            deleteVehicleQuery.setInt(1, vehicle.getId());
            deleteVehicleQuery.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void close() {
        if(instance == null) return;
        try{
            instance.close();
        }catch (Exception e){
            System.out.println("Nije uspjelo zatvaranje konekcije");
        }
        instance = null;
    }
}
