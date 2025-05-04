package com.example.accessing_data_rest.services;

import com.example.accessing_data_rest.model.Game;
import com.example.accessing_data_rest.model.Player;
import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.model.GameState;
import com.example.accessing_data_rest.repositories.GameRepository;
import com.example.accessing_data_rest.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class GameService {
    @Autowired private GameRepository gameRepository;
    @Autowired private PlayerRepository playerRepository;

    public List<Game> getGames() {
        return (List<Game>) gameRepository.findAll();
    }

    public List<Game> getOpenGames() {
        return gameRepository.findByState(GameState.SIGNUP);
    }

    @Transactional
    public Game createGame(Game game) {
        game.setState(GameState.SIGNUP);
        Game saved = gameRepository.save(game);
        User owner = saved.getCreator();
        Player player = new Player();
        player.setGame(saved);
        player.setUser(owner);
        player.setName(owner.getName());
        playerRepository.save(player);
        return saved;
    }

    @Transactional
    public void startGame(Long gameId, Long userId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        if (!(game.getCreator().getUid() == userId)) {
            throw new RuntimeException("Only creator can start the game");
        }
        if (game.getPlayers().size() < game.getMinPlayers()) {
            throw new RuntimeException("Not enough players to start");
        }
        game.setState(GameState.ACTIVE);
        gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long gameId, Long userId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        if (!(game.getCreator().getUid() == userId)) {
            throw new RuntimeException("Only creator can delete the game");
        }
        gameRepository.delete(game);
    }
}