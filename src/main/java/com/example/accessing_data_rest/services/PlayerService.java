package com.example.accessing_data_rest.services;

import com.example.accessing_data_rest.model.Player;
import com.example.accessing_data_rest.model.Game;
import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.model.GameState;
import com.example.accessing_data_rest.repositories.GameRepository;
import com.example.accessing_data_rest.repositories.PlayerRepository;
import com.example.accessing_data_rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class PlayerService {
    @Autowired private PlayerRepository playerRepository;
    @Autowired private GameRepository gameRepository;
    @Autowired private UserRepository userRepository;

    @Transactional
    public Player createPlayer(Player player) {
        Game game = gameRepository.findById(player.getGame().getUid())
                .orElseThrow(() -> new RuntimeException("Game not found"));
        User user = userRepository.findById(player.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (game.getState() != GameState.SIGNUP) {
            throw new RuntimeException("Game is not open for sign up");
        }
        if (game.getPlayers().size() >= game.getMaxPlayers()) {
            throw new RuntimeException("Game is full");
        }
        for (Player p : game.getPlayers()) {
            if (p.getUser().getUid() == user.getUid()) {
                throw new RuntimeException("User already in game");
            }
        }
        player.setGame(game);
        player.setUser(user);
        if (player.getName() == null) player.setName(user.getName());
        return playerRepository.save(player);
    }

    @Transactional
    public void leaveGame(Long gameId, Long userId) {
        List<Player> players = playerRepository.findByGame_UidAndUser_Uid(gameId, userId);
        if (players.isEmpty()) {
            throw new RuntimeException("Player not found in game");
        }
        playerRepository.delete(players.get(0));
    }
}