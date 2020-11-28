package vendingmachine.storage;

import vendingmachine.model.Item;

import java.util.Map;
import java.util.Queue;

public class VendingStorage {

	private Map<Integer, Queue<Item>> storageMap;

	public Map<Integer, Queue<Item>> getStorageMap() {
		return storageMap;
	}

	public void setStorageMap(Map<Integer, Queue<Item>> storageMap) {
		this.storageMap = storageMap;
	}

	@Override
	public String toString() {
		return "VendingStorage{" +
				"storageMap=" + storageMap +
				'}';
	}

	public void updateStorage(int shelfCode) {
		storageMap.get(shelfCode).poll();
	}
}
