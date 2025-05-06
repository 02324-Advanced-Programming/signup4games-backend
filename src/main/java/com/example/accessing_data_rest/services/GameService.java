package com.example.accessing_data_rest.services;

import com.example.accessing_data_rest.model.Game;
import com.example.accessing_data_rest.model.GameState;
import com.example.accessing_data_rest.model.Player;
import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.repositories.GameRepository;
import com.example.accessing_data_rest.repositories.PlayerRepository;
import com.example.accessing_data_rest.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Game> getGames() {
        return (List<Game>) gameRepository.findAll();
    }

    public List<Game> getOpenGames() {
        return gameRepository.findByState(GameState.SIGNUP);
    }

    @Transactional
    public Game createGame(Game game) {
        game.setState(GameState.SIGNUP);
        try {
        Game saved = gameRepository.save(game);
        User owner = saved.getCreator();
        Player player = new Player();
        player.setGame(saved);
        player.setUser(owner);
        player.setName(owner.getName());
        playerRepository.save(player);
        return saved;
        } catch (Exception e) {
            throw new RuntimeException("Error creating game: " + e.getMessage());
        }
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

    @Transactional
    public Player joinGame(long gameId, long userId, String name) throws ChangeSetPersister.NotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (game.getState() != GameState.SIGNUP) {
            throw new IllegalStateException("Cannot join a non-open game");
        }
        if (game.getPlayers().size() >= game.getMaxPlayers()) {
            throw new IllegalStateException("Game is full");
        }
        boolean alreadyIn = game.getPlayers().stream()
                .anyMatch(p -> p.getUser().getUid() == userId);
        if (alreadyIn) {
            throw new IllegalStateException("Already joined");
        }
        Player p = new Player();
        p.setGame(game);
        p.setName(name);
        User user = userRepository.findByUid(userId);
        if (user == null) {
            throw new IllegalStateException("User not found");
        }
        p.setUser(user);
        return playerRepository.save(p);
    }

    @Transactional
    public void leaveGame(long gameId, long userId) {
        var users = playerRepository.findByGame_UidAndUser_Uid(gameId, userId);
        if (users == null || users.isEmpty()) {
            throw new IllegalStateException("Player not found in game");
        }
        var game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalStateException("Game not found"));
        var creator = game.getCreator();
        if (creator.getUid() == userId) {
            throw new IllegalStateException("Creator cannot leave the game");
        }
        Player player = users.get(0);
        playerRepository.delete(player);
    }
}