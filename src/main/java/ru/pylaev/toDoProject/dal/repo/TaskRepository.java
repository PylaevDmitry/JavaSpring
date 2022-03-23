package ru.pylaev.toDoProject.dal.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByOwner(String owner);
}
