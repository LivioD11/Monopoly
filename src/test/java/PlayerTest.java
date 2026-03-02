import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    void playerInitializedHasCorrectValues(){
        String name = "Name";
        char sign = 'N';
        int expectedBalance = Config.getInt("bank.contribution",0);
        Player player = new Player(name,sign);

        assertEquals(name, player.getName());
        assertEquals(sign,player.getSign());
        assertEquals(expectedBalance, player.getBalance());
    }

    @Test
    void playerIsBroke(){
        String name = "Name";
        char sign = 'N';
        int toll = Config.getInt("bank.contribution",0) + 1;
        Player player = new Player(name, sign);
        player.changeBalance(-toll);

        assertEquals(true, player.isBroke());

    }
}
