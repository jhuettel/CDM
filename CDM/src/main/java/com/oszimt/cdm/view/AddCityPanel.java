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
public class AddCityPanel extends JPanel {
    private static final long serialVersionUID = 20171113L;

    public AddCityPanel() {
        super();
        // panel base
        setPreferredSize(new Dimension(200, 55));
        setBackground(Color.WHITE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        String operation = DataService.ADD;
        String full = operation + DataService.CITY;
        setBorder(BorderFactory.createTitledBorder(full));

        // elements
        MyTextField cityInput = new MyTextField(DataService.CITY, operation);
        cityInput.setPreferredSize(new Dimension(60, 26));
        add(cityInput);

        ActionButton actionbutton = new ActionButton(operation);
        actionbutton.setActionCommand(full);
        add(actionbutton);

        // layout adjustments
        layout.putConstraint(SpringLayout.EAST, cityInput, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, actionbutton, 10, SpringLayout.EAST, cityInput);
        layout.putConstraint(SpringLayout.WEST, cityInput, 10, SpringLayout.WEST, this);
    }
}
