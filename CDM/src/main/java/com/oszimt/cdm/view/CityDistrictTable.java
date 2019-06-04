package com.oszimt.cdm.view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.District;

/**
 * @author JH
 * @version 1.0
 * @since 24.11.2017
 */
public class CityDistrictTable extends JTable {
    private static final long serialVersionUID = 20171113L;

    private DefaultTableModel tableModel;

    /**
     * Creates a new {@code CityDistrictTable} instance.
     * 
     * @since 24.11.2017
     */
    public CityDistrictTable() {
        super();
    }

    /**
     * Creates a new {@code CityDistrictTable} instance.
     * 
     * @since 24.11.2017
     * @param tableModel
     */
    public CityDistrictTable(DefaultTableModel tableModel) {
        super(tableModel);
        this.tableModel = tableModel;

        getTableHeader().setFont(new Font(DataService.COURIER, Font.BOLD, 12));
        getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getTableHeader().setReorderingAllowed(false);

        setBackground(Color.WHITE);
        setFont(new Font(DataService.COURIER, Font.PLAIN, 12));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        setEnabled(false);
        setFocusable(false);
        centerCellData();
        setFillsViewportHeight(true);
    }

    /**
     * @since 24.11.2017
     * @param cityName
     * @param districtName
     */
    protected void addRow(String cityName, String districtName) {
        String[] row = { cityName, districtName };
        this.tableModel.addRow(row);
        revalidate();
    }

    /**
     * @since 24.11.2017
     */
    private void centerCellData() {
        for (int i = 0; i < this.getColumnCount(); i++) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * @since 30.11.2017
     */
    protected void fetchAllRows() {
        int count = 0;
        for (City city : DataService.getInstance().getAllCities()) {
            if (!city.getDistricts().isEmpty()) {
                for (District district : city.getDistricts()) {
                    addRow(city.getName(), district.getName());
                    count++;
                }
            } else if (city.getName().equals(DataService.CITY)) {
            } else {
                addRow(city.getName(), "");
            }
        }
    }

    /**
     * @since 07.12.2017
     * @param cityList
     */
    protected void setRows(List<City> cityList) {
        int count = 0;
        for (City city : cityList) {
            if (!city.getDistricts().isEmpty()) {
                for (District district : city.getDistricts()) {
                    addRow(city.getName(), district.getName());
                    count++;
                }
            } else if (city.getName().equals(DataService.CITY)) {
            } else {
                addRow(city.getName(), "");
            }
        }

    }

    /**
     * Removes all rows in {@code CityDistrictTable}.
     * @since 30.11.2017
     */
    protected void removeAllRows() {
        while (tableModel.getRowCount() > 0) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.removeRow(i);
            }
        }
    }
}