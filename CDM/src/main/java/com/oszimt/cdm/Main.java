package com.oszimt.cdm;

import com.oszimt.cdm.business.BusinessSort;
import com.oszimt.cdm.data.XMLData;
import com.oszimt.cdm.view.Gui;

public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
//        Tui tui = new Tui(new BusinessSort(new XMLModifier()));
        Gui gui = new Gui(new BusinessSort(new XMLData()));

    }
}