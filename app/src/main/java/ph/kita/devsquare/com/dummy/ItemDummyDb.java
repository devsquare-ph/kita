package ph.kita.devsquare.com.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ph.kita.devsquare.com.objects.Item;

/**
 * Created by Alex on 12/07/2016.
 */
public class ItemDummyDb {
    private static final String TAG = ItemDummyDb.class.getName();

    private int index = 0;
    private List<Item> items = new ArrayList<>();

    public ItemDummyDb() {
        List<Item> dumyPOSItems = new ArrayList<Item>(Arrays.asList(new Item(index++,"vaseline", 1, "shampoo", 2, 1, "apple.png",null),
                new Item(index++,"safeguard", 1, "soap", 2, 1, "apple.png",null),
                new Item(index++,"silka", 1, "soap", 2, 1, null,null)));

        items.addAll(dumyPOSItems);
    }


    public int count() {
        Log.d(TAG, "count: " + items.size());
        return items.size();
    }

    public List<Item> getAll() {
        List<Item> copyItems = new ArrayList<>();
        for (Item item : items) {
            copyItems.add(new Item(item));
        }
        Log.d(TAG, "getAll: " + copyItems.toString());
        return copyItems;
    }

    public Item getItemById(int id) {
        for (Item item : items) {
            if (id == item.getId()) {
                Log.d(TAG, "getItemById: " + item.toString());
                return new Item(item);
            }
        }

        return null;
    }

    public int createItem(Item item) {
        index++;
        item.setId(index);
        items.add(item);
        Log.d(TAG, "createItem: " + item.toString());
        return index;
    }

    public void updateItem(Item item) {
        for (Item dbItem : items) {
            if (item.getId() == dbItem.getId()) {
                dbItem.setName(item.getName());
                dbItem.setPrice(item.getPrice());
                dbItem.setQualitytNWeight(item.getQualitytNWeight());
                dbItem.setStock(item.getStock());
                dbItem.setTag(item.getTag());
                Log.d(TAG, "updateItem: " + dbItem.toString());
                break;
            }
        }
    }

    public void removeItem(Item item) {
        Log.d(TAG, "removeItem: " + item.toString());
        items.remove(item);
    }
}
