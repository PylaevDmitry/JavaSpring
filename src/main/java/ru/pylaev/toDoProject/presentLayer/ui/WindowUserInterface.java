package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;
import ru.pylaev.toDoProject.presentLayer.view.ViewToJScrollPaneWriter;

import javax.swing.*;

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
    private final MainWindow mainWindow = new MainWindow();

    @Autowired
    public WindowUserInterface(View view, UserInputService userInputService) {
        super(view, userInputService);
    }

    @Override
    public void showStartView() {
        mainWindow.add(panel);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e -> {
            ViewHandler.processUserInput(textField.getText(), view, userInputService);
            refreshPanel();
            textField.setText("");
        });
        refreshPanel();
    }

    @Override
    public void processUserActions() {
        try {
            Thread.sleep(250);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void refreshPanel() {
        panel.removeAll();
        panel.add(ViewToJScrollPaneWriter.write(view));
        panel.add(textField);
        panel.repaint();
        mainWindow.setVisible(true);
    }
}
