package com.example.accessing_data_rest.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uid;

    private String name;

    private int minPlayers;

    private int maxPlayers;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    // @JsonBackReference("owner-games")    // ← back on the singular owner
    private User owner;


    private boolean isOpen; // is the game open to join, only if state is "open" and max players is not reached

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    // @JsonManagedReference              // << add this import + annotation
    private List<Player> players;

    @Enumerated(EnumType.STRING)
    private GameState state;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long id) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    // state could be "open", "active", "finished"
    // "open" means that players can join the game
    // "active" means that the game is currently being played and players can't join
    // "finished" means that the game has ended and players can't join

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public User getCreator() {
        return owner;
    }

    public void setCreator(User creator) {
        this.owner = creator;
    }
}
