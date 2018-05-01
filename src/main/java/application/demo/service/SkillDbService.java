package application.demo.service;

import java.util.List;

import application.demo.domain.Skill;

public interface SkillDbService extends DatabaseService<Skill, Long> {
	List<Skill> findByName(String name);

//	@Query("SELECT s FROM Skill s WHERE s.name = :name")
//	Skill findSkillByName(@Param("name") String name);
}
