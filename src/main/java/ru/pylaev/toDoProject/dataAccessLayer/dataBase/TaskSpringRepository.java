package ru.pylaev.toDoProject.dataAccessLayer.dataBase;

import org.springframework.data.repository.CrudRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;

import java.util.List;

public interface TaskSpringRepository extends CrudRepository<Task, Long> {
    List<Task> findByOwner(String owner);
}
