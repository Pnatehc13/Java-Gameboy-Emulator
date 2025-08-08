import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel {
    private static final int WIDTH = 160;
    private static final int HEIGHT = 144;
    private static final int SCALE = 3;

    private final BufferedImage image;

    public Screen() {
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void drawFrame(int[] framebuffer) {
        image.setRGB(0, 0, WIDTH, HEIGHT, framebuffer, 0, WIDTH);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
    }

    public static Screen createWindow(CPU cpu) {
        JFrame frame = new JFrame("Game Boy");
        Screen screen = new Screen();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(screen);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Focus needed for key events
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                handleKey(cpu, key, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                handleKey(cpu, key, false);
            }

            private void handleKey(CPU cpu, int keyCode, boolean pressed) {
                switch (keyCode) {
                    case KeyEvent.VK_Z: cpu.setKeyState(4, pressed); break; // A
                    case KeyEvent.VK_X: cpu.setKeyState(5, pressed); break; // B
                    case KeyEvent.VK_ENTER: cpu.setKeyState(7, pressed); break; // Start
                    case KeyEvent.VK_BACK_SPACE: cpu.setKeyState(6, pressed); break; // Select

                    case KeyEvent.VK_UP: cpu.setKeyState(2, pressed); break; // Up
                    case KeyEvent.VK_DOWN: cpu.setKeyState(3, pressed); break; // Down
                    case KeyEvent.VK_LEFT: cpu.setKeyState(1, pressed); break; // Left
                    case KeyEvent.VK_RIGHT: cpu.setKeyState(0, pressed); break; // Right
                }
            }
        });

        frame.requestFocus(); // Ensure key events are received
        return screen;
    }
}