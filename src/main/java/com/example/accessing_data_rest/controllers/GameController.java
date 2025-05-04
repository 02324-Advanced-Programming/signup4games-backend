package com.example.accessing_data_rest.controllers;

import com.example.accessing_data_rest.model.Game;
import com.example.accessing_data_rest.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roborally/games")
public class GameController {
    @Autowired private GameService gameService;

    @GetMapping("")
    public List<Game> getGames() {
        return gameService.getGames();
    }

    @GetMapping("/opengames")
    public List<Game> getOpenGames() {
        return gameService.getOpenGames();
    }

    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Game postGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    @PostMapping("/{gameId}/start")
    public void startGame(@PathVariable Long gameId, @RequestParam Long userId) {
        gameService.startGame(gameId, userId);
    }

    @DeleteMapping("/{gameId}")
    public void deleteGame(@PathVariable Long gameId, @RequestParam Long userId) {
        gameService.deleteGame(gameId, userId);
    }
}