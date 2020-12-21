package BullsAndCows.controller;

import BullsAndCows.servicelayer.ServiceLayer;
import BullsAndCows.servicelayer.ServiceLayerImpl;

public class BullsAndCowsController {
    ServiceLayer service = new ServiceLayerImpl();

    void runProgram () {
        connectToDatabase();
    }

    private void connectToDatabase() {
        service.setUpDatabase();
    }

}
