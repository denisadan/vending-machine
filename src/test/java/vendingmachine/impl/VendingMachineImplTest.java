package vendingmachine.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import vendingmachine.exceptions.NotFullPaidException;
import vendingmachine.exceptions.NotSufficientChangeException;
import vendingmachine.exceptions.SoldOutException;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;
import vendingmachine.storage.CoinStorage;
import vendingmachine.storage.VendingStorage;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static vendingmachine.model.Coin.*;

@RunWith(MockitoJUnitRunner.class)
public class VendingMachineImplTest {
    VendingMachineImpl vendingMachine = new VendingMachineImpl();
    VendingStorage storage = new VendingStorage();
    CoinStorage coinStorage = new CoinStorage();
    HashMap<Integer, Queue<Item>> storageMap = new HashMap<>();

    @Before
    public void initFields() {
        Queue<Item> cokeQueue = new ArrayDeque<>();
        Item coke = new Item("Coca-Cola", 20);
        cokeQueue.add(coke);
        cokeQueue.add(coke);
        cokeQueue.add(coke);
        cokeQueue.add(coke);
        cokeQueue.add(coke);

        Queue<Item> chocolateQueue = new ArrayDeque<>();
        Item chocolate = new Item("Milka", 50);
        chocolateQueue.add(chocolate);
        chocolateQueue.add(chocolate);

        Queue<Item> waterQueue = new ArrayDeque<>();
        Item water = new Item("Water", 10);
        waterQueue.add(water);

        storageMap.put(10, chocolateQueue);
        storageMap.put(11, cokeQueue);
        storageMap.put(12, new ArrayDeque<>());
        storageMap.put(13, null);
        storageMap.put(14, waterQueue);
        storage.setStorageMap(storageMap);
        vendingMachine.setStorage(storage);

        coinStorage.setQuarterStash(vendingMachine.addCoinsToStash(8, QUARTER_25));
        coinStorage.setDimeStash(vendingMachine.addCoinsToStash(8, DIME_10));
        coinStorage.setNickelStash(vendingMachine.addCoinsToStash(8, NICKEL_5));
        coinStorage.setPennyStash(vendingMachine.addCoinsToStash(30, PENNY_1));
        vendingMachine.setCoinStorage(coinStorage);

    }

    @Test
    public void test_previewItem() {
        assertEquals(new Item("Water", 10), vendingMachine.previewItem(14));

        assertThrows(SoldOutException.class, () -> vendingMachine.previewItem(12));

        assertThrows(SoldOutException.class, () -> vendingMachine.previewItem(13));

    }

    @Test
    public void test_payItem() {
        Queue<Coin> amountIntroduced = introduceMoney(4, 5, 10, 5);
        int amountAsInt = vendingMachine.getAmountIntroducedAsInt(amountIntroduced);
        assertEquals(205, amountAsInt);

        assertEquals(5, vendingMachine.payItem(200, amountIntroduced));
        assertEquals(0, vendingMachine.payItem(205, amountIntroduced));
        assertEquals(0, vendingMachine.payItem(205, amountIntroduced));

        assertThrows(NotFullPaidException.class, () -> vendingMachine.payItem(300, amountIntroduced));
    }

    @Test
    public void test_getChangeInCoins() {
        assertEquals(350, coinStorage.getTotalCoinsInStorage());
        assertThrows(NotSufficientChangeException.class, () -> vendingMachine.getChangeInCoins(400));

        Queue<Coin> changeInCoins = vendingMachine.getChangeInCoins(250);
        assertEquals(250, vendingMachine.getAmountIntroducedAsInt(changeInCoins));

        System.out.println(changeInCoins);
    }

    @Test
    public void test_buyItem() {
        int initialCoinSize = coinStorage.getDimeStash().size();
        int initialStorageSize = storageMap.get(14).size();

        assertEquals(350, coinStorage.getTotalCoinsInStorage());
        assertThrows(SoldOutException.class, () -> vendingMachine.buyItem(12, null));
        assertThrows(NotFullPaidException.class, () -> vendingMachine.buyItem(14, new ArrayDeque<>()));
        vendingMachine.buyItem(14, introduceMoney(0, 1, 0, 0));

        assertEquals( initialStorageSize - 1, storageMap.get(14).size());
        assertEquals(initialCoinSize + 1, coinStorage.getDimeStash().size());
    }

    private Queue<Coin> introduceMoney(int quarters, int dimes, int nickels, int pennies) {
        Queue<Coin> stash = new ArrayDeque<>();

        stash.addAll(vendingMachine.addCoinsToStash(quarters, QUARTER_25));
        stash.addAll(vendingMachine.addCoinsToStash(dimes, DIME_10));
        stash.addAll(vendingMachine.addCoinsToStash(nickels, NICKEL_5));
        stash.addAll(vendingMachine.addCoinsToStash(pennies, PENNY_1));


        return stash;
    }

}
