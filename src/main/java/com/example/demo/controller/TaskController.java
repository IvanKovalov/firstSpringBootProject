package com.example.demo.controller;


import com.example.demo.TaskService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TaskController {

    final static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private  TaskService taskService;

    @Autowired
    private TaskRepository repository;



   /* void TaskController(TaskRepository repository) {
        this.repository = repository;
    }*/


    @GetMapping("/tasks")
    Iterable<TaskEntity> all() {
        return taskService.getAllTasks();
    }

    @PostMapping("/task")
    public void add( @RequestParam(value = "name") String name , @RequestParam(value = "description") String description) {

        taskService.addTask(name,description);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @DeleteMapping("/task")
    public ResponseEntity<Integer> deleteTask(@RequestParam(value = "Id") int Id) {

        taskService.deleteTaskById(Id);
        return ResponseEntity.ok(Id);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskEntity> updateTask( @RequestBody TaskEntity taskDetails)  {


        final TaskEntity updatedTask = taskService.updateTask(taskDetails);

        return ResponseEntity.ok(updatedTask);
    }

}
