package com.oszimt.cdm.business;

import java.util.List;

import com.oszimt.cdm.data.IData;
import com.oszimt.cdm.exception.NullObjectException;
import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

/**
 * Abstract business layer implementing most of the functionality.
 * @author alex.eggers
 */
public abstract class AbstractBusiness implements IBusiness {

    protected IData data;

    @Override
    public boolean addCity(String name) {
        return data.addCity(new City(name));
    }

    @Override
    public boolean addDistrict(City city, String name) {
        return data.addDistrict(city, new District(name));
    }

    @Override
    public boolean editCity(City city) {
        return data.editCity(city);
    }

    @Override
    public boolean editDistrict(District district) {
        return data.editDistrict(district);
    }

    @Override
    public boolean deleteCity(City city) {
        return data.deleteCity(city);
    }

    @Override
    public boolean deleteDistrict(District district) {
        return data.deleteDistrict(district);
    }

    @Override
    public List<City> findCitiesByName(String name) {
        List<City> cities = data.findCitiesByName(name);
        if (cities == null) {
            throw new NullObjectException("City list was null instead of empty.");
        }
        return cities;
    }

    @Override
    public final List<City> getAllCitiesSorted() {
        List<City> cities = data.getAllCities();
        if (cities == null) {
            throw new NullObjectException("City list was null instead of empty.");
        }
        return sortCities(cities);
    }

    protected abstract List<City> sortCities(List<City> cities);
}
