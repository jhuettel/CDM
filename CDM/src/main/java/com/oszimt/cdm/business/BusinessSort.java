package com.oszimt.cdm.business;

import java.util.List;

import com.oszimt.cdm.data.IData;
import com.oszimt.cdm.model.City;

/**
 * Business layer that will return the data sorted alphabetically.
 * @author alex.eggers
 */
public class BusinessSort extends AbstractBusiness {

    /**
     * Creates a new {@code BusinessSort} instance.
     * @param data An implementation of the {@link IData} interface
     */
    public BusinessSort(IData data) {
        this.data = data;
    }

    @Override
    public List<City> sortCities(List<City> cities) {
        cities.sort((city1, city2) -> city1.getName().compareToIgnoreCase(city2.getName()));
        cities.forEach((city) -> {
            city.getDistricts().sort((district1, district2) -> district1.getName().compareToIgnoreCase(district2.getName()));
        });
        return cities;
    }
}
