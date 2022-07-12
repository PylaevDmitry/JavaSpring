package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;
import ru.pylaev.toDoProject.presentLayer.view.ViewToJScrollPaneWriter;

import javax.swing.*;
import java.util.List;

@Component
public class WindowUserInterface extends UserInterfaceBase {
    private static class MainWindow extends JFrame {
        public MainWindow() {
            setTitle("TODO");
            setDefaultCloseOperation((WindowConstants.DISPOSE_ON_CLOSE));
            setBounds(300, 300, 900, 400);
            setVisible(true);
        }
    }

    private final JTextField textField = new JTextField(72);
    private final JPanel panel = new JPanel();
    private MainWindow mainWindow;
    private List<Task> tasks;

    @Autowired
    public WindowUserInterface(View view, TaskRepository taskRepository) {
        super(view, taskRepository);
    }

    @Override
    public void showStartView() {
        mainWindow = new MainWindow();
        mainWindow.add(panel);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e -> {
            tasks = ViewHandler.processUserInput(textField.getText(), view, taskRepository);
            refreshPanel();
            textField.setText("");
        });
        refreshPanel();
    }

    @Override
    public void processUserInput() {
        try {
            Thread.sleep(250);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void refreshPanel() {
        panel.removeAll();
        panel.add(ViewToJScrollPaneWriter.write(view, tasks));
        panel.add(textField);
        panel.repaint();
        mainWindow.setVisible(true);
    }
}
