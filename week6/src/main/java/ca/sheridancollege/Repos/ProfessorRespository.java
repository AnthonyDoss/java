package ca.sheridancollege.Repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Professor;


public interface ProfessorRespository  extends CrudRepository<Professor,Integer>{
	public Optional<Professor> findByName(String name);
}
