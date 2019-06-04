package com.oszimt.cdm.model;

import java.util.List;

/**
 * [iobs description]
 *
 * @author JH
 * @version 1.0
 * @since 15.05.17
 */
public interface ITableObserver {
    /**
     * [update description]
     * 
     * @version 1.0
     * @since 15.05.17
     */
    public void update();

    /**
     * @since 07.12.2017
     * @param cityList
     */
    public void update(List<City> cityList);
}
