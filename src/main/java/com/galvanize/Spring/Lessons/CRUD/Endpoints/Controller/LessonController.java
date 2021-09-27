package com.galvanize.Spring.Lessons.CRUD.Endpoints.Controller;


import com.galvanize.Spring.Lessons.CRUD.Endpoints.Model.Lesson;
import com.galvanize.Spring.Lessons.CRUD.Endpoints.Repository.LessonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonRepository repository;

    public LessonController(LessonRepository repository) {
        this.repository = repository;
    }


    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("{id}")
    public Optional<Lesson> getTitleByID(@PathVariable long id) {
        return this.repository.findById(id);
    }

    @DeleteMapping("{id}")
    public String  deleteDatabaseRowByID(@PathVariable long id) {
        this.repository.deleteById(id);
        return "Record " + id + " has been deleted.";
    }

    @PatchMapping("{id}")
    public Object patchDataRowSQL(@RequestBody Lesson lesson){
        if (this.repository.findById(lesson.getId()).isEmpty()){
            return lesson.getId() + " is not a valid SQL ID. Please try again.";
        }
        else {return this.repository.save(lesson);}
    }
}