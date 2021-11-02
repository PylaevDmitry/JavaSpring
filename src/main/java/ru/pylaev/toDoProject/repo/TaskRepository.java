package ru.pylaev.toDoProject.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pylaev.toDoProject.models.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
