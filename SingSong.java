import java.util.ArrayList;
import java.util.List;

class AnimatedText {
    public static synchronized void animateText(String text, long delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}

class SingLyric implements Runnable {
    private final String lyric;
    private final long delay;
    private final long speed;

    public SingLyric(String lyric, long delay, long speed) {
        this.lyric = lyric;
        this.delay = delay;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        AnimatedText.animateText(lyric, speed);
    }
}

public class SingSong {
    public static void main(String[] args) {
        String[][] lyrics = {
            {"\nKarna kamu cantik", "90"},
            {"Kan kuberi segalanya apa yang kupunya", "90"},
            {"Dan hatimu baik", "100"},
            {"Sempurnalah duniaku saat kau di sisiku\n", "100"},
            {"Bukan karna make up di wajahmu", "90"},
            {"Atau lipstik merah itu", "80"},
            {"Terciptalah cinta yang kupuja\n", "100"}
        };
        
        long[] delays = {300, 3400, 7400, 10500, 14500, 18000, 21000};
        
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < lyrics.length; i++) {
            Thread thread = new Thread(new SingLyric(lyrics[i][0], delays[i], Long.parseLong(lyrics[i][1])));
            threads.add(thread);
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
