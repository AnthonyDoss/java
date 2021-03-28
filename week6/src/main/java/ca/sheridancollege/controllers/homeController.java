package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.Repos.CourseRepository;
import ca.sheridancollege.Repos.ProfessorRespository;
import ca.sheridancollege.Repos.StudentRepository;
import ca.sheridancollege.beans.Course;
import ca.sheridancollege.beans.Professor;
import ca.sheridancollege.beans.Student;

@Controller
public class homeController {

	@Autowired
	private StudentRepository stuRepo;
	
	@Autowired
	private CourseRepository corseRepo;
	
	@Autowired
	private ProfessorRespository proffRepo;
	
	
	@GetMapping("/")
	public String root() {
		return "home.html";
	}
	
	@GetMapping("/addStudent")
	public String loadStudent() {
		return "addStudent.html";
	}
	
	@PostMapping("/addStudent")
	public String saveStudent(@RequestParam String name) {
		Student stu = Student.builder().name(name).build();
		stuRepo.save(stu);
		return "addStudent.html";
	}
	@GetMapping("/addProff")
	public String loadProff() {
		return "addProff.html";
	}
	
	@PostMapping("/addProff")
	public String saveProffPost(@RequestParam String name) {
		Professor proff = Professor.builder().name(name).build();
		proffRepo.save(proff);
		return "addProff.html";
	}
	@GetMapping("/addCourse")
	public String loadCourse() {
		return "addCourse.html";
	}
	
	@PostMapping("/addCourse")
	public String saveCourse(@RequestParam String name) {
		Course cor = Course.builder().name(name).build();
		corseRepo.save(cor);
		return "addCourse.html";
	}
	@GetMapping("/assignToCourse")
	public String assignStudent(Model model) {
		model.addAttribute("students",stuRepo.findAll());
		model.addAttribute("courses",corseRepo.findAll());
		
		return "assignToCourse.html";
	}
	
	@PostMapping("/assignToCourse")
	public String assignPostStudent(@RequestParam String name,@RequestParam Integer course) {

	Student stu = stuRepo.findByName(name).get();
	Course cor = corseRepo.findById(course).get();
		
		stu.getCourses().add(cor);
		cor.getStudents().add(stu);
		
		corseRepo.save(cor);
		return "redirect:/assignToCourse";
	}
	@GetMapping("/assignProffToCourse")
	public String assignProff(Model model) {
		model.addAttribute("proffs",proffRepo.findAll());
		model.addAttribute("courses",corseRepo.findAll());
		
		return "assignProff.html";
	}
	
	@PostMapping("/assignProffToCourse")
	public String assignPostProff(@RequestParam String name,@RequestParam Integer course) {

	Professor proff = proffRepo.findByName(name).get();
	Course cor = corseRepo.findById(course).get();
		
		proff.getCourses().add(cor);
		cor.setProfessor(proff);
		
		corseRepo.save(cor);
		//proffRepo.save(cor);
		return "redirect:/assignProffToCourse";
	}
	@GetMapping("/viewCourse")
	public String goView(Model model) {
		model.addAttribute("courses",corseRepo.findAll());
		return "viewCourse.html";
	}
	
	@PostMapping("/viewCourse")
	public String goViewPOst(Model model,@RequestParam Integer course) {
		Course cor = corseRepo.findById(course).get();
		model.addAttribute("course",cor);
		model.addAttribute("courses",corseRepo.findAll());
		return "viewCourse.html";
	}
	@GetMapping("/viewProff")
	public String goViewProff(Model model) {
		model.addAttribute("proffs",proffRepo.findAll());
		return "viewProff.html";
	}
	
	@PostMapping("/viewProff")
	public String goViewPOstProff(Model model,@RequestParam Integer proffId) {
		Professor prof = proffRepo.findById(proffId).get();
		model.addAttribute("proffs",proffRepo.findAll());
		model.addAttribute("proff",prof);
		//model.addAttribute("course",prof.getCourses());
		//model.addAttribute("students",);
		return "viewProff.html";
	}
	@GetMapping("/delete/{id}/{corID}")
	private String dropCourse(@PathVariable int id,@PathVariable int corID) {
		if(corseRepo.findById(corID).isPresent() && stuRepo.findById(id).isPresent()) {
		Course cor = corseRepo.findById(corID).get();
		Student stu = stuRepo.findById(id).get();
		
		for(Course c : stu.getCourses()) {
			if(c.getId().equals(cor.getId())) {
				stu.getCourses().remove(c);
				break;
			}
		}
		
		for(Student s : cor.getStudents()) {
			if(s.getId().equals(stu.getId())) {
				cor.getStudents().remove(s);
				break;
			}
		}
		stuRepo.save(stu);
		corseRepo.save(cor);
		}
		return "redirect:/viewCourse";
	}
}
