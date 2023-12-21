package com.example.lab6.Task;

import com.example.lab6.Category.Category;
import com.example.lab6.Category.CategoryRepository;
import com.example.lab6.Task.dto.CreateTaskDto;
import com.example.lab6.Task.dto.UpdateTaskDto;
import com.example.lab6.Task.errors.TaskNameTaken;
import com.example.lab6.Task.errors.TaskNotFoundException;
import com.example.lab6.Task.utils.DateParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final DateParser dateParser;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, DateParser dateParser) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.dateParser = dateParser;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasks(String sort) {
        return taskRepository.findAll(Sort.by(sort));
    }

    public Page<Task> getAllTasks(Pageable pageable) {return taskRepository.findAll(pageable); }



    public Task getTaskById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new TaskNotFoundException(taskId);
        }
        return task.get();
    }

    public Task createTask(CreateTaskDto task) {
        Date deadline = dateParser.parseDateStr(task.getDeadline());
        Task newTask = new Task(task.getTaskName(), deadline, task.getPriority(), false);
        return taskRepository.save(newTask);
    }

    public Task updateTask(Long id, UpdateTaskDto dto) {
        Optional<Task> found = taskRepository.findById(id);
        if (found.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        Task task = found.get();
        if (dto.getDone() != null) task.setDone(dto.getDone());
        if (dto.getTaskName() != null) task.setName(dto.getTaskName());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getDeadline() != null) task.setDeadline(dateParser.parseDateStr(dto.getDeadline()));
        return taskRepository.save(task);
    }

    @Transactional
    public List<Task> createMany(List<CreateTaskDto> tasks) {
        List<Task> createdTasks = new ArrayList<>();
        for (CreateTaskDto dto : tasks) {
            if (taskRepository.findByName(dto.getTaskName()).isPresent()) {
                throw new TaskNameTaken(dto.getTaskName());
            }
            Date deadline = dateParser.parseDateStr(dto.getDeadline());
            Task newTask = new Task(dto.getTaskName(), deadline, dto.getPriority(), false);
            createdTasks.add(taskRepository.save(newTask));
        }
        return createdTasks;
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    @Transactional
    public void assignCategoryToTask(Long taskId, String categoryName) {
        Optional<Category> foundCategory = categoryRepository.findByName(categoryName);
        Category category;
        if (foundCategory.isEmpty()) {
            category = new Category(categoryName);
            categoryRepository.save(category);
        } else category = foundCategory.get();
        Optional<Task> foundTask = taskRepository.findById(taskId);
        if (foundTask.isEmpty()){
            throw new TaskNotFoundException(taskId);
        }
        Task task = foundTask.get();
        task.setCategory(category);
        taskRepository.save(task);
    }
}
