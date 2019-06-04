package com.oszimt.cdm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import com.oszimt.cdm.business.IBusiness;

/**
 * Creates a custom {@code JFrame}.
 * 
 * @author JH
 * @version 1.0
 * @since 24.11.2017
 */
public class Gui extends JFrame {
    private static final long serialVersionUID = 20171113L;

    /**
     * Creates a new {@code MainFrame} instance.
     * 
     * @since 24.11.2017
     */
    public Gui(IBusiness business) {
        super();
        // frame base
        setTitle("CDM 1.0");
        setPreferredSize(new Dimension(505, 495));

        DataService.getInstance().setBusiness(business);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setFont(new Font(DataService.ARIAL, Font.PLAIN, 20));

        int xGap = 10;
        int yGap = 10;

        // elements
        JLabel headline = new JLabel("City-District-Manager");
        headline.setFont(new Font(DataService.ARIAL, Font.BOLD, 30));
        add(headline, BorderLayout.NORTH);

        ContentPanel westContent = new ContentPanel("west");
        add(westContent, BorderLayout.WEST);

        ContentPanel eastContent = new ContentPanel("east");
        add(eastContent, BorderLayout.EAST);

        // layout adjustments
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headline, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, headline, yGap, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, westContent, yGap, SpringLayout.SOUTH, headline);
        layout.putConstraint(SpringLayout.WEST, westContent, xGap, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, eastContent, yGap, SpringLayout.SOUTH, headline);
        layout.putConstraint(SpringLayout.WEST, eastContent, -20, SpringLayout.EAST, westContent);

        // window base
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
