package ru.pylaev.toDoProject.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pylaev.toDoProject.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
