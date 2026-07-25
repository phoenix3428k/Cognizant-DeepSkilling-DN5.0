package com.cognizant.ormlearn.repositories;

import com.cognizant.ormlearn.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}