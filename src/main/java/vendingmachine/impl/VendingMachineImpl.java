package vendingmachine.impl;


import vendingmachine.exceptions.NotFullPaidException;
import vendingmachine.exceptions.NotSufficientChangeException;
import vendingmachine.exceptions.SoldOutException;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;
import vendingmachine.storage.CoinStorage;
import vendingmachine.storage.VendingStorage;

import java.util.ArrayDeque;
import java.util.Queue;

import static vendingmachine.model.Coin.*;

public class VendingMachineImpl implements IVendingMachine {

    private VendingStorage storage;
    private CoinStorage coinStorage;

    public void setStorage(VendingStorage storage) {
        this.storage = storage;
    }

    public void setCoinStorage(CoinStorage coinStorage) {
        this.coinStorage = coinStorage;
    }

    @Override
    public void buyItem(int shelfCode, Queue<Coin> amountPayed) {
        Item selectedItem = previewItem(shelfCode);

        int changeToBeDelivered = payItem(selectedItem.getPrice(), amountPayed);
        Queue<Coin> changeInCoins = getChangeInCoins(changeToBeDelivered);

        coinStorage.updateWithCoins(amountPayed);
        coinStorage.updateWithChange(changeInCoins);
        storage.updateStorage(shelfCode);

    }

    @Override
    public Item previewItem(int shelfCode) {
        Queue<Item> items = storage.getStorageMap().get(shelfCode);

        if (items == null || items.isEmpty()) {
            throw new SoldOutException("Item is not available.");
        }

        return items.peek();
    }

    @Override
    public int payItem(int productPrice, Queue<Coin> amountIntroduced) {
        int amountIntroducedAsInt = getAmountIntroducedAsInt(amountIntroduced);

        if (amountIntroducedAsInt >= productPrice) {
            return amountIntroducedAsInt - productPrice;
        } else {
            throw new NotFullPaidException();
        }
    }

    @Override
    public Queue<Coin> getChangeInCoins(int change) {
        if (coinStorage.getTotalCoinsInStorage() < change) {
            throw new NotSufficientChangeException("Coins amount is not enough to retrieve change.");
        }

        int noOfCoinsNeeded = change / 25;
        int noOfCoinsGot;
        noOfCoinsGot = getTheNoOfCoinsNeeded(noOfCoinsNeeded, coinStorage.getQuarterStash()); //1
        Queue<Coin> changeInQuarters = addCoinsToStash(noOfCoinsGot, QUARTER_25);

        int remainingChange = change - noOfCoinsGot * 25;

        noOfCoinsNeeded = remainingChange / 10;
        noOfCoinsGot = getTheNoOfCoinsNeeded(noOfCoinsNeeded, coinStorage.getDimeStash());
        Queue<Coin> changeInDimes = addCoinsToStash(noOfCoinsGot, DIME_10);
        remainingChange = remainingChange - noOfCoinsGot * 10;

        noOfCoinsNeeded = remainingChange / 5;
        noOfCoinsGot = getTheNoOfCoinsNeeded(noOfCoinsNeeded, coinStorage.getNickelStash());
        Queue<Coin> changeInNickels = addCoinsToStash(noOfCoinsGot, NICKEL_5);
        remainingChange = remainingChange - noOfCoinsGot * 5;

        noOfCoinsNeeded = remainingChange;
        noOfCoinsGot = getTheNoOfCoinsNeeded(noOfCoinsNeeded, coinStorage.getPennyStash());
        remainingChange = remainingChange - noOfCoinsGot;
        Queue<Coin> changeInPennies = addCoinsToStash(noOfCoinsGot, PENNY_1);

        if (remainingChange != 0) {
            throw new NotSufficientChangeException("Cannot retrieve change from existing coins.");
        }

        Queue<Coin> changeReturned = new ArrayDeque<>(changeInQuarters);
        changeReturned.addAll(changeInNickels);
        changeReturned.addAll(changeInPennies);
        changeReturned.addAll(changeInDimes);

        return changeReturned;
    }

    private int getTheNoOfCoinsNeeded(int noOfCoins, Queue<Coin> stash) {
        return Math.min(noOfCoins, stash.size());
    }

    public Queue<Coin> addCoinsToStash(int noOfCoins, Coin coin) {
        Queue<Coin> stash = new ArrayDeque<>();
        for (int i = 0; i < noOfCoins; i++) {
            stash.add(coin);
        }
        return stash;
    }

    public int getAmountIntroducedAsInt(Queue<Coin> amountIntroduced) {
        int total = 0;
        for (Coin c : amountIntroduced) {
            switch (c) {
                case QUARTER_25:
                    total += 25;
                    break;
                case DIME_10:
                    total += 10;
                    break;
                case NICKEL_5:
                    total += 5;
                    break;
                case PENNY_1:
                    total += 1;
                    break;
            }
        }

        return total;
    }

}
