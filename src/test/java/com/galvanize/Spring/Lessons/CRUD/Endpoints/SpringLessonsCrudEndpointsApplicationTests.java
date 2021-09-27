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

    @Transactional
    @Rollback
    @Test
    public void customQueryReturnsResultsByProvidedTitle() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Lesson-Plan-0");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2020-08-01");
        lesson.setDeliveredOn(date);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Lesson-Plan-1");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = formatter1.parse("2021-08-26");
        lesson1.setDeliveredOn(date1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Lesson-Plan-2");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = formatter2.parse("2021-09-27");
        lesson.setDeliveredOn(date2);

        this.repository.save(lesson);
        this.repository.save(lesson1);
        this.repository.save(lesson2);

        this.mvc.perform(get("/lesson/find/Lesson-Plan-0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Lesson-Plan-0")));
    }

    @Transactional
    @Rollback
    @Test
    public void customQueryReturnsAllDataSavedBetweenTwoProvidedDates() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Lesson-Plan-0");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2020-08-24");
        lesson.setDeliveredOn(date);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Lesson-Plan-1");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = formatter1.parse("2021-08-25");
        lesson1.setDeliveredOn(date1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Lesson-Plan-2");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = formatter2.parse("2021-09-26");
        lesson2.setDeliveredOn(date2);

        this.repository.save(lesson);
        this.repository.save(lesson1);
        this.repository.save(lesson2);

        this.mvc.perform(get("/lesson/between?date1=2020-01-01&date2=2021-09-27"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Lesson-Plan-0")))
                .andExpect(jsonPath("$[1].title", is("Lesson-Plan-1")))
                .andExpect(jsonPath("$[2].title", is("Lesson-Plan-2")));
    }

}


