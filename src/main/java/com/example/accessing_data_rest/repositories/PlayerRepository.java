package com.example.accessing_data_rest.repositories;

import com.example.accessing_data_rest.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "player", path = "player")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long>, CrudRepository<Player, Long> {

    List<Player> findByGame_UidAndUser_Uid(Long gameId, Long userId);

    List<Player> findByGame_Uid(Long gameId);

}
