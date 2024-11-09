package com.example.repository;

import com.example.model.ShelterModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends MongoRepository<ShelterModel, String>, QuerydslPredicateExecutor<ShelterModel> {
}
