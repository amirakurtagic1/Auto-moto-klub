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
    private PreparedStatement getOwnersQuery, getPlaceQuery, addOwnerQuery, getMaxIdForOwnerQuery, getMaxIfForPlaceQuery, addPlaceQuery, getPlacesQuery;

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
        owner.setPlaceOfBirth(getPlace(rs.getInt(8)));
        owner.setLivingPlace(getPlace(rs.getInt(9)));
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
        return null;
    }

    @Override
    public ObservableList<Place> getPlaces() {
        return null;
    }

    @Override
    public ObservableList<Manufacturer> getManufacturers() {
        return null;
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
