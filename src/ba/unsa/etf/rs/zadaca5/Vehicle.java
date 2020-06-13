package ba.unsa.etf.rs.zadaca5;

public class Vehicle {
    private int id;
    private Owner owner;
    private Manufacturer manufacturer;
    private String model, chasisNumber, plateNumber;

    public Vehicle(int id, Manufacturer manufacturer, String model, String chasisNumber, String plateNumber, Owner owner) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.chasisNumber = chasisNumber;
        this.plateNumber = plateNumber;
        this.owner = owner;
    }

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChasisNumber() {
        return chasisNumber;
    }

    public void setChasisNumber(String chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
