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
            setDefaultCloseOperation((WindowConstants.DO_NOTHING_ON_CLOSE));
            setBounds(300, 300, 900, 400);
            add(field);
            setVisible(true);
        }
    }

    private final JPanel field = new JPanel();
    private final MainWindow mainWindow = new MainWindow();

    private volatile boolean running = true;
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
            if (userInput[0].equals("EXIT")) {
                mainWindow.dispose();
                running = false;
            }
            userInputEnds = true;
            textField.setText("");
        });

        List<String> initList = new ArrayList<>();
        initList.add(view.getMessage());
        show(field, initList);
        while (running) {
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
            List<String> list = new ArrayList<>();
            if (!Objects.isNull(view.getArrTasks())) {
                list.addAll(List.of(view.getArrTasks()));
            }
            list.add(view.getMessage());
            this.show(field,list);
            userInputEnds = false;
        }
    }

    private void show (JPanel field,List<String> bufList) {
        field.removeAll();
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (int i = bufList.size()-1; i >=0 ; i--) {
            dlm.add(0, bufList.get(i));
        }
        JList<String> jList = new JList<>(dlm);
        jList.setFixedCellWidth(790);
        field.add(new JScrollPane(jList));
    }
}
