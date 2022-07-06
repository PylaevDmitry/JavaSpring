package ru.pylaev.toDoProject.presentLayer.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewToJScrollPaneWriter {
    public static JScrollPane write (View view) {
        List<String> list = new ArrayList<>();
        if (!Objects.isNull(view.getArrTasks())) {
            list.addAll(List.of(view.getArrTasks()));
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
