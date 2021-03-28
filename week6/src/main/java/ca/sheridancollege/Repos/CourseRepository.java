package ca.sheridancollege.Repos;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Course;


public interface CourseRepository  extends CrudRepository<Course,Integer> {

	
}
