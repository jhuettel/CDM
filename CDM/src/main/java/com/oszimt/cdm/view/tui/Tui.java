package com.oszimt.cdm.view.tui;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.oszimt.cdm.business.IBusiness;
import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

/**
 * Console-based UI
 * @author alex.eggers
 */
public class Tui {

    private IBusiness business;

    private Scanner scanner;

    private final String DIVIDER = "----------------------------------------------";

    private final String SMALL_DIVIDER = "-----------------------";

    private final String ENTER_TO_CONTINUE = "Press Enter to continue";

    private final String DB_ERROR_MSG = "An error has occured. Please contact the database administrator.";

    public Tui(IBusiness business) {
        this.business = business;
        this.scanner = new Scanner(System.in);
        displayUi();
    }

    private void displayUi() {
        boolean repeat = true;
        while (repeat) {
            repeat = showMenu();
        }
        System.out.println("Goodbye!");
    }

    private boolean showMenu() {
        System.out.println(DIVIDER);
        System.out.println("Main Menu - Enter the number of the action you wish to take:");
        System.out.println("1. Show all cities");
        System.out.println("2. Find cities by name");
        System.out.println("3. Add a new city");
        System.out.println("4. Add a new district");
        System.out.println("5. Edit a city");
        System.out.println("6. Edit a district");
        System.out.println("7. Delete a city");
        System.out.println("8. Delete a district");
        System.out.println("0. Exit program");
        System.out.println("");

        // Initialized to avoid compiler errors
        int userSelection = getNrInput(0, MenuSelection.values().length);

        switch (MenuSelection.valueOf(userSelection)) {
            case SHOW_ALL_CITIES:
                showAllCities();
                break;
            case FIND_CITIES_BY_NAME:
                findAndShowCitiesByName();
                break;
            case ADD_CITY:
                addCity();
                break;
            case ADD_DISTRICT:
                addDistrict();
                break;
            case EDIT_CITY:
                editCity();
                break;
            case EDIT_DISTRICT:
                editDistrict();
                break;
            case DELETE_CITY:
                deleteCity();
                break;
            case DELETE_DISTRICT:
                deleteDistrict();
                break;
            case EXIT:
                return false;
            default:
                System.out.println("Invalid input, please try again.");
                enterToContinue();
                break;
        }
        return true;
    }

    private void showAllCities() {
        System.out.println(DIVIDER);
        System.out.println("List of all cities:");
        System.out.println("");
        showCityAndDistrictList(business.getAllCitiesSorted());
        enterToContinue();
    }

    private void findAndShowCitiesByName() {
        System.out.println(DIVIDER);
        System.out.println("Please enter the name of the city you wish to look up: ");
        String cityName = scanner.nextLine();
        System.out.println("Searching...");
        System.out.println("");

        List<City> cities = business.findCitiesByName(cityName);
        if (cities.isEmpty()) {
            System.out.println("No cities of that name found. Make sure to type the entire name of the city.");
            enterToContinue();
            return;
        }
        System.out.println("List of found cities:");
        showCityAndDistrictList(cities);
        enterToContinue();
    }

    private void showCityAndDistrictList(List<City> cities) {
        for (City city : cities) {
            System.out.println(SMALL_DIVIDER);
            System.out.println("City:");
            System.out.println("[" + cities.indexOf(city) + "]: " + city.getName());
            System.out.println("");
            System.out.println("Districts:");

            List<District> districts = city.getDistricts();
            if (districts.isEmpty()) {
                System.out.println("No districts found");
                continue;
            }
            for (District district : districts) {
                System.out.println("[" + districts.indexOf(district) + "]: " + district.getName());
            }
        }
    }

    private void showCityList(List<City> cities) {
        for (City city : cities) {
            System.out.println("[" + cities.indexOf(city) + "]: " + city.getName());
        }
    }

    private void showDistrictList(List<District> districts) {
        for (District district : districts) {
            System.out.println("[" + districts.indexOf(district) + "]: " + district.getName());
        }
    }

    private void addCity() {
        System.out.println("Please enter the name of the city you want to add:");
        String newCityName = scanner.nextLine();
        System.out.println();
        if (business.addCity(newCityName)) {
            System.out.println("Successfully added " + newCityName);
        } else {
            System.out.println(DB_ERROR_MSG);
        }
        enterToContinue();
    }

    private int getNrInput(int lowerLimit, int upperLimit) {
        int nr = 0;
        while (true) {
            try {
                nr = Integer.parseInt(scanner.nextLine());
                if (nr >= lowerLimit && nr < upperLimit) {
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println();
                System.out.println("Please only enter a number.");
                System.out.println();
            }
        }
        return nr;
    }

    private void addDistrict() {
        System.out.println("Please enter the number of the city you wish to add a district to:");
        List<City> currentCities = business.getAllCitiesSorted();
        showCityList(currentCities);
        int cityNr = getNrInput(0, currentCities.size());

        System.out.println();
        System.out.println("Please enter the name of the district you wish to add:");
        String newDistrictName = scanner.nextLine();

        if (business.addDistrict(currentCities.get(cityNr), newDistrictName)) {
            System.out.println("Successfully added " + newDistrictName);
        } else {
            System.out.println(DB_ERROR_MSG);
        }
    }

    private void editCity() {
        System.out.println("Please enter the number of the city you wish to edit:");
        List<City> currentCities = business.getAllCitiesSorted();
        showCityList(currentCities);
        int cityNr = getNrInput(0, currentCities.size());
        City cityToBeEdited = currentCities.get(cityNr);

        System.out.println();
        System.out.println("Please enter the new name for the city:");
        String newCityName = scanner.nextLine();
        String oldCityName = cityToBeEdited.getName();
        cityToBeEdited.setName(newCityName);

        if (business.editCity(cityToBeEdited)) {
            System.out.println("Successfully changed " + oldCityName + " to " + newCityName);
        } else {
            System.out.println(DB_ERROR_MSG);
        }
    }

    private void editDistrict() {
        System.out.println("Please enter the number of the city whose district you wish to edit:");
        List<City> currentCities = business.getAllCitiesSorted();
        showCityList(currentCities);
        int cityIndex = getNrInput(0, currentCities.size());
        City cityToBeEdited = currentCities.get(cityIndex);
        List<District> currentDistricts = currentCities.get(cityIndex).getDistricts();
        System.out.println("Please enter the number of the district you wish to edit:");
        showDistrictList(currentDistricts);
        int districtIndex = getNrInput(0, currentDistricts.size());
        District districtToBeEdited = cityToBeEdited.getDistricts().get(districtIndex);
        System.out.println("Please enter the new name for the district:");
        String oldDistrictName = districtToBeEdited.getName();
        String newDistrictName = scanner.nextLine();
        districtToBeEdited.setName(newDistrictName);

        if (business.editDistrict(districtToBeEdited)) {
            System.out.println("Successfully changed " + oldDistrictName + " to " + newDistrictName);
        } else {
            System.out.println(DB_ERROR_MSG);
        }
    }

    private void deleteCity() {
        System.out.println("Please enter the number of the city you wish to delete:");
        List<City> currentCities = business.getAllCitiesSorted();
        showCityList(currentCities);
        int cityIndex = getNrInput(0, currentCities.size());
        City cityToBeDeleted = currentCities.get(cityIndex);

        System.out.println("Chosen city:");
        System.out.println(cityToBeDeleted.getName());
        System.out.println("Related districts:");
        showDistrictList(cityToBeDeleted.getDistricts());

        System.out.println();

        while (true) {
            System.out.println("Are you sure you want to proceed? (y/n)");
            String choice = scanner.nextLine();
            if (choice.equals("n") || choice.equals("no")) {
                System.out.println("Aborting");
                return;
            } else if (choice.equals("y") || choice.equals("yes")) {
                break;
            } else {
                System.out.println("Please enter either 'y/yes' or 'n/no'.");
            }
        }

        if (business.deleteCity(cityToBeDeleted)) {
            System.out.println("Successfully deleted " + cityToBeDeleted.getName());
        } else {
            System.out.println(DB_ERROR_MSG);
        }
    }

    private void deleteDistrict() {
        System.out.println("Please enter the number of the city whose district you wish to delete:");
        List<City> currentCities = business.getAllCitiesSorted();
        showCityList(currentCities);
        int cityIndex = getNrInput(0, currentCities.size());
        City city = currentCities.get(cityIndex);

        System.out.println("Select the district that you wish to delete:");
        List<District> currentDistricts = city.getDistricts();
        showDistrictList(currentDistricts);
        int districtIndex = getNrInput(0, currentDistricts.size());
        District districtToBeDeleted = currentDistricts.get(districtIndex);

        System.out.println("Chosen district:");
        System.out.println(districtToBeDeleted.getName() + " in city " + city.getName());
        System.out.println();

        while (true) {
            System.out.println("Are you sure you want to proceed? (y/n)");
            String choice = scanner.nextLine();
            if (choice.equals("n") || choice.equals("no")) {
                System.out.println("Aborting");
                return;
            } else if (choice.equals("y") || choice.equals("yes")) {
                break;
            } else {
                System.out.println("Please enter either 'y/yes' or 'n/no'.");
            }
        }

        if (business.deleteDistrict(districtToBeDeleted)) {
            System.out.println("Successfully deleted " + districtToBeDeleted.getName());
        } else {
            System.out.println(DB_ERROR_MSG);
        }
    }

    /**
     * Requests the user press enter to continue using the program, used after a warning was issued.
     */
    private void enterToContinue() {
        System.out.println("");
        System.out.println(ENTER_TO_CONTINUE);
        System.out.println("");
        scanner.nextLine();
    }

    private enum MenuSelection {
        SHOW_ALL_CITIES(1),
        FIND_CITIES_BY_NAME(2),
        ADD_CITY(3),
        ADD_DISTRICT(4),
        EDIT_CITY(5),
        EDIT_DISTRICT(6),
        DELETE_CITY(7),
        DELETE_DISTRICT(8),
        EXIT(0);

        private int value;

        private static HashMap<Integer, MenuSelection> map = new HashMap<>();

        private MenuSelection(int value) {
            this.value = value;
        }

        static {
            for (MenuSelection menuSelection : MenuSelection.values()) {
                map.put(menuSelection.value, menuSelection);
            }
        }

        public static MenuSelection valueOf(int menuSelection) {
            return map.get(menuSelection);
        }
    }
}
