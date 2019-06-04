package com.oszimt.cdm.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 * @author JH
 * @version 1.0
 * @since 24.11.2017
 */
public class ContentPanel extends JPanel {
    private static final long serialVersionUID = 20171113L;

    private SpringLayout layout = new SpringLayout();

    /**
     * @since 24.11.2017 Creates a new {@code ContentPanel} instance.
     * @param position
     */
    public ContentPanel(String position) {
        super();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 400));
        setLayout(layout);

        if (position.equals("west")) {
            createWestPanel();
        } else {
            createEastPanel();
        }
    }

    /**
     * @since 24.11.2017
     */
    private void createWestPanel() {
        // elements
        TablePanel districtTable = new TablePanel();
        ShowCityPanel showCityPanel = new ShowCityPanel();
        JTextArea description = new JTextArea();
        description.setPreferredSize(new Dimension(200, 100));
        description.setLineWrap(true);
        description.setText("");

        this.add(districtTable);
        this.add(showCityPanel);
        this.add(description);

        // layout adjustments
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, districtTable, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, districtTable, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, showCityPanel, 10, SpringLayout.SOUTH, districtTable);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showCityPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, description, 10, SpringLayout.SOUTH, showCityPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, description, 0, SpringLayout.HORIZONTAL_CENTER, this);
    }

    /**
     * @since 24.11.2017
     */
    private void createEastPanel() {
        // elements
        AddCityPanel addCityPanel = new AddCityPanel();
        AddDistrictPanel addDistrictPanel = new AddDistrictPanel();
        EntryPanel editEntryPanel = new EntryPanel(DataService.EDIT);
        EntryPanel removeEntryPanel = new EntryPanel(DataService.REMOVE);

        add(addCityPanel);
        add(addDistrictPanel);
        add(editEntryPanel);
        add(removeEntryPanel);

        // layout adjustments
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addCityPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, addCityPanel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, addDistrictPanel, 10, SpringLayout.SOUTH, addCityPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addDistrictPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, editEntryPanel, 10, SpringLayout.SOUTH, addDistrictPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, editEntryPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, removeEntryPanel, 10, SpringLayout.SOUTH, editEntryPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, removeEntryPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
    }
}
