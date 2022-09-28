package com.microservices.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.model.Informations;

@Repository
public interface InformationRepository extends JpaRepository<Informations, Integer> {

	List<Informations> findByParentId(Integer id);

}
