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
public class AddDistrictPanel extends JPanel {
    private static final long serialVersionUID = 20171113L;

    /**
     * @since 24.11.2017 Creates a new {@code AddDistrictPanel} instance.
     */
    public AddDistrictPanel() {
        super();
        // panel base
        setPreferredSize(new Dimension(200, 85));
        setBackground(Color.WHITE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        String operation = DataService.ADD;
        String full = operation + DataService.DISTRICT;
        setBorder(BorderFactory.createTitledBorder(full));

        // elements
        MyComboBox cityList = new MyComboBox(DataService.CITY, operation);
        add(cityList);

        MyTextField districtInput = new MyTextField(DataService.DISTRICT, operation);
        districtInput.setPreferredSize(new Dimension(60, 26));
        add(districtInput);

        ActionButton actionbutton = new ActionButton(operation);
        actionbutton.setActionCommand(full);
        add(actionbutton);

        // layout adjustments
        layout.putConstraint(SpringLayout.EAST, cityList, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, districtInput, 10, SpringLayout.EAST, cityList);
        layout.putConstraint(SpringLayout.WEST, cityList, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, actionbutton, 10, SpringLayout.SOUTH, districtInput);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, actionbutton, 0, SpringLayout.HORIZONTAL_CENTER, this);
    }
}
