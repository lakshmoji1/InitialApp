package com.prog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prog.entity.Medicine;


@Repository
public interface MediRepo extends JpaRepository<Medicine,Integer> 
{
	Medicine getMedicineByName(String name);
	
	List<Medicine> findByNameLike(String name);
}