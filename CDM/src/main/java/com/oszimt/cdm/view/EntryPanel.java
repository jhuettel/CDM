package com.oszimt.cdm.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * @author JH
 * @version 1.0
 * @since 24.11.2017
 */
public class EntryPanel extends JPanel {
    private static final long serialVersionUID = 20171113L;

    /**
     * Creates a new {@code EntryPanel} instance.
     * 
     * @since 24.11.2017
     * @param operation
     */
    public EntryPanel(String operation) {
        super();
        // panel base
        if (operation.equals(DataService.REMOVE)) {
            setPreferredSize(new Dimension(200, 85));
        }
        if (operation.equals(DataService.EDIT)) {
            setPreferredSize(new Dimension(200, 125));
        }
        setBackground(Color.WHITE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        String full = operation + DataService.ENTRY;
        setBorder(BorderFactory.createTitledBorder(full));

        // elements
        MyComboBox cityList = new MyComboBox(DataService.CITY, operation);
        add(cityList);

        MyComboBox districtList = new MyComboBox(DataService.DISTRICT, operation);
        add(districtList);

        MyTextField newCityField = new MyTextField(DataService.CITY, operation);
        newCityField.setPreferredSize(new Dimension(60, 26));
        MyTextField newDistrictField = new MyTextField(DataService.DISTRICT, operation);
        newDistrictField.setPreferredSize(new Dimension(60, 26));

        if (operation.equals(DataService.EDIT)) {
            add(newCityField);
            add(newDistrictField);
        }

        ActionButton actionbutton = new ActionButton(operation);
        actionbutton.setActionCommand(full);
        add(actionbutton);

        // layout adjustments
        layout.putConstraint(SpringLayout.EAST, cityList, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, districtList, 10, SpringLayout.EAST, cityList);
        layout.putConstraint(SpringLayout.WEST, cityList, 10, SpringLayout.WEST, this);

        if (operation.equals(DataService.REMOVE)) {
            layout.putConstraint(SpringLayout.NORTH, actionbutton, 10, SpringLayout.SOUTH, districtList);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, actionbutton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        }
        if (operation.equals(DataService.EDIT)) {
            layout.putConstraint(SpringLayout.NORTH, newCityField, 10, SpringLayout.SOUTH, cityList);
            //.
            layout.putConstraint(SpringLayout.WEST, newCityField, 35, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, newDistrictField, 10, SpringLayout.SOUTH, districtList);
            layout.putConstraint(SpringLayout.WEST, newDistrictField, 10, SpringLayout.EAST, newCityField);

            layout.putConstraint(SpringLayout.NORTH, actionbutton, 10, SpringLayout.SOUTH, newDistrictField);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, actionbutton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        }
    }

    /**
     * Creates a new {@code EntryPanel} instance.
     * 
     * @since 24.11.2017
     */
    public EntryPanel() {
        super();
    }
}
