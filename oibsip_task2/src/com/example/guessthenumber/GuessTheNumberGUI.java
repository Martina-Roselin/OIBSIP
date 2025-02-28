package com.example.guessthenumber;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame implements ActionListener {
    private int randomNumber;
    private int attempts;
    private JTextField guessField;
    private JButton guessButton;
    private JButton resetButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel titleLabel;
    
    public GuessTheNumberGUI() {
        super("Guess The Number");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout(10, 10));
        
        // Top Panel: Title with solid dark background and custom border
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(44, 62, 80)); // Dark blue-gray
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));
        titleLabel = new JLabel("Guess The Number");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(236, 240, 241)); // Light gray
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel: Uses BoxLayout (Y_AXIS) with two sections:
        //   1. A prompt panel for the guess input.
        //   2. A button panel with the Guess button.
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(52, 73, 94)); // Dark slate blue
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Prompt Section Panel
        JPanel promptPanel = new JPanel();
        promptPanel.setLayout(new BoxLayout(promptPanel, BoxLayout.X_AXIS));
        promptPanel.setOpaque(false);
        JLabel promptLabel = new JLabel("Enter your guess (1-100): ");
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        promptLabel.setForeground(new Color(236, 240, 241));
        guessField = new JTextField(6);
        guessField.setFont(new Font("SansSerif", Font.PLAIN, 24));
        guessField.setMaximumSize(new Dimension(100, 40));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        promptPanel.add(promptLabel);
        promptPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        promptPanel.add(guessField);
        
        // Button Section Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        guessButton.setBackground(new Color(39, 174, 96)); // Vibrant green
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);
        guessButton.addActionListener(this);
        buttonPanel.add(guessButton);
        
        // Add sections to center panel with spacing
        centerPanel.add(promptPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(buttonPanel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom Panel: Displays feedback, attempts, and a reset button.
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(44, 62, 80));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(new LineBorder(new Color(236, 240, 241), 2, true));
        
        feedbackLabel = new JLabel("Make your guess!", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        feedbackLabel.setForeground(new Color(241, 196, 15)); // Golden yellow
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        attemptsLabel = new JLabel("Attempts: 0 / 5", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        attemptsLabel.setForeground(new Color(236, 240, 241));
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        resetButton.setBackground(new Color(192, 57, 43)); // Bold red
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> resetGame());
        resetButton.setVisible(false);
        
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(feedbackLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(attemptsLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(resetButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 100));
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        resetGame();
        setVisible(true);
    }
    
    // Resets the game state
    private void resetGame() {
        randomNumber = new Random().nextInt(100) + 1;
        attempts = 0;
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        resetButton.setVisible(false);
        feedbackLabel.setText("Make your guess!");
        attemptsLabel.setText("Attempts: 0 / 5");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = guessField.getText();
        try {
            int guess = Integer.parseInt(input);
            attempts++;
            if (guess < randomNumber) {
                feedbackLabel.setText("Too low! Try again.");
            } else if (guess > randomNumber) {
                feedbackLabel.setText("Too high! Try again.");
            } else {
                feedbackLabel.setText("Correct! You win!");
                guessButton.setEnabled(false);
                guessField.setEditable(false);
                resetButton.setVisible(true);
            }
            attemptsLabel.setText("Attempts: " + attempts + " / 5");
            if (attempts >= 5 && guess != randomNumber) {
                feedbackLabel.setText("Game Over! The number was " + randomNumber);
                guessButton.setEnabled(false);
                guessField.setEditable(false);
                resetButton.setVisible(true);
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number!");
        }
    }
    
    public static void main(String[] args) {
        try {
            // Set Nimbus look and feel if available
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fall back to default look and feel if Nimbus isn't available
        }
        SwingUtilities.invokeLater(() -> new GuessTheNumberGUI());
    }
}
