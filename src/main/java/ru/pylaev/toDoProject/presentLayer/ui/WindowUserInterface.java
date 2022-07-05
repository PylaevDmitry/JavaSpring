package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;
import ru.pylaev.toDoProject.presentLayer.view.ViewToJScrollPaneWriter;

import javax.swing.*;

@Component
public class WindowUserInterface extends UserInterface {
    private class MainWindow extends JFrame {
        public MainWindow() {
            setTitle("TODO");
            setDefaultCloseOperation((WindowConstants.DISPOSE_ON_CLOSE));
            setBounds(300, 300, 900, 400);
            add(panel);
            setVisible(true);
        }
    }

    private final JTextField textField = new JTextField(72);
    private final JPanel panel = new JPanel();
    private final MainWindow mainWindow = new MainWindow();
    
    public WindowUserInterface(View view, UserInputService userInputService) {
        super(view, userInputService);
    }

    @Override
    public void run () {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e -> {
            processTextField(textField);
            textField.setText("");
        });

        processTextField(textField);

        try {while (true) {Thread.sleep(250);}}
        catch (InterruptedException e) {e.printStackTrace();}
    }

    private void processTextField(JTextField textField) {
        ViewHandler.processUserInput(textField.getText(), view, userInputService);

        panel.removeAll();
        panel.add(ViewToJScrollPaneWriter.write(view));
        panel.add(textField);
        panel.repaint();
        mainWindow.setVisible(true);
    }
}
