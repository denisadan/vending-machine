package vendingmachine.impl;

import vendingmachine.model.Coin;
import vendingmachine.model.Item;

import java.util.Queue;

public interface IVendingMachine {

	Item previewItem(int shelfCode);

	void buyItem(int shelfCode, Queue<Coin> amountPayed);
	
	int payItem(int productPrice, Queue<Coin> amountIntroduced);
	
	Queue<Coin> getChangeInCoins(int change);
	
}
