package ca.sheridancollege.Repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Student;

public interface StudentRepository extends CrudRepository<Student,Integer> {
	
 public Optional<Student> findByName(String name);
}
