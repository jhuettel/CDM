package com.oszimt.cdm.view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.oszimt.cdm.model.City;
import com.oszimt.cdm.model.IInputObserver;

/**
 * @author JH
 * @version 1.0
 * @since 24.11.2017
 */
public class MyTextField extends JTextField implements IInputObserver {
    private static final long serialVersionUID = 20171128L;

    private String name;

    /**
     * Creates a new {@code MyTextField} instance.
     * 
     * @since 24.11.2017
     * @param name
     * @param operation
     */
    public MyTextField(String name, String operation) {
        super(name);
        this.name = name;
        DataService.getInstance().addObserverByName(this, name, operation);

        this.addFocusListener(new FocusListener() {
            /**
             * @since 24.11.2017
             * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
             */
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(name)) {
                    setText("");
                }
            }
            /**
             * @since 24.11.2017
             * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
             */
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(name);
                }
            }
        });
    }

    /**
     * Returns the current displayed Text in {@code MyTextField}.
     * 
     * @since 30.11.2017
     * @see com.oszimt.cdm.model.ITableObserver#getUserInput()
     */
    @Override
    public String getUserInput() {
        return getText();
    }

    /**
     * @since 11.12.2017
     * @see com.oszimt.cdm.model.IInputObserver#update(com.oszimt.cdm.model.City)
     */
    @Override
    public void update(City city) {
        // optional focuslistener for possible districts
    }

    /**
     * refresh after button-action
     * 
     * @since 11.12.2017
     * @see com.oszimt.cdm.model.IInputObserver#update()
     */
    @Override
    public void update() {
        setText(name);
    }
}
