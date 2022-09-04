package com.example.demo.controller;


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
    private TaskRepository repository;

    void TaskController(TaskRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/tasks")
    Iterable<TaskEntity> all() {
        logger.info("List all tasks");
        return repository.findAll();
    }

    @PostMapping("/task")
    public void add( @RequestParam(value = "name") String name , @RequestParam(value = "description") String description) {
        TaskEntity task = new TaskEntity(name, description);
        logger.info("Created new task with name {} and description {}", name , description);
        repository.save(task);
        logger.info("Saved new task with name {} in DB", name);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @DeleteMapping("/task")
    public ResponseEntity<Integer> deleteOrder(@RequestParam(value = "Id") int Id) {
        logger.info("Received task's id");
        repository.deleteById(Id);
        logger.info("Deleted task by id {}", Id);
        return ResponseEntity.ok(Id);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskEntity> updateUser( @RequestBody TaskEntity taskDetails)  {
        logger.info("Received task's id");
        TaskEntity task = repository.findById(taskDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found on :: "+ taskDetails.getId()));
        logger.info("Found task by id {}", taskDetails.getId());

        task.setName(taskDetails.getName());
        logger.info("Updated task's name by id {}", task.getId());
        task.setDescription(taskDetails.getDescription());
        logger.info("Updated task's description by id {}",  task.getId());

        final TaskEntity updatedTask = repository.save(task);
        logger.info("Updated task by id {}",  task.getId());
        return ResponseEntity.ok(updatedTask);
    }

}
