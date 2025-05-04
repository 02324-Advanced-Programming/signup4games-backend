package com.example.accessing_data_rest.repositories;

import com.example.accessing_data_rest.model.Game;
import com.example.accessing_data_rest.model.GameState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "game", path = "game")
public interface GameRepository extends PagingAndSortingRepository<Game, Long>, CrudRepository<Game, Long> {

    List<Game> findByName(String name);

    List<Game> findByState(GameState state);

    Game findByUid(long uid);

    void deleteByUid(long uid);
}
