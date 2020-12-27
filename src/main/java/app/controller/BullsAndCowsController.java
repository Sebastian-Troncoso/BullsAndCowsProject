package app.controller;

import app.dao.BullsAndCowsDao;
import app.dto.Game;
import app.exception.InputGuessInvalidException;
import app.exception.InputGuessInvalidLength;
import app.servicelayer.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class BullsAndCowsController {

    ServiceLayer service;
    BullsAndCowsDao dao;

    @Autowired
    BullsAndCowsController(ServiceLayer service, BullsAndCowsDao  dao) {
        this.service = service;
        this.dao = dao;
    }

    @GetMapping
    public List<Game> getGames() throws SQLException {
        connectToDatabase();
        return service.getAllGames();
    }


    @PostMapping()
    public ResponseEntity<Void> createGame() throws SQLException {
        connectToDatabase();
        service.addGame();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    //Need to working on this part
    public void guessAnswer(@PathVariable String gameID, @RequestBody String guess )
            throws InputGuessInvalidLength,
            InputGuessInvalidException,
            SQLException {
        System.out.println("This is the game ID: " +gameID + " This is the guess answer: " + guess);
        service.addGuess(gameID, guess);
    }



    private void connectToDatabase() {
        service.setUpDatabase();
    }

}
