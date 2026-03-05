import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    void playerInitializedHasCorrectValues(){
        String name = "Name";
        char sign = 'N';
        int expectedBalance = 2000;
        Player player = new Player(name,sign);

        assertEquals(name, player.getName());
        assertEquals(sign,player.getSign());
        assertEquals(expectedBalance, player.getBalance());
    }

    @Test
    void playerIsBroke(){
        String name = "Name";
        char sign = 'N';
        int toll = 2001;
        Player player = new Player(name, sign);
        player.payMoney(toll);

        assertEquals(true, player.isBroke());

    }
}
