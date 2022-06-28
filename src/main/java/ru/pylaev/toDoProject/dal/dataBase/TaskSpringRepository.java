package ru.pylaev.toDoProject.dal.dataBase;

import org.springframework.data.repository.CrudRepository;
import ru.pylaev.toDoProject.dal.Task;

import java.util.List;

public interface TaskSpringRepository extends CrudRepository<Task, Long> {
    List<Task> findByOwner(String owner);
}
