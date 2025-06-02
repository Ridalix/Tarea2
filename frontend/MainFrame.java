package frontend;

import backend.TestManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {

    private final TestManager testManager;

    public MainFrame() {
        super("Administrador de Pruebas - Taxonomía de Bloom");

        this.testManager = new TestManager();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mostrarPantallaCarga();
    }

    public void mostrarPantallaCarga() {
        JPanel panelCarga = new JPanel(new BorderLayout());

        JButton cargarBtn = new JButton("Cargar archivo de ítems");
        panelCarga.add(cargarBtn, BorderLayout.CENTER);

        cargarBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File archivo = chooser.getSelectedFile();
                try {
                    testManager.cargarItemsDesdeArchivo(archivo);
                    JOptionPane.showMessageDialog(this, "Ítems cargados correctamente.");
                    mostrarResumenCarga();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar el archivo:\n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setContentPane(panelCarga);
        revalidate();
        repaint();
    }

    private void mostrarResumenCarga() {
        JPanel resumenPanel = new JPanel(new BorderLayout());

        JLabel info = new JLabel("Ítems cargados: " + testManager.getItems().size() +
                " | Tiempo estimado total: " + testManager.calcularTiempoTotal() + " min");
        info.setHorizontalAlignment(SwingConstants.CENTER);

        JButton iniciarBtn = new JButton("Iniciar prueba");

        iniciarBtn.addActionListener(e -> {
            TestPanel testPanel = new TestPanel(testManager, () -> {
            ResumePanel resumePanel = new ResumePanel(testManager, () -> mostrarPantallaRevision());
            setContentPane(resumePanel);
            revalidate();
            repaint();
        });


            setContentPane(testPanel);
            revalidate();
            repaint();
        });

        resumenPanel.add(info, BorderLayout.CENTER);
        resumenPanel.add(iniciarBtn, BorderLayout.SOUTH);

        setContentPane(resumenPanel);
        revalidate();
        repaint();
    }
    private void mostrarPantallaRevision() {
    RevisionPanel revisionPanel = new RevisionPanel(testManager);
    setContentPane(new JScrollPane(revisionPanel));
    revalidate();
    repaint();
}

}
