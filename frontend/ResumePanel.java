package frontend;

import backend.TestManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResumePanel extends JPanel {

    public ResumePanel(TestManager testManager, Runnable onRevisar) {
        setLayout(new BorderLayout());

        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        StringBuilder sb = new StringBuilder("Resultados:\n");

        sb.append("\n➤ Por nivel de la taxonomía:\n");
        for (Map.Entry<String, Double> entry : testManager.calcularPorcentajePorNivel().entrySet()) {
            sb.append(String.format("   %s: %.2f%%\n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n➤ Por tipo de ítem:\n");
        for (Map.Entry<String, Double> entry : testManager.calcularPorcentajePorTipo().entrySet()) {
            sb.append(String.format("   %s: %.2f%%\n", entry.getKey(), entry.getValue()));
        }

        resultado.setText(sb.toString());

        JButton btnRevisar = new JButton("Revisar respuestas");
        btnRevisar.addActionListener(e -> onRevisar.run());

        add(new JScrollPane(resultado), BorderLayout.CENTER);
        add(btnRevisar, BorderLayout.SOUTH);
    }
}
