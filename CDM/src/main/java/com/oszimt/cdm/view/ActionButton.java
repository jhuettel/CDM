package com.oszimt.cdm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author JH
 * @version 1.0
 * @since 28.11.2017
 */
public class ActionButton extends JButton implements ActionListener {
    private static final long serialVersionUID = 201711281L;

    /**
     * @since 24.11.2017 Creates a new {@code ActionButton} instance.
     */
    public ActionButton() {
        this("");
    }

    /**
     * @since 24.11.2017 Creates a new {@code ActionButton} instance.
     * @param name
     */
    public ActionButton(String name) {
        super(name.toUpperCase());
        addActionListener(this);
    }

    /**
     * @since 24.11.2017
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DataService.getInstance().setValue(e.getActionCommand());
    }
}
