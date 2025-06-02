package frontend;

import backend.Item;
import backend.TestManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestPanel extends JPanel {
    private final TestManager testManager;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final JButton btnAnterior;
    private final JButton btnSiguiente;
    private int currentIndex = 0;
    private final List<Item> items;

    public TestPanel(TestManager testManager, Runnable onFinishTest) {
        this.testManager = testManager;
        this.items = testManager.getItems();
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        
        for (int i = 0; i < items.size(); i++) { // una vista por cada item
            cardPanel.add(createItemPanel(i), "item" + i);
        }

        add(cardPanel, BorderLayout.CENTER);

        JPanel navPanel = new JPanel();
        btnAnterior = new JButton("Atrás");
        btnSiguiente = new JButton("Siguiente");

        btnAnterior.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                cardLayout.show(cardPanel, "item" + currentIndex);
                updateButtons();
            }
        });

        btnSiguiente.addActionListener(e ->    {
            if (currentIndex < items.size() - 1) {
                currentIndex++;
                cardLayout.show(cardPanel, "item" + currentIndex);
                updateButtons();
            } else {
                // utimo ítem a resp
                onFinishTest.run(); 
            }
        });

        navPanel.add(btnAnterior);
        navPanel.add(btnSiguiente);

        add(navPanel, BorderLayout.SOUTH);
        updateButtons();
    }

    private JPanel createItemPanel(int index) {
        Item item = items.get(index);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        cardLayout.show(cardPanel, "item0");
        JLabel enunciado = new JLabel("<html><h3>" + item.getEnunciado() + "</h3></html>");
        panel.add(enunciado, BorderLayout.NORTH);

        ButtonGroup grupoOpciones = new ButtonGroup();
        JPanel opcionesPanel = new JPanel(new GridLayout(0, 1));

        if (item.getTipo().equalsIgnoreCase("verdadero_falso")) {
    String[] opcionesVF = {"Verdadero", "Falso"};
    for (String opcion : opcionesVF) {
        JRadioButton rb = new JRadioButton(opcion);
        grupoOpciones.add(rb);
        opcionesPanel.add(rb);

        if (opcion.equals(item.getRespuestaUsuario())) {
            rb.setSelected(true);
        }

        rb.addActionListener(e -> testManager.setRespuesta(index, opcion));
    }
} else {
    for (String opcion : item.getOpciones()) {
        JRadioButton rb = new JRadioButton(opcion);
        grupoOpciones.add(rb);
        opcionesPanel.add(rb);

        if (opcion.equals(item.getRespuestaUsuario())) {
            rb.setSelected(true);
        }

        rb.addActionListener(e -> testManager.setRespuesta(index, opcion));
    }
}


        panel.add(opcionesPanel, BorderLayout.CENTER);

        return panel;
    }

    private void updateButtons() {
        btnAnterior.setEnabled(currentIndex > 0);
        if (currentIndex == items.size() - 1) {
            btnSiguiente.setText("Enviar respuestas");
        } else {
            btnSiguiente.setText("Siguiente");
        }
    }
}
