package com.example.lab6.Category;

import com.example.lab6.Task.Task;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "CategoryRepository.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")
@Table
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;
    @Column(unique = true)
    private String name;


    @OneToMany(mappedBy = "category")
    private Set<Task> tasksInCategory = new HashSet<>();

    public Set<Task> getTasksInCategory() {
        return tasksInCategory;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {

    }

    @PreRemove
    private void preRemove() {
        for (Task t : tasksInCategory) {
            t.setCategory(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
