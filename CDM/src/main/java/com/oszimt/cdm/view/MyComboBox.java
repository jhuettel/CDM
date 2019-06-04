package com.oszimt.cdm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;
import com.oszimt.cdm.model.IInputObserver;

/**
 * @author JH
 * @version 1.0
 * @since 28.11.2017
 */
public class MyComboBox extends JComboBox<String> implements IInputObserver, ActionListener {

    private static final long serialVersionUID = 201711282L;

    private String name;

    /**
     * Creates a new {@code MyComboBox} instance.
     * 
     * @since 28.11.2017
     */
    public MyComboBox() {
        super();
    }

    /**
     * Creates a new {@code MyComboBox} instance.
     * 
     * @since 28.11.2017
     * @param name
     * @param operation
     */
    public MyComboBox(String name, String operation) {
        super();
        this.name = name;
        DataService.getInstance().addObserverByName(this, name, operation);

        if (name.equals(DataService.CITY)) {
            // base entries: all cities
            addActionListener(this);
            updateCities();
        } else if (name.equals(DataService.DISTRICT)) {
            // default selection: all/no districts
            String[] districts = { DataService.DISTRICT };
            this.setModel(new DefaultComboBoxModel<String>(districts));
        } else {
            // false input
        }
    }

    /**
     * @since 30.11.2017
     * @see com.oszimt.cdm.model.ITableObserver#getUserInput()
     */
    @Override
    public String getUserInput() {
        return getSelectedItem().toString();
    }

    /**
     * @since 11.12.2017
     * @param city
     */
    public void update(City city) {
        this.removeAllItems();

        ArrayList<String> districtList = new ArrayList<String>();
        districtList.add(DataService.DISTRICT);
        for (District district : city.getDistricts()) {
            districtList.add(district.getName());
        }
        this.setModel(new DefaultComboBoxModel<String>(districtList.toArray(new String[0])));
    }

    /**
     * @since 11.12.2017
     * @see javax.swing.JComboBox#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DataService.getInstance().setCityValue(getUserInput(), this);
    }

    /**
     * @since 11.12.2017
     * @see com.oszimt.cdm.model.IInputObserver#update()
     */
    @Override
    public void update() {
        // refresh anytime a button is clicked (except on show)
        if (name.equals(DataService.CITY)) {
            updateCities();
        } else {
            // dev fail or need districts
        }
    }

    /**
     * @since 11.12.2017
     */
    private void updateCities() {
        ArrayList<String> cityList = new ArrayList<String>();
        cityList.add(DataService.CITY);
        for (City city : DataService.getInstance().getAllCities()) {
            cityList.add(city.getName());
        }
        // adds item : cityList to selectionable items
        this.setModel(new DefaultComboBoxModel<String>(cityList.toArray(new String[0])));
    }
}
