package com.oszimt.cdm.business;

import java.util.List;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

/**
 * Interface for communicating with the business layer.
 * @author alex.eggers
 */
public interface IBusiness {

    /**
     * Adds a city to the database.
     * @param name Name of the new city
     * @return True on success, false on failure
     */
    public boolean addCity(String name);

    /**
     * Adds a district to the database.
     * @param city The city that the district belongs to
     * @param name Name of the new district
     * @return True on success, false on failure
     */
    public boolean addDistrict(City city, String name);

    /**
     * Changes the name of the given city in the database.
     * @param city The city being changed
     * @return True on success, false on failure
     */
    public boolean editCity(City city);

    /**
     * Changes the name of the given district in the database.
     * @param district The district being changed
     * @return True on success, false on failure
     */
    public boolean editDistrict(District district);

    /**
     * Deletes city and all its districts from the database.
     * @param city The city being deleted
     * @return True on success, false on failure
     */
    public boolean deleteCity(City city);

    /**
     * Deletes district from database.
     * @param district The district being deleted
     * @return True on success, false on failure
     */
    public boolean deleteDistrict(District district);

    /**
     * Returns all cities in the database, sorted according to the implementation. Utilizes method
     * template pattern to serve different implementations of {@link IBusiness}.
     * @return A list of cities.
     */
    public List<City> getAllCitiesSorted();

    /**
     * Returns a list of {@link City} found by name. If none are found, an empty list should be
     * returned.
     * @param name
     * @return A list of cities with the given name.
     */
    public List<City> findCitiesByName(String name);
}
