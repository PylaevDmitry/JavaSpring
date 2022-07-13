package ru.pylaev.toDoProject.presentLayer.runUi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.businessLogicLayer.StateService;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

@Component
public class WindowUserInterface extends RunUI {
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

    @Autowired
    public WindowUserInterface(State state) {
        super(state);
    }

    @Override
    public void showStartView() {
        mainWindow = new MainWindow();
        mainWindow.add(panel);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e -> {
            view.setTasks(StateService.processUserInput(textField.getText(), state));
            view.setMessage(state.getStep().toString());
            refreshPanel();
            textField.setText("");
        });
        refreshPanel();
    }

    @Override
    public void processUserInput() {
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void refreshPanel() {
        panel.removeAll();
        panel.add(JScrollPaneWriter.write(view.getMessage(), view.getTasks()));
        panel.add(textField);
        panel.repaint();
        mainWindow.setVisible(true);
    }
}
