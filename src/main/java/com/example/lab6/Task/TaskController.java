package com.example.lab6.Task;

import com.example.lab6.Task.dto.CreateTaskDto;
import com.example.lab6.Task.dto.UpdateTaskDto;
import com.example.lab6.Task.errors.DateParseException;
import com.example.lab6.Task.errors.TaskNameTaken;
import com.example.lab6.Task.errors.TaskNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping("api/tasks")
@Tag(name = "Task", description = "Operations with tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @Operation(summary = "get tasks", description = "get all existing tasks")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found tasks", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class))) }) })
  @GetMapping
  public List<Task> getAll(@RequestParam(value = "sort", required = false) String sort) {
    if (sort != null) {
      return taskService.getAllTasks(sort);
    }
    return taskService.getAllTasks();
  }

  @Operation(summary = "get page", description = "get page of tasks")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found tasks", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class))) }) })
  @GetMapping("/page")
  Page<Task> getPage(@RequestParam(value = "limit") int limit,
      @RequestParam(value = "page") int page,
      @RequestParam(value = "sort", required = false) String sortProp) {
    Pageable pageable;
    if (sortProp != null) {
      pageable = PageRequest.of(page, limit, Sort.by(sortProp));
    } else
      pageable = PageRequest.of(page, limit);

    return taskService.getAllTasks(pageable);
  }

  @Operation(summary = "get task", description = "get task by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found tasks", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class))) }) })
  @GetMapping(path = "{taskId}")
  public Task getOneById(
      @PathVariable(name = "taskId") Long taskId) {
    return taskService.getTaskById(taskId);
  }

  @Operation(summary = "create task", description = "create one task from given json")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created successfully", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)) }) })
  @PostMapping
  public Task createTask(@RequestBody CreateTaskDto task) {
    return taskService.createTask(task);
  }

  @Operation(summary = "create many", description = "create many tasks")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created successfully", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class))) }) })
  @PostMapping("/create-many")
  public List<Task> createManyTasks(@RequestBody List<CreateTaskDto> tasks) {
    return taskService.createMany(tasks);
  }

  @Operation(summary = "update task", description = "update task information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated successfully", content = { @Content }) })
  @PutMapping(path = "{taskId}")
  public Task updateTask(
      @PathVariable("taskId") Long taskId,
      @RequestBody UpdateTaskDto task) {
    return taskService.updateTask(taskId, task);
  }

  @Operation(summary = "delete task", description = "delete task by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted successfully", content = { @Content }) })
  @DeleteMapping(path = "{taskId}")
  public void deleteTask(@PathVariable("taskId") Long taskId) {
    taskService.deleteTask(taskId);
  }

  @Operation(summary = "add task to category", description = "add task to existing category or create new")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Task added to category", content = { @Content }) })
  @PutMapping("/{taskId}/categories/{categoryName}")
  public void assignCategoryToTask(@PathVariable String categoryName, @PathVariable Long taskId) {
    taskService.assignCategoryToTask(taskId, categoryName);
  }

  @ExceptionHandler({ TaskNotFoundException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNFException(Exception ex) {
    return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler({ DateParseException.class, TaskNameTaken.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleBRException(Exception ex) {
    return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
  }
}
