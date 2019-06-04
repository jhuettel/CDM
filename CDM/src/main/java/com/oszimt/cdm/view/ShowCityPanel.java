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
public class ShowCityPanel extends JPanel {
    private static final long serialVersionUID = 20171113L;

    /**
     * Creates a new {@code ShowCityPanel} instance.
     * 
     * @since 24.11.2017
     */
    public ShowCityPanel() {
        super();
        // panel base
        setPreferredSize(new Dimension(200, 55));
        setBackground(Color.WHITE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        String operation = DataService.SHOW;
        String full = operation + DataService.CITY;
        setBorder(BorderFactory.createTitledBorder(full));

        // elements
        MyComboBox cityList = new MyComboBox(DataService.CITY, operation);
        add(cityList);

        ActionButton actionbutton = new ActionButton(operation);
        actionbutton.setActionCommand(full);
        add(actionbutton);

        // layout adjustments
        layout.putConstraint(SpringLayout.EAST, cityList, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, actionbutton, 10, SpringLayout.EAST, cityList);
        layout.putConstraint(SpringLayout.WEST, cityList, 10, SpringLayout.WEST, this);
    }
}
