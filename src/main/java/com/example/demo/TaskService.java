package com.example.demo;

import com.example.demo.entity.TaskEntity;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;
    final static Logger logger = LoggerFactory.getLogger(TaskService.class);


    public TaskEntity updateTask(TaskEntity taskEntity) {

        logger.info("Received task's id");
        TaskEntity task = repository.findById(taskEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found on :: "+ taskEntity.getId()));
        logger.info("Found task by id {}", taskEntity.getId());

        task.setName(taskEntity.getName());
        logger.info("Updated task's name by id {}", task.getId());
        task.setDescription(taskEntity.getDescription());
        logger.info("Updated task's description by id {}",  task.getId());

        final TaskEntity updatedTask = repository.save(task);
        logger.info("Updated task by id {}",  task.getId());

        return  updatedTask;

    }

    public void addTask(String name, String description){
        TaskEntity task = new TaskEntity(name, description);
        logger.info("Created new task with name {} and description {}", name , description);
        repository.save(task);
        logger.info("Saved new task with name {} in DB", name);
    }

    public Iterable<TaskEntity> getAllTasks () {
        logger.info("List all tasks");
        return repository.findAll();
    }

    public void deleteTaskById (int Id) {

        logger.info("Received task's id");
        repository.deleteById(Id);
        logger.info("Deleted task by id {}", Id);

    }

}
