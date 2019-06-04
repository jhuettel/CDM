package com.oszimt.cdm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.ITableObserver;

/**
 * @author JH
 * @version 1.0
 * @since 24.11.2017
 */
public class TablePanel extends JPanel implements ITableObserver {
    private static final long serialVersionUID = 20171113L;

    private CityDistrictTable table;

    /**
     * Creates a new {@code TablePanel} instance.
     * 
     * @since 24.11.2017
     */
    public TablePanel() {
        super();
        // panel base
        setPreferredSize(new Dimension(200, 310));
        setLayout(new BorderLayout());

        // elements
        table = createTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(jsp, BorderLayout.CENTER);
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        DataService.getInstance().addObserver(this);
    }

    /**
     * Creates a new {@code CityDistrictTable} with default values.
     * 
     * @since 24.11.2017
     */
    private CityDistrictTable createTable() {
        table = new CityDistrictTable(new DefaultTableModel(new String[] { "City", "District" }, 0));
        table.fetchAllRows();
        return table;
    }

    /**
     * @since 24.11.2017
     * @see com.oszimt.cdm.model.ITableObserver#update()
     */
    @Override
    public void update() {
        table.removeAllRows();
        table.fetchAllRows();
    }

    /**
     * @since 07.12.2017
     * @see com.oszimt.cdm.model.ITableObserver#update()
     */
    @Override
    public void update(List<City> cityList) {
        table.removeAllRows();
        table.setRows(cityList);
    }
}
