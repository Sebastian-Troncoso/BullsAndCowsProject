package BullsAndCows.controller;

import BullsAndCows.dao.BullsAndCowsDaoDbImpl;
import BullsAndCows.servicelayer.ServiceLayer;
import BullsAndCows.servicelayer.ServiceLayerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/bullcow")
public class BullsAndCowsController {

    ServiceLayer service;

    @Autowired
    BullsAndCowsController(ServiceLayer service) {
        this.service = service;
    }

    void run () {
        connectToDatabase();
    }

    @PostMapping()
    public ResponseEntity<Void> createGame() throws SQLException {
        service.addGame();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    private void connectToDatabase() {
        service.setUpDatabase();
    }

}
