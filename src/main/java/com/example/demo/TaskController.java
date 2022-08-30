package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TaskController {

    @Autowired
    private TaskRepository repository;

    void EmployeeController(TaskRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/tasks")
    Iterable<Task> all() {
        return repository.findAll();
    }

    @PostMapping("/add")
    public void add( @RequestParam(value = "name") String name , @RequestParam(value = "description") String description) {
        Task task = new Task( name, description);
         repository.save(task);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<Integer> deleteOrder(@RequestParam(value = "Id") int Id) {
        repository.deleteById(Id);
        return ResponseEntity.ok(Id);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateUser(
            @PathVariable(value = "id") int Id,
            @RequestBody Task taskDetails)  {
        Task task = repository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: "+ Id));

        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());

        final Task updatedTask = repository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

}
