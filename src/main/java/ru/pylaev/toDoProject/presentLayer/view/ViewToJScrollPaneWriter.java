package ru.pylaev.toDoProject.presentLayer.view;

import ru.pylaev.toDoProject.dataAccessLayer.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewToJScrollPaneWriter {
    public static JScrollPane write (View view, List<Task> tasks) {
        List<String> list = new ArrayList<>();
        if (!Objects.isNull(view.getTasks())) {
            list.addAll((tasks.stream().map(Task::toString).toList()));
        }
        list.add(view.getMessage().toString());
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (int i = list.size()-1; i >=0 ; i--) {
            dlm.add(0, list.get(i));
        }
        JList<String> jList = new JList<>(dlm);
        jList.setFixedCellWidth(790);
        return new JScrollPane(jList);
    }
}
