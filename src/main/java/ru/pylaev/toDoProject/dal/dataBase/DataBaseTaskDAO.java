package ru.pylaev.toDoProject.dal.dataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.dal.DAO;
import ru.pylaev.toDoProject.dal.Task;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
@Component
public class DataBaseTaskDAO implements DAO {
    private final TaskSpringRepository taskSpringRepository;

    @Autowired
    public DataBaseTaskDAO(TaskSpringRepository taskSpringRepository) {
        this.taskSpringRepository = taskSpringRepository;
    }

    @Override
    public List<Task> findByOwner(String owner) {
        return taskSpringRepository.findByOwner(owner);
    }

    @Override
    public void save(Task task) {
        taskSpringRepository.save(task);
    }

    @Override
    public Optional<Task> findById(long id) {
        return taskSpringRepository.findById(id);
    }
}
