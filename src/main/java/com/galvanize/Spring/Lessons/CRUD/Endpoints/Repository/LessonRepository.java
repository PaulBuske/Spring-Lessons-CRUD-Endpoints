package com.galvanize.Spring.Lessons.CRUD.Endpoints.Repository;

import com.galvanize.Spring.Lessons.CRUD.Endpoints.Model.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;


public interface LessonRepository extends CrudRepository<Lesson, Long> {

    Iterable<Lesson> findByTitle(String title);

    Iterable<Lesson> findByDeliveredOnBetween(Date deliveredOnStart, Date deliveredOnEnd);


}