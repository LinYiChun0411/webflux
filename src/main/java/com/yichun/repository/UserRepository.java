package com.yichun.repository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.yichun.domain.User;

import reactor.core.publisher.Flux;


@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
	Flux<User> findByAgeBetween(int start, int end);
	
	@Query("{'age':{'$gte':20, '$lte':30}}")
	Flux <User> oldUser();
	
}
