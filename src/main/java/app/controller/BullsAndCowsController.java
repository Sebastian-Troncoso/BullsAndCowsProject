package app.controller;

import app.dao.BullsAndCowsDao;
import app.dto.Game;
import app.dto.Round;
import app.exception.InputGuessInvalidException;
import app.exception.InputGuessInvalidLength;
import app.exception.InvalidIDException;
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

    @GetMapping()
    public List<Game> getGames() throws SQLException {

        connectToDatabase();
        return service.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable String id) throws SQLException, InvalidIDException {

        connectToDatabase();
        return service.getGameById(id);
    }
    
    @GetMapping("/rounds/{id}")
    public List<Round> getRoundsByGameId(@PathVariable String id) throws SQLException, InvalidIDException {

        connectToDatabase();
        return service.getRoundBasedOnGameID(id);
    }

    @PostMapping("/create")
    public ResponseEntity createGame() throws SQLException {

        connectToDatabase();
        service.addGame();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/guess/{id}/{guess}")
    public List<Round> guessAnswer(@PathVariable String id, @PathVariable String guess )
            throws InputGuessInvalidLength,
            InputGuessInvalidException,
            SQLException, InvalidIDException {

        connectToDatabase();
        return service.addGuess(id, guess);
    }
    

    private void connectToDatabase() {
        service.setUpDatabase();
    }

}
