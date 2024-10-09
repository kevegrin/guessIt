package game;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
    private JFrame window;
    private GamePanel panel;

    public Game(){
        initializeWindow();
    }

    private void initializeWindow(){
        // render de la ventana
        window = new JFrame ("Guess It!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        //render del panel de juego

        panel = new GamePanel();
        window.add(panel, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();

        window.add(controlPanel, BorderLayout.SOUTH);
        
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private JPanel createControlPanel(){
        // panel de control
        JPanel controlPanel = new JPanel();

        // botones para operaciones
        JButton botonSuma = new JButton("+");
        JButton botonResta = new JButton("-");
        JButton botonMultiplicacion = new JButton("*");
        JButton botonDivision = new JButton("/");
        JButton botonRefresh = new JButton("Refresh");

        
        // listeners para los botones
        botonSuma.addActionListener(e -> panel.setOperacionActual("+"));
        botonResta.addActionListener(e -> panel.setOperacionActual("-"));
        botonMultiplicacion.addActionListener(e -> panel.setOperacionActual("*"));
        botonDivision.addActionListener(e -> panel.setOperacionActual("/"));
        botonRefresh.addActionListener(e -> panel.refresh());

        // agregar los botones al panel de control
        controlPanel.add(botonSuma);
        controlPanel.add(botonResta);
        controlPanel.add(botonMultiplicacion);
        controlPanel.add(botonDivision);
        controlPanel.add(botonRefresh);

        return controlPanel;
    }

        public static void main(String[] args) {
            new Game();
        }
}
