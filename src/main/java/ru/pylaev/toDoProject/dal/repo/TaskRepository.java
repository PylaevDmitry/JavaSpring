package ru.pylaev.toDoProject.dal.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pylaev.toDoProject.dal.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
