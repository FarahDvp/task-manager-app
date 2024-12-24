package com.isamm.tasks;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TaskManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TaskManagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProjectService projectService;

	@Test
	public void getAllProjects_ReturnsEmptyList() throws Exception {
		if (mockMvc == null) {
			throw new RuntimeException("mockMvc is null. Check if projectController is being injected correctly.");
		}

		String responseContent = mockMvc.perform(get("/api/projects"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		System.out.println("Response Content: " + responseContent);
	}

	@Autowired
	private ObjectMapper objectMapper;  // Jackson ObjectMapper for JSON conversion

	@Test
	public void createProjectWithRequestBody() throws Exception {
		// Given
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setName("New Project");
		projectDTO.setDescription("Test project");

		// When
		ResultActions result = mockMvc.perform(post("/api/projects")
				.content(objectMapper.writeValueAsString(projectDTO))
				.contentType(MediaType.APPLICATION_JSON));

		// Then
		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("New Project"))
				.andExpect(jsonPath("$.description").value("Test project"));
	}

	@Test
	public void unitgetAllProjects_ReturnsEmptyList() {

		// Act
		List<ProjectDTO> projects = projectService.findAll();

		// Assert
		assertEquals(2, projects.size(), "The list of projects should be empty");
	}



}
