package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class WindowUserInterface extends UserInterface {
    private class MainWindow extends JFrame {
        public MainWindow() {
            setTitle("TODO");
            setDefaultCloseOperation((WindowConstants.DISPOSE_ON_CLOSE));
            setBounds(300, 300, 900, 400);
            add(field);
            setVisible(true);
        }
    }

    private final JPanel field = new JPanel();
    private final MainWindow mainWindow = new MainWindow();

    private boolean userInputEnds;

    public WindowUserInterface(View view, UserInputService userInputService) {
        super(view, userInputService);
    }

    @Override
    public void run () {
        final String[] userInput = new String[1];

        JTextField textField = new JTextField(72);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e -> {
            userInput[0] = textField.getText();
            userInputEnds = true;
            textField.setText("");
        });

        while (true) {
            List<String> list = new ArrayList<>();
            if (!Objects.isNull(view.getArrTasks())) {
                list.addAll(List.of(view.getArrTasks()));
            }
            list.add(view.getMessage());
            field.removeAll();
            DefaultListModel<String> dlm = new DefaultListModel<>();
            for (int i = list.size()-1; i >=0 ; i--) {
                dlm.add(0, list.get(i));
            }
            JList<String> jList = new JList<>(dlm);
            jList.setFixedCellWidth(790);
            field.add(new JScrollPane(jList));
            userInputEnds = false;
            field.add(textField);
            field.repaint();
            mainWindow.setVisible(true);
            while (!userInputEnds) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ignored) {
                }
            }
            ViewHandler.processUserInput(userInput[0], view, userInputService);
        }
    }


}
