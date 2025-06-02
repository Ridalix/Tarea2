package frontend;

import backend.Item;
import backend.TestManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RevisionPanel extends JPanel {
    public RevisionPanel(TestManager testManager) {
        setLayout(new GridLayout(0, 1));
        List<Item> items = testManager.getItems();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            JPanel panel = new JPanel(new BorderLayout());
            String estado = item.getRespuestaCorrecta().equalsIgnoreCase(item.getRespuestaUsuario()) ? "✅ Correcto" : "❌ Incorrecto";

            JLabel enunciado = new JLabel("<html><b>" + item.getEnunciado() + "</b> (" + estado + ")</html>");
            panel.add(enunciado, BorderLayout.NORTH);

            JTextArea info = new JTextArea();
            info.setEditable(false);
            info.setText("Tu respuesta: " + item.getRespuestaUsuario() + "\nRespuesta correcta: " + item.getRespuestaCorrecta());
            panel.add(info, BorderLayout.CENTER);

            add(panel);
        }
    }
}
