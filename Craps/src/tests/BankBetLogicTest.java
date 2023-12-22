package tests;

import model.BankBetLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankBetLogicTest {
    private BankBetLogic myBank;

    @BeforeEach
    void setup() {
        myBank = new BankBetLogic();
        myBank.setBankAmount(10000);
    }

    @Test
    void adjustBet() {
        myBank.adjustBet(100);
        assertEquals(100, myBank.getBetAmount());
    }

    @Test
    void adjustBank() {
        myBank.adjustBank(2000);
        assertEquals(8000, myBank.getBankAmount());
    }

    @Test
    void resetBankAndBet() {
        myBank.resetBankAndBet();
        assertEquals(0, myBank.getBankAmount());
        assertEquals(0, myBank.getBetAmount());
    }

    @Test
    void setBetAmount() {
        myBank.setBetAmount(1000);
        assertEquals(1000, myBank.getBetAmount());
        assertEquals(9000, myBank.getBankAmount());
    }

    @Test
    void setBetAmountNegativeOrZero() {
        assertThrows(IllegalArgumentException.class, () -> myBank.setBetAmount(-1000));
        assertThrows(IllegalArgumentException.class, () -> myBank.setBetAmount(0));
    }

    @Test
    void setBetAmountMoreThanBankAmount() {
        assertThrows(IllegalArgumentException.class, () -> myBank.setBetAmount(2000000));
    }

    @Test
    void setBetAmountEqualToBank() {
        myBank.setBetAmount(10000);
        assertEquals(10000, myBank.getBetAmount());
    }

    @Test
    void setBankAmount() {
        myBank.setBankAmount(10);
        assertEquals(10, myBank.getBankAmount());
    }

    @Test
    void setBankAmountToZero() {
        assertThrows(IllegalArgumentException.class, () -> myBank.setBankAmount(0));
    }

    @Test
    void setBankAmountNegative() {
        assertThrows(IllegalArgumentException.class, () -> myBank.setBankAmount(-9999));
    }
}