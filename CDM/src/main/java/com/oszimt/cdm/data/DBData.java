package com.oszimt.cdm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

public class DBData implements IData {

    private final String HOST = "localhost";

    private final String DATABASE = "cdm";

    private final String DB_USER = "root";

    private final String DB_PASS = "";

    private final String CITIES = "cities";

    private final String DISTRICTS = "districts";

    private Connection connection;

    public DBData() {
        connection = connectToMysql(HOST, DATABASE, DB_USER, DB_PASS);
    }

    @Override
    public boolean addCity(City city) {
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO cities (name) VALUES ('");
            builder.append(city.getName());
            builder.append("')");
            stmt.execute(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addDistrict(City city, District district) {
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO districts (name, cityID) VALUES ('");
            builder.append(district.getName());
            builder.append("', ");
            builder.append(city.getID());
            builder.append(")");
            stmt.execute(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean editCity(City city) {
        return editInTable(CITIES, city.getName(), city.getID());
    }

    @Override
    public boolean editDistrict(District district) {
        return editInTable(DISTRICTS, district.getName(), district.getID());
    }

    @Override
    public boolean deleteCity(City city) {
        return deleteFromTable(CITIES, city.getID());
    }

    @Override
    public boolean deleteDistrict(District district) {
        return deleteFromTable(DISTRICTS, district.getID());
    }

    @Override
    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM cities");
            ResultSet results = stmt.executeQuery(builder.toString());
            cities = handleCityResults(results);
        } catch (SQLException sqle) {
            throw new RuntimeException("An error with the database has occurred. Please contact the authors");
        }
        return cities;
    }

    @Override
    public List<City> findCitiesByName(String name) {
        List<City> cities = new ArrayList<>();
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM cities WHERE name = '");
            builder.append(name);
            builder.append("'");
            ResultSet results = stmt.executeQuery(builder.toString());
            cities = handleCityResults(results);
        } catch (SQLException sqle) {
            throw new RuntimeException("An error with the database has occurred. Please contact the authors");
        }
        return cities;
    }

    /**
     * Builds a {@link List} of {@link City} from a {@link ResultSet}
     * @param results The result set obtained by querying the database
     * @return A list of cities
     */
    private List<City> handleCityResults(ResultSet results) {
        List<City> cities = new ArrayList<>();
        try {
            while (results.next()) {
                int id = results.getInt("id");
                List<District> districts = findDistrictsByCityId(id);
                String foundName = results.getString("name");
                City city = new City(Integer.toString(id), foundName, districts);
                cities.add(city);
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error with the database has occurred. Please contact the authors");
        }
        return cities;
    }

    /**
     * Checks if the correct driver is present in the classpath
     */
    private void testForDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
    }

    /**
     * Checks if the connection to the given database is successful
     * @return connection object
     */
    private Connection testConnection(String url, String usr, String pwd) {
        try {
            Connection connection = DriverManager.getConnection(url, usr, pwd);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Can't connect to the database!", e);
        }
    }

    /**
     * connects to a database
     * @param host
     * @param database
     * @param user
     * @param passwd
     * @return connection object
     */
    private Connection connectToMysql(String host, String database, String user, String passwd) {
        String url = "jdbc:mysql://" + host + "/" + database;
        Connection connection;
        testForDriver();
        connection = testConnection(url, user, passwd);
        return connection;
    }

    /**
     * Finds all districts in the city with the given id
     * @param cityID The given city id
     * @return A {@link List} of districts
     */
    private List<District> findDistrictsByCityId(int cityID) {
        List<District> districts = new ArrayList<>();
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM districts WHERE cityID = ");
            builder.append(cityID);
            ResultSet results = stmt.executeQuery(builder.toString());

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                District district = new District(Integer.toString(id), name);
                districts.add(district);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        return districts;

    }

    /**
     * Edits the name of a specific table entry.
     * @param table Name of the table where the edit is meant to be carried out
     * @param name New name of the entry
     * @param id Database id of the entry
     * @return True if done successfully.
     */
    private boolean editInTable(String table, String name, String id) {
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE ");
            builder.append(table);
            builder.append(" SET name = '");
            builder.append(name);
            builder.append("' WHERE id =");
            builder.append(id);
            stmt.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            throw new RuntimeException("An error with the database has occurred. Please contact the authors");
        }
        return true;
    }

    /**
     * Deletes a specific table entry.
     * @param table Name of the table where the edit is meant to be carried out
     * @param id Database id of the entry
     * @return True if done successfully.
     */
    private boolean deleteFromTable(String table, String id) {
        try (
            Statement stmt = connection.createStatement();) {
            StringBuilder builder = new StringBuilder();
            builder.append("DELETE FROM ");
            builder.append(table);
            builder.append(" WHERE id = ");
            builder.append(id);
            stmt.executeUpdate(builder.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }
}
