package com.galvanize.Spring.Lessons.CRUD.Endpoints;

import com.galvanize.Spring.Lessons.CRUD.Endpoints.Model.Lesson;
import com.galvanize.Spring.Lessons.CRUD.Endpoints.Repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;


@SpringBootTest
@AutoConfigureMockMvc
class SpringLessonsCrudEndpointsApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	LessonRepository repository;


	//TODO write a get that inserts data into the repo and then retrieves the data

	@Transactional
	@Rollback
	@Test
	public void getTestThatRetirevesDataByGet() throws Exception {
		//Setup
		Lesson lesson = new Lesson();
		lesson.setTitle("Monday Sucks");
		SimpleDateFormat formmater = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formmater.parse("2021-09-27");
		lesson.setDeliveredOn(date);

		this.repository.save(lesson);
		//Execution
		this.mvc.perform(get("/lesson"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is("Monday Sucks")));

	}
		@Transactional
		@Rollback
		@Test
		public void getByIdVariableReturnsCorrectData() throws Exception {
			//Setup
			Lesson lesson = new Lesson();
			lesson.setTitle("Monday Sucks");
			SimpleDateFormat formmater = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formmater.parse("2021-09-27");
			lesson.setDeliveredOn(date);

			this.repository.save(lesson);
			//Execution
			this.mvc.perform(get("/lesson/1"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", is(1)));
		}


			//Assertion

	@Transactional
	@Rollback
	@Test

	public void testCreateLesson() throws Exception {

		//set up

		String jason = "{\"title\": \"SQL TEST\",\"deliveredOn\": \"2017-04-06\"}";

		this.mvc.perform(post("/lesson")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jason))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("SQL TEST")));

	}


	@Transactional
	@Rollback
	@Test

	public void patchTest() throws Exception {
		Lesson lesson = new Lesson();
		lesson.setTitle("Monday Sucks");
		SimpleDateFormat formmater = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formmater.parse("2021-09-27");
		lesson.setDeliveredOn(date);

		this.repository.save(lesson);

		String jason = "{\"id\" : 1, \"title\": \"SQL TEST\",\"deliveredOn\": \"2017-04-06\"}";
		this.mvc.perform(patch("/lesson/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jason))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is("SQL TEST")));

	}


}


