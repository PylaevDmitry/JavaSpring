package ru.pylaev.toDoProject.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataBaseTaskDAO implements DAO {
    private final TaskRepository taskRepository;

    @Autowired
    public DataBaseTaskDAO(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findByOwner(String owner) {
        return taskRepository.findByOwner(owner);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(long id) {
        return taskRepository.findById(id);
    }
}
