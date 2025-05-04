package com.example.accessing_data_rest.repositories;

import com.example.accessing_data_rest.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    List<User> findByName(String name);

    User findByUid(long uid);
}
