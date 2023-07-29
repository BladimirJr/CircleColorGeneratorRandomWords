/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWordGenerator extends JFrame {
    private List<String> words;
    private JButton generateButton;
    private JLabel wordLabel;
    private CirclePanel circlePanel;

    public RandomWordGenerator() {
        setTitle("Generador_Palabras");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        words = new ArrayList<>();
        loadWordsFromFile("words.txt"); // Load words from the file

        wordLabel = new JLabel("", JLabel.CENTER);
        generateButton = new JButton("Generar");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomWord();
                circlePanel.randomizeColor();
            }
        });

        circlePanel = new CirclePanel();
        circlePanel.setPreferredSize(new Dimension(100, 100)); // Set size for CirclePanel

        // Create a panel to hold the wordLabel and circlePanel side by side
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(circlePanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(generateButton, BorderLayout.SOUTH);
    }

    private void loadWordsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(line.trim());
                }
            }
        } catch (IOException e) {
        }
    }

    private void generateRandomWord() {
        if (words.isEmpty()) {
            wordLabel.setText("Palabras no disponibles.");
            return;
        }

        int randomIndex = new Random().nextInt(words.size());
        String randomWord = words.get(randomIndex);
        wordLabel.setText(randomWord);
    }

    private class CirclePanel extends JPanel {
        private Color circleColor;

        public CirclePanel() {
            circleColor = Color.RED;
        }

        public void randomizeColor() {
            int red = new Random().nextInt(256);
            int green = new Random().nextInt(256);
            int blue = new Random().nextInt(256);
            circleColor = new Color(red, green, blue);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(circleColor);
            int circleDiameter = Math.min(getWidth(), getHeight()) - 20;
            int x = (getWidth() - circleDiameter) / 2;
            int y = (getHeight() - circleDiameter) / 2;
            g.fillOval(x, y, circleDiameter, circleDiameter);

            // Draw the word in the center of the circle
            String word = wordLabel.getText();
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(word);
            int stringHeight = fm.getHeight();
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            g.setColor(Color.BLACK);
            g.drawString(word, centerX - stringWidth / 2, centerY + stringHeight / 4);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RandomWordGenerator window = new RandomWordGenerator();
                window.setVisible(true);
            }
        });
    }
}



