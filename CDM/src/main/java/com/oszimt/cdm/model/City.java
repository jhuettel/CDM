package com.oszimt.cdm.model;

import java.util.List;

/**
 * Model for the city object.
 * @author alex.eggers
 */
public class City {

    private String id;

    private String name;

    private List<District> districts;

    /**
     * Creates a new {@code City} instance. This is used when a city is first built and has not
     * received an id from the database nor any related districts yet.
     * @param name The city's name
     */
    public City(String name) {
        this.name = name;
    }

    /**
     * Creates a new {@code City} instance. This is used when the city object is built from data in
     * the database.
     * @param cityID The city's id in the database.
     * @param name The city's name
     * @param districts The districts the city possesses
     */
    public City(String id, String name, List<District> districts) {
        this(name);
        this.districts = districts;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
