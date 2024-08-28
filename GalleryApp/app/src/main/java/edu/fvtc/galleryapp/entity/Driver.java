package edu.fvtc.galleryapp.entity;

public class Driver {

    private String driverName;
    private String driverBio;

    public String getDriverName() {

        return driverName;
    }

    public void setDriverName(String driverName) {

        this.driverName = driverName;
    }

    public String getDriverBio() {

        return driverBio;
    }

    public void setDriverBio(String driverBio) {

        this.driverBio = driverBio;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverName='" + driverName + '\'' +
                ", driverBio='" + driverBio + '\'' +
                '}';
    }
}
