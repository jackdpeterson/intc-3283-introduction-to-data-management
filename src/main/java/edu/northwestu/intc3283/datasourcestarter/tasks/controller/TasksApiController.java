package edu.northwestu.intc3283.datasourcestarter.tasks.controller;

import edu.northwestu.intc3283.datasourcestarter.tasks.dto.UpdateTaskRequest;
import edu.northwestu.intc3283.datasourcestarter.tasks.entity.Task;
import edu.northwestu.intc3283.datasourcestarter.tasks.repository.TasksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TasksApiController {

    private final TasksRepository tasksRepository;

    public TasksApiController(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") Long id) {
        Optional<Task> taskOptional = this.tasksRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        return taskOptional.get();
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long id, @Validated @RequestBody UpdateTaskRequest taskRequest) {
        final Task task = this.tasksRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        this.tasksRepository.save(task);
        return task;
    }

    @PostMapping("")
    public Task createTask(@Validated @RequestBody Task task) {
        task.setStatus("PENDING");
        return this.tasksRepository.save(task);
    }

    @GetMapping("")
    public Page<Task> getCollection(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(
                page,
                pageSize
        );
        final Page<Task> results = this.tasksRepository.findAll(pageable);
        return results;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("id") Long id) {
        if (this.tasksRepository.existsById(id)) {
            this.tasksRepository.deleteById(id);
        }
    }
}
