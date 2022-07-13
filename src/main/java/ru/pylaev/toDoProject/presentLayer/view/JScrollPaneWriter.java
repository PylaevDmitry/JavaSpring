package ru.pylaev.toDoProject.presentLayer.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JScrollPaneWriter {
    public static JScrollPane write (String message, String[] tasks) {
        List<String> list = new ArrayList<>();
        if (Objects.nonNull(tasks)) {
            list.addAll(Arrays.stream(tasks).toList());
        }
        list.add(message);
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (int i = list.size()-1; i >=0 ; i--) {
            dlm.add(0, list.get(i));
        }
        JList<String> jList = new JList<>(dlm);
        jList.setFixedCellWidth(790);
        return new JScrollPane(jList);
    }
}
