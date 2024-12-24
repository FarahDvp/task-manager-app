package com.isamm.tasks;

import com.isamm.tasks.models.*;
import com.isamm.tasks.repository.LabelRepository;
import com.isamm.tasks.repository.MemberRepository;
import com.isamm.tasks.repository.ProjectRepository;
import com.isamm.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class TaskManagementApplication implements CommandLineRunner {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private LabelRepository labelRepository;
	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://devazure.eastus.cloudapp.azure.com","http://devazure.eastus.cloudapp.azure.com/app"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
	@Override
	public void run(String... args) throws Exception {
		// Create two Members
/*		Member member1 = new Member();
		member1.setUsername("admin");
		member1.setRole(Role.valueOf("ADMIN"));
		member1.setPassword(passwordEncoder.encode("admin"));*/
		var member1 = Member.builder()
				.username("admin")
				.fullname("Admin Admin")
				.email("admin@gmail.com")
				.phone("12345678")
				.password(passwordEncoder.encode("admin"))
				.role(Role.valueOf("ADMIN"))
				.build();
		var member2 = Member.builder()
				.username("user")
				.fullname("User User")
				.email("user@gmail.com")
				.phone("12345678")
				.password(passwordEncoder.encode("user"))
				.role(Role.valueOf("USER"))
				.build();
		/*Member member2 = new Member();
		member2.setUsername("user");
		member2.setPassword(new BCryptPasswordEncoder().encode("user"));*/

		// Create a Project
		Project project = new Project();
		project.setName("Sample Project");
		project.setDescription("This is a sample project");

		Project project2 = new Project();
		project2.setName("Sample Project 2");
		project2.setDescription("This is a sample project 2");
		// Create two Tasks
		Task task1 = new Task();
		task1.setTitle("Task 1");
		task1.setDescription("Description for Task 1");
		task1.setStartDate(LocalDate.now());
		task1.setDueDate(LocalDate.now().minusDays(7));
		task1.setCompleted(false);
		task1.setPriority("High");
		task1.setStatus("IN_PROGRESS");
		Task task2 = new Task();
		task2.setTitle("Task 2");
		task2.setDescription("Description for Task 2");
		task2.setStartDate(LocalDate.now());
		task2.setPriority("Normal");
		task2.setDueDate(LocalDate.now().minusDays(7));
		task2.setCompleted(false);
		task2.setStatus("IN_PROGRESS");
		Task task3 = new Task();
		task3.setTitle("Task 3");
		task3.setDescription("Description for Task 3");
		task3.setStartDate(LocalDate.now());
		task3.setDueDate(LocalDate.now().minusDays(7));
		task3.setCompleted(false);
		task3.setPriority("Low");
		task3.setStatus("PENDING");
		Task task4 = new Task();
		task4.setTitle("Task 4");
		task4.setDescription("Description for Task 4");
		task4.setStartDate(LocalDate.now());
		task4.setDueDate(LocalDate.now().minusDays(7));
		task4.setCompleted(false);
		task1.setPriority("High");
		task4.setStatus("IN_PROGRESS");
		Task task5 = new Task();
		task5.setTitle("Task 5");
		task5.setDescription("Description for Task 5");
		task5.setStartDate(LocalDate.now());
		task5.setDueDate(LocalDate.now().minusDays(7));
		task5.setCompleted(false);
		task5.setStatus("PENDING");
		task5.setPriority("Normal");
		Task task6 = new Task();
		task6.setTitle("Task 6");
		task6.setDescription("Description for Task 6");
		task6.setStartDate(LocalDate.now());
		task6.setDueDate(LocalDate.now().plusDays(7));
		task6.setCompleted(false);
		task6.setStatus("DONE");
		task6.setPriority("Low");
		// Create two Labels
		Label label1 = new Label();
		label1.setName("Label 1");

		Label label2 = new Label();
		label2.setName("Label 2");

		// Establish relationships
		project.setMembers(Arrays.asList(member1, member2));

		task1.setProject(project);
		task2.setProject(project);
		task3.setProject(project);
		task4.setProject(project2);
		task5.setProject(project2);
		task6.setProject(project2);


		Set<Task> tasks = new HashSet<>(Arrays.asList(task1, task2));
		label1.setTasks(tasks);
		label2.setTasks(tasks);

		// Save entities
		memberRepository.saveAll(Arrays.asList(member1, member2));
		projectRepository.save(project);
		projectRepository.save(project2);
		taskRepository.saveAll(Arrays.asList(task1, task2, task3, task4, task5, task6));
		labelRepository.saveAll(Arrays.asList(label1, label2));
	}
}
