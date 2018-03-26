package application.demo.domain.skills;

import java.util.List;

import application.demo.domain.DatabaseService;

//public interface SkillService extends DatabaseService<Skill, Long> {
public interface SkillService extends DatabaseService<Skill, Long> {
	List<Skill> findByName(String name);

//	@Query("SELECT s FROM Skill s WHERE s.name = :name")
//	Skill findSkillByName(@Param("name") String name);
}
