package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BunTest {
    Bun bun;
    String bunName = "White Bun";
    float price = 10.5f;

    @BeforeEach
    void setup(){
        bun = new Bun(bunName, price);
    }

    @Test
    void getNameReturnCorrectBunName() {
        assertEquals(bunName, bun.getName());
    }

    @Test
    void getPriceReturnCorrectBunPrice() {
        assertEquals(price, bun.getPrice());
    }
}