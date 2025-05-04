package com.example.accessing_data_rest.controllers;

import com.example.accessing_data_rest.model.Player;
import com.example.accessing_data_rest.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roborally/players")
public class PlayerController {
    @Autowired private PlayerService playerService;

    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Player postPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @DeleteMapping("/{gameId}")
    public void leaveGame(@PathVariable Long gameId, @RequestParam Long userId) {
        playerService.leaveGame(gameId, userId);
    }
}
