package com.laurinka.skga.app.rest;

/**
 * Holds values from webservice call.
 */

public class Hcp {
    private String name;
    private String club;
    private String hcp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getHcp() {
        return hcp;
    }

    public void setHcp(String hcp) {
        this.hcp = hcp;
    }

    @Override
    public String toString() {
        return "Hcp{" +
                "name='" + name + '\'' +
                ", club='" + club + '\'' +
                ", hcp='" + hcp + '\'' +
                '}';
    }
}
