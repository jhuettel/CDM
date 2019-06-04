package com.oszimt.cdm.data;

import java.util.List;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

public interface IData {

    /**
     * Adds a city to the database.
     * @param city City object that will be added
     * @return True on success, false on failure
     */
    public boolean addCity(City city);

    /**
     * Adds a district to the database.
     * @param city City object that the district belongs to
     * @param district District object that will be added
     * @return True on success, false on failure
     */
    public boolean addDistrict(City city, District district);

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
     * Returns all cities in the database.
     * @return A list of cities.
     */
    public List<City> getAllCities();

    /**
     * Returns a list of {@link City} found by name. If none are found, an empty list should be
     * returned.
     * @param name
     * @return A list of cities with the given name
     */
    public List<City> findCitiesByName(String name);
}
