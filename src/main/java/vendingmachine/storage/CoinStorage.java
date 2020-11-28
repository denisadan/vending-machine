package vendingmachine.storage;

import vendingmachine.model.Coin;

import java.util.Queue;

public class CoinStorage {

	private Queue<Coin> quarterStash;
	private Queue<Coin> dimeStash;
	private Queue<Coin> nickelStash;
	private Queue<Coin> pennyStash;

	public Queue<Coin> getQuarterStash() {
		return quarterStash;
	}

	public Queue<Coin> getDimeStash() {
		return dimeStash;
	}

	public Queue<Coin> getNickelStash() {
		return nickelStash;
	}

	public Queue<Coin> getPennyStash() {
		return pennyStash;
	}

	public void setQuarterStash(Queue<Coin> quarterStash) {
		this.quarterStash = quarterStash;
	}

	public void setDimeStash(Queue<Coin> dimeStash) {
		this.dimeStash = dimeStash;
	}

	public void setNickelStash(Queue<Coin> nickelStash) {
		this.nickelStash = nickelStash;
	}

	public void setPennyStash(Queue<Coin> pennyStash) {
		this.pennyStash = pennyStash;
	}

	public int getTotalCoinsInStorage() {
		return quarterStash.size() * 25 + dimeStash.size() * 10 + nickelStash.size() * 5 + pennyStash.size();
	}

	public void updateWithChange(Queue<Coin> moneyReceived) {
		for (Coin m : moneyReceived) {
			switch (m) {
				case QUARTER_25:
					getQuarterStash().poll();
					break;
				case DIME_10:
					getDimeStash().poll();
					break;
				case NICKEL_5:
					getNickelStash().poll();
					break;
				case PENNY_1:
					getPennyStash().poll();
					break;
			}
		}
	}

	public void updateWithCoins(Queue<Coin> moneyReceived) {
		for (Coin m : moneyReceived) {
			switch (m) {
				case QUARTER_25:
					getQuarterStash().add(m);
					break;
				case DIME_10:
					getDimeStash().add(m);
					break;
				case NICKEL_5:
					getNickelStash().add(m);
					break;
				case PENNY_1:
					getPennyStash().add(m);
					break;
			}
		}
	}
}
