package ru.pylaev.toDoProject.dataAccessLayer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DAO {
    List<Task> findByOwner(String owner);
    void save (Task task);
    Optional<Task> findById(long id);
}
