package com.example.lab6.Task;

import com.example.lab6.Category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;


    private Date deadline;
    private Integer priority;
    private  Boolean done;

    public Boolean getDone() {
        return done;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public void setDone(Boolean isDone) {
        done = isDone;
    }

    public Long getId() {
        return id;
    }

    public Task() {}

    public Task(Long id, String name, Date deadline, Integer priority, Boolean done) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.done = done;
    }

    public Task(String name, Date deadline, Integer priority, Boolean done) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String taskName) {
        this.name = taskName;
    }

    public Date getDeadline() { return deadline; }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
