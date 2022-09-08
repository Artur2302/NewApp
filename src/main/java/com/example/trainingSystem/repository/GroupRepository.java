package com.example.trainingSystem.repository;

import com.example.trainingSystem.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Groups,Long> {
 Optional<Groups> findGroupsByName(String name);
}
