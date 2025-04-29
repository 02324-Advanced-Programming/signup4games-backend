package com.example.accessing_data_rest.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {

    @Id
    @Column(name="game_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uid;

    private String name;

    private int minPlayers;

    private int maxPlayers;

    private String state;

    private User creator; // the user who created the game

    private boolean isOpen; // is the game open to join, only if state is "open" and max players is not reached

    // TODO There could be more attributes here, kie
    //      in which state is the sign up for the game, did
    //      the game started or finish (after the game started
    //      you might not want new players coming in etc.)
    //      See analogous classes in client.

    @OneToMany(mappedBy="game")
    private List<Player> players;

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

    public List<Player> getPlayers() {
        return players;
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

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
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
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
