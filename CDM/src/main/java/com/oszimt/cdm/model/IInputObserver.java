package com.oszimt.cdm.model;

/**
 * [iobs description]
 *
 * @author JH
 * @version 1.0
 * @since 07.12.2017
 */
public interface IInputObserver {

    /**
     * 
     * @since 30.11.2017
     * @return
     */
    public String getUserInput();

    /**
     * refreshes data
     * 
     * @since 11.12.2017
     * @param s
     */
    public void update(City city);

    /**
     * refreshes data
     * 
     * @since 11.12.2017
     */
    public void update();

}
