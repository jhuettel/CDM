package com.oszimt.cdm.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.oszimt.cdm.business.IBusiness;
import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;
import com.oszimt.cdm.model.IInputObserver;
import com.oszimt.cdm.model.IObservableValue;
import com.oszimt.cdm.model.ITableObserver;

/**
 * @author JH
 * @version 1.0
 * @since 28.11.2017
 */
public class DataService implements IObservableValue {
    // Today's Menu: Spaghetti, freshly imported from Java
    public static final String CITY = "City";

    public static final String DISTRICT = "District";

    public static final String ENTRY = "Entry";

    public static final String ADD = "Add";

    public static final String REMOVE = "Remove";

    public static final String EDIT = "Edit";

    public static final String SHOW = "Show";

    public static final String ARIAL = "Arial";

    public static final String COURIER = "Courier New";

    private static DataService instance;

    public IBusiness business;

    private ArrayList<ITableObserver> tableObserver;

    private HashMap<String, IInputObserver> showCityObserver;

    private HashMap<String, IInputObserver> addCityObserver;

    private HashMap<String, IInputObserver> addDistrictObservers;

    private HashMap<String, IInputObserver> editEntryObservers;

    private HashMap<String, IInputObserver> newEntryObservers;

    private HashMap<String, IInputObserver> removeEntryObservers;

    /**
     * Creates a new instance of {@code DataService} if there isn't already one.
     *
     * @version 1.0
     * @since 28.11.2017
     * @return an instance of {@code DataService}.
     */
    protected static DataService getInstance() {
        if (DataService.instance == null) {
            DataService.instance = new DataService();
        }
        return instance;
    }

    /**
     * @since 28.11.2017
     * @param business
     */
    protected void setBusiness(IBusiness business) {
        this.business = business;
    }

    /**
     * @since 28.11.2017
     * @see com.oszimt.cdm.model.IValue#setValue(java.lang.String)
     */
    @Override
    public void setValue(String value) {
        // as only buttons call this method, value = e.getActionCommand(operation + obj)
        switch (value) {
            case SHOW + CITY: {
                showCity(showCityObserver.get(CITY).getUserInput());
                break;
            }
            case ADD + CITY: {
                addCity(addCityObserver.get(CITY).getUserInput());
                break;
            }
            case ADD + DISTRICT: {
                addDistrict(getCity(addDistrictObservers), addDistrictObservers.get(DISTRICT).getUserInput());
                break;
            }
            case EDIT + ENTRY: {
                editEntry(getCity(editEntryObservers), editEntryObservers.get(DISTRICT).getUserInput(), newEntryObservers.get(CITY).getUserInput(), newEntryObservers.get(DISTRICT).getUserInput());
                break;
            }
            case REMOVE + ENTRY: {
                removeEntry(getCity(removeEntryObservers), removeEntryObservers.get(DISTRICT).getUserInput());
                break;
            }
            default: {
                // error
            }
        }
        notifyAllObserver();
    }

    /**
     * Calls upon each observers update-method, which refreshes its values and removes focus.
     * 
     * @since 11.12.2017
     */
    private void notifyAllObserver() {
        // textfield
        addCityObserver.get(CITY).update();
        addDistrictObservers.get(DISTRICT).update();

        // combobox
        showCityObserver.get(CITY).update();
        addDistrictObservers.get(CITY).update();
        editEntryObservers.get(CITY).update();
        newEntryObservers.get(CITY).update();
        removeEntryObservers.get(CITY).update();

        // secondary
        // editEntryObservers.get(DISTRICT).update(city);
        // removeEntryObservers.get(DISTRICT).update(city);
    }

    /**
     * Notify District Comboboxes to update their Values for given City.
     * 
     * @since 11.12.2017
     * @param cityValue
     * @param observer
     */
    protected void setCityValue(String cityValue, IInputObserver observer) {
        if (observer == editEntryObservers.get(CITY)) {
            editEntryObservers.get(DISTRICT).update(convertToCity(cityValue));
        }
        if (observer == removeEntryObservers.get(CITY)) {
            removeEntryObservers.get(DISTRICT).update(convertToCity(cityValue));
        }
    }

    /**
     * Gets the current value of given {@code IObserver} as Text and converts it to {@code City}.
     * 
     * @since 30.11.2017
     * @param observerList
     * @return {@code City}
     */
    private City getCity(HashMap<String, IInputObserver> observerList) {
        City city;
        String cityValue = observerList.get(CITY).getUserInput();
        if (!cityValue.equals(CITY)) {
            city = convertToCity(cityValue);
        } else {
            city = new City("");
        }
        return city;
    }

    /**
     * Creates a new {@code DataService} instance.
     * 
     * @since 28.11.2017
     */
    private DataService() {
        tableObserver = new ArrayList<ITableObserver>();
        showCityObserver = new HashMap<String, IInputObserver>();
        addCityObserver = new HashMap<String, IInputObserver>();
        addDistrictObservers = new HashMap<String, IInputObserver>();
        editEntryObservers = new HashMap<String, IInputObserver>();
        newEntryObservers = new HashMap<String, IInputObserver>();
        removeEntryObservers = new HashMap<String, IInputObserver>();
    }

    // IMPLEMENTED OBSERVER METHODS
    /**
     * Adds a given Object to the list of {@code ITableObserver}s.
     * 
     * @since 28.11.2017
     * @see com.oszimt.cdm.model.IObservableValue#addObserver(com.oszimt.cdm.model.ITableObserver)
     */
    @Override
    public void addObserver(ITableObserver observer) {
        if (observer instanceof TablePanel) {
            tableObserver.add(observer);
        }
    }

    /**
     * @since 30.11.2017
     * @param observer
     * @param name
     */
    protected void addObserverByName(IInputObserver observer, String name, String operation) {
        if (observer instanceof MyTextField && name.equals(CITY)) {
            switch (operation) {
                case ADD: {
                    addCityObserver.put(CITY, observer);
                }
                case EDIT: {
                    newEntryObservers.put(CITY, observer);
                }
                default: {

                }
            }
        }
        if (observer instanceof MyTextField && name.equals(DISTRICT)) {
            switch (operation) {
                case ADD: {
                    addDistrictObservers.put(DISTRICT, observer);
                }
                case EDIT: {
                    newEntryObservers.put(DISTRICT, observer);
                }
                default: {

                }
            }
        }
        if (observer instanceof MyComboBox && name.equals(CITY)) {
            switch (operation) {
                case SHOW: {
                    showCityObserver.put(CITY, observer);
                    break;
                }
                case ADD: {
                    addDistrictObservers.put(CITY, observer);
                    break;
                }
                case EDIT: {
                    editEntryObservers.put(CITY, observer);
                    break;
                }
                case REMOVE: {
                    removeEntryObservers.put(CITY, observer);
                    break;
                }
                default: {
                    // dev fail
                }
            }
        }
        if (observer instanceof MyComboBox && name.equals(DISTRICT)) {
            switch (operation) {
                case EDIT: {
                    editEntryObservers.put(DISTRICT, observer);
                    break;
                }
                case REMOVE: {
                    removeEntryObservers.put(DISTRICT, observer);
                    break;
                }
                default: {
                    // dev fail
                }
            }
        }
    }

    /**
     * @since 28.11.2017
     * @see com.oszimt.cdm.model.IObservableValue#notifyObserver(com.oszimt.cdm.model.ITableObserver)
     */
    @Override
    public void notifyObserver(ArrayList<ITableObserver> observerList) {
        for (ITableObserver observer : observerList) {
            observer.update();
        }
    }

    // BUSINESS METHODS
    /**
     * Adds a City to the database and notifies the {@code CityDistrictTable} to update.
     * 
     * @since 28.11.2017
     * @param name
     * @return
     */
    private boolean addCity(String name) {
        boolean success = false;
        if (business.addCity(name)) {
            notifyObserver(tableObserver);
            success = true;
        }
        return success;
    }

    /**
     * 
     * @since 28.11.2017
     * @param city
     * @param name
     * @return
     */
    private void addDistrict(City city, String name) {
        business.addDistrict(city, name);
        notifyObserver(tableObserver);
    }

    /**
     * 
     * @since 28.11.2017
     * @param cityStr
     * @param districtStr
     * @return
     */
    private boolean editEntry(City city, String districtStr, String newCity, String newDistrict) {
        District district = convertToDistrict(city, districtStr);
        if (!newCity.equals("") && !newCity.equals(CITY)) {
            city.setName(newCity);
            business.editCity(city);
        }
        if (!newDistrict.equals("") && !newDistrict.equals(DISTRICT)) {
            district.setName(newDistrict);
            business.editDistrict(district);
        }
        tableObserver.get(0).update();
        return true;
    }

    /**
     * 
     * @since 28.11.2017
     * @param city
     * @param district
     * @return
     */
    private boolean removeEntry(City city, String districtStr) {
        boolean success = true;
        try {
            // if no district is specified
            if (districtStr.equals(DISTRICT)) {
                business.deleteCity(city);
            } else {
                if (business.deleteDistrict(convertToDistrict(city, districtStr))) {
                    notifyObserver(tableObserver);
                }
            }
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    /**
     * 
     * @since 28.11.2017
     * @return
     */
    protected List<City> getAllCities() {
        return business.getAllCitiesSorted();
    }

    /**
     * 
     * @since 28.11.2017
     * @param name
     * @return
     */
    private List<City> showCity(String name) {
        List<City> cityList = new ArrayList<City>();
        if (name.equals(CITY)) {
            cityList = getAllCities();
        } else {
            cityList = business.findCitiesByName(name);
        }
        tableObserver.get(0).update(cityList);
        return cityList;
    }

    // CONVERTIONS
    /**
     * 
     * @since 29.11.2017
     * @param cityToBe
     * @return
     */
    private City convertToCity(String cityToBe) {
        // assuming every city.name is unique
        City convertedCity = new City("");
        for (City city : getAllCities()) {
            if (cityToBe.equals(city.getName())) {
                convertedCity = city;
                break;
            }
        }
        return convertedCity;
    }

    /**
     * 
     * @since 29.11.2017
     * @param city
     * @param districtToBe
     * @return
     */
    private District convertToDistrict(City city, String districtToBe) {
        District convertedDistrict = new District("");
        try {
            for (District district : city.getDistricts()) {
                if (district.getName().equals(districtToBe)) {
                    convertedDistrict = district;
                }
            }
        } catch (Exception e) {
            // System.out.println("No matching district was found.");
        }
        return convertedDistrict;
    }
}
