package org.vaadin.exampleapp.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.exampleapp.data.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
