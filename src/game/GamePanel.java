package game;

// para renderizar en pantalla
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;



public class GamePanel extends JPanel {
    // atributo de la matriz a renderizar en pantalla
    private int[][] matrix;
    
    // grid de booleans para saber cuales numeros ya se usaron.
    private boolean[][] usados;



    // componentes de la ui
    private int targetNum;
    private int currentNum;
    private String operacionActual = "+"; // default = +
    private final int paddingUI = 10;
    private int Nivel = 1;


    // constructor
    public GamePanel(){
        // por ahora el tam del panel es estatico
        setPreferredSize(new Dimension(800, 600));

        // generamos la matriz
        // por ahora se generara de 4x4 con numeros desde el 1 al 10, pero incrementa en base al nivel
        matrix = MatrixGenerator.generateMatrix(4, 1, 10);
        usados = new boolean[matrix.length][matrix[0].length];

        // inicializar el total a adivinar y el total acumulado
        targetNum = MatrixGenerator.generateMatrix(1, 20, 50)[0][0]; // codigo fancy para generar un numero random de 20 a 50
        currentNum = 0;

        // listener para manejar clicks

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                enClick(e.getX(), e.getY());
            }
        });
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // calculamos dimensiones en base a la matriz
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // definimos el espacio para la UI de arriba
        int espacioUI = (int) (panelHeight * 0.1);

        int tamGrid = Math.min(panelWidth / matrix.length, (panelHeight - espacioUI - paddingUI) / matrix[0].length);


        // render del total a adivinar y el total acumulado
        g.drawString("Objetivo: " + targetNum, 10, 20);
        g.drawString("Actual: " + currentNum, 10, 40);
        g.drawString("Operacion: " + operacionActual, 10, 60);
        g.drawString("Nivel: " + Nivel, 10, 80);

        // padding matriz
        int gridY = espacioUI + paddingUI;

        // render de la matriz
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                int num = matrix[i][j];

                // colorear las celdas usadas gris TODO - deshabilitar las celdas usadas
                if(usados[i][j]){
                    g.setColor(Color.lightGray);
                }else{
                    g.setColor(Color.white);
                }

                int x = j * tamGrid;
                int y = (i * tamGrid) + gridY;

                g.fillRect(x, y, tamGrid, tamGrid);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, tamGrid, tamGrid);
                g.drawString(Integer.toString(num), x + tamGrid / 3, y + 2 * tamGrid / 3);
            }
        }


    }

    private void enClick(int mouseX, int mouseY){
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int espacioUI = (int) (panelHeight * 0.1);
        int tamGrid = Math.min(panelWidth / matrix.length, (panelHeight - espacioUI - paddingUI) / matrix[0].length);
       
        // ajustar padding
        int gridY = espacioUI + paddingUI;

        // calcular la celda en la que se hizo click
        int celdaX = mouseX / tamGrid;
        int celdaY = (mouseY - gridY) / tamGrid;

        if( celdaY >= 0 && celdaY < matrix.length && celdaX >= 0 && celdaX < matrix[0].length){
            // confirmar que no este usada
            if(!usados[celdaY][celdaX]){
            // marcar la celda como usada y capturar el numero
            usados[celdaY][celdaX] = true;
            int numeroClick = matrix[celdaY][celdaX];

            // si la celda esta dentro de la matriz, realizar la operacion con el numero de la celda y el total actual
            switch(operacionActual){
                case "+": currentNum += numeroClick; repaint(); break;
                case "-": currentNum -= numeroClick; repaint(); break;
                case "*": currentNum *= numeroClick; repaint(); break;
                case "/": if(currentNum != 0 && numeroClick != 0){currentNum /= numeroClick;} break;
                default: break;
            }
            repaint();
        }
    }


        // si el total actual es igual al total a adivinar, se gana el juego, se avanza el nivel, y se regenera el board.
        if(currentNum == targetNum){
            System.out.println("Ganaste!");
            Nivel++;
            targetNum = MatrixGenerator.generateMatrix(1, 20*Nivel, 50*Nivel)[0][0];
            currentNum = 0;
            matrix = MatrixGenerator.generateMatrix(4+Nivel, 1, 10*(Nivel/2));
            usados = new boolean[matrix.length][matrix[0].length];
            repaint();
        }
    }

    public void setOperacionActual(String operacion){
        operacionActual = operacion;
        repaint();
    }

    public void refresh(){
        currentNum = 0;
        usados = new boolean[matrix.length][matrix[0].length];
        repaint();
    }

}
