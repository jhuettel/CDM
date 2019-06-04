package com.oszimt.cdm.model;

import java.util.ArrayList;

/**
 * [description]
 *
 * @author JH
 * @version 1.0
 * @since 22.08.17
 */
public interface IObservableValue extends IValue {

    /**
     * [description]
     *
     * @version 1.0
     * @since 22.08.17
     * @param observer [description]
     */
    public abstract void addObserver(ITableObserver observer);

    /**
     * [description]
     *
     * @version 1.0
     * @since 22.08.17
     * @param observer [description]
     */
    public abstract void notifyObserver(ArrayList<ITableObserver> observer);
}
