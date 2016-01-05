import java.util.Random;

public class RandomInt {
    static Random r = new Random();
    static int getRandomInt (int multiplier){
        while (true){
            int x = Math.round (r.nextFloat() * multiplier);
            if (x == 0 ) continue;
            return x;
        }
    }
}
