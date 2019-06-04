package com.oszimt.cdm.model;

/**
 * Model for the district object.
 * @author alex.eggers
 */
public class District {

    private String id;

    private String name;

    /**
     * Creates a new {@code District} instance. This is used when a new district is created and it
     * has not received an id from the database yet.
     * @param name The district's name
     */
    public District(String name) {
        this.name = name;
    }

    /**
     * Creates a new {@code District} instance. This is used when the district object is built from
     * data in the database.
     * @param districtID The district's id
     * @param name The district's name
     */
    public District(String id, String name) {
        this(name);
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
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
