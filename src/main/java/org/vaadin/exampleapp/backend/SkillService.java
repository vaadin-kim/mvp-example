package org.vaadin.exampleapp.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.data.Skill;

@Component
public class SkillService {

	@Autowired
	private SkillRepository skillRepository;

	public List<Skill> getSkills() {
		return skillRepository.findAll();
	}

}
