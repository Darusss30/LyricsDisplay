import javax.swing.*;
import java.awt.*;

public class LyricsDisplay extends JPanel {
    private static final String[] lyrics = {
            "Karena kamu cantik",
            "Kan kuberi segalanya apa yang kupunya",
            "Dan hatimu baik",
            "Sempurnalah duniaku saat kau di sisiku",
            "Bukan karna make up di wajahmu",
            "Atau lipstik merah itu",
            "Lembut hati tutur kata",
            "Terciptalah cinta yang kupuja"
    };

    private static final int[] delays = {400, 300, 900, 10, 400, 800, 400, 5000};
    private static final int[] speeds = {90, 90, 115, 120, 90, 105, 100, 95};

    private int currentIndex = 0;
    private String currentLine = "";
    private int currentCharIndex = 0;
    private boolean isLineCompleted = false;
    private JLabel backgroundGif;
    private JLabel textLabel;
    private Timer charTimer;
    private Timer lineTimer;

    public LyricsDisplay() {
        ImageIcon icon = new ImageIcon("space2.gif");
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

                setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());

                backgroundGif = new JLabel(icon);
        backgroundGif.setLayout(new BorderLayout());
        add(backgroundGif, BorderLayout.CENTER);

        textLabel = new JLabel("", SwingConstants.CENTER);
        textLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setOpaque(false);

        backgroundGif.add(textLabel, BorderLayout.CENTER);
        
        charTimer = new Timer(speeds[currentIndex], e -> animateCharacter());

        lineTimer = new Timer(delays[currentIndex], e -> {
            if (currentIndex < lyrics.length - 1) {
                currentIndex++;
                currentLine = "";
                currentCharIndex = 0;
                textLabel.setText("");
                isLineCompleted = false;
                charTimer.setDelay(speeds[currentIndex]);
                charTimer.start();
            } else {
                lineTimer.stop();
                charTimer.stop();
            }
        });
        lineTimer.setRepeats(false);

        charTimer.start();
    }

    private void animateCharacter() {
        if (currentIndex < lyrics.length) {
            if (currentCharIndex < lyrics[currentIndex].length()) {
                currentLine += lyrics[currentIndex].charAt(currentCharIndex);
                textLabel.setText(currentLine);
                currentCharIndex++;
            } else if (!isLineCompleted) {
                isLineCompleted = true;
                charTimer.stop();
                lineTimer.setInitialDelay(delays[currentIndex]);
                lineTimer.restart();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lyrics Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new LyricsDisplay());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
