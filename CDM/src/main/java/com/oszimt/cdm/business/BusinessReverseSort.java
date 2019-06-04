package com.oszimt.cdm.business;

import java.util.List;

import com.oszimt.cdm.data.IData;
import com.oszimt.cdm.model.City;

/**
 * Business layer that will return the data in reverse alphabetical order.
 * @author alex.eggers
 */
public class BusinessReverseSort extends AbstractBusiness {

    /**
     * Creates a new {@code BusinessReverseSort} instance.
     * @param data An implementation of the {@link IData} interface
     */
    public BusinessReverseSort(IData data) {
        this.data = data;
    }

    @Override
    public List<City> sortCities(List<City> cities) {
        cities.sort((city1, city2) -> city2.getName().compareToIgnoreCase(city1.getName()));
        cities.forEach((city) -> {
            city.getDistricts().sort((district1, district2) -> district2.getName().compareToIgnoreCase(district1.getName()));
        });
        return cities;
    }
}
