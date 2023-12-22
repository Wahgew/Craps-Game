package tests;

import model.CrapsLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrapsLogicTest {

    private CrapsLogic myCraps;

    @BeforeEach
    void setup() {
        myCraps = new CrapsLogic();
    }

    @Test
    void testRoll() {
        myCraps.roll();
        // simulate 1000 random rolls
        for (int i = 0; i < 1000; i++) {
            int dice1 = myCraps.getDice1();
            int dice2 = myCraps.getDice2();
            assertTrue(dice1 >= 1 && dice1 <= 6);
            assertTrue(dice2 >= 1 && dice2 <= 6);
        }
    }

    @Test
    void testSetPoint() {
        myCraps.setPoint(10);
        assertEquals(10, myCraps.getPoint());
    }

    @Test
    void testSetTotal() {
        myCraps.setTotal(7);
        assertEquals(7, myCraps.getTotal());
    }

    @Test
    void testSetPlayerWins() {
        myCraps.setPlayerWins(21);
        assertEquals(21, myCraps.getPlayerWins());
    }

    @Test
    void testSetHouseWins() {
        myCraps.setHouseWins(200);
        assertEquals(200, myCraps.getHouseWins());
    }

    @Test
    void testSetDie1() {
        myCraps.setDie1(4);
        assertEquals(4, myCraps.getDice1());
    }

    @Test
    void testSetDie2() {
        myCraps.setDie2(1);
        assertEquals(1, myCraps.getDice2());
    }

    @Test
    void testGetGameWonFirstRollSeven() {
        // simulate first roll is 7
        myCraps.setDie1(1);
        myCraps.setDie1(6);
        myCraps.setWins(true);
        assertTrue(myCraps.getGameWon());
    }

    @Test
    void testGetGameWonFirstRollEleven() {
        // simulate first roll is 11
        myCraps.setDie1(6);
        myCraps.setDie1(5);
        myCraps.setWins(true);
        assertTrue(myCraps.getGameWon());
    }

    @Test
    void testGetGameLost() {
        // simulate first roll is 2
        myCraps.setDie1(1);
        myCraps.setDie1(1);
        myCraps.setWins(false);
        assertFalse(myCraps.getGameWon());
    }

    @Test
    void testGetGameLostOnTwelve() {
        // simulate first roll is 12
        myCraps.setDie1(6);
        myCraps.setDie1(6);
        myCraps.setWins(false);
        assertFalse(myCraps.getGameWon());
    }

    @Test
    void testGetGameLostOnThree() {
        // simulate first roll is 3
        myCraps.setDie1(2);
        myCraps.setDie1(1);
        myCraps.setWins(false);
        assertFalse(myCraps.getGameWon());
    }

    @Test
    void testPlayGame() {
        myCraps.roll();
        int point = myCraps.getPoint();
        int total = myCraps.getTotal();

        while (true) {
            if (total  == 7 || total == 11) {
                assertTrue(myCraps.getGameWon());
                break; // Game won
            } else if (total  == 2 || total  == 3 || total  == 12) {
                assertFalse(myCraps.getGameWon());
                break; // Game lost
            } else {
                myCraps.roll();
                if (myCraps.getTotal() == point) {
                    assertTrue(myCraps.getGameWon());
                    break; // Game won
                } else if (myCraps.getTotal() == 7) {
                    assertFalse(myCraps.getGameWon());
                    break; // Game lost
                }
            }
        }
    }
}