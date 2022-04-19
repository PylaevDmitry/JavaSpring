package ru.pylaev.toDoProject.dal;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByOwner(String owner);
}
