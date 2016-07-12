package ph.kita.devsquare.com.dummy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ph.kita.devsquare.com.objects.Item;

/**
 * Created by Alex on 12/07/2016.
 */
public class ItemDummyDb {

    private static int index = 0;
    private static List<Item> items = new ArrayList<>();

    {
        List<Item> dumyPOSItems = new ArrayList<Item>(Arrays.asList(new Item(index++,"vaseline", 1, "shampoo", 2, 1, "apple.png",null),
                new Item(index++,"safeguard", 1, "soap", 2, 1, "apple.png",null),
                new Item(index++,"silka", 1, "soap", 2, 1, null,null)));

        items.addAll(dumyPOSItems);
    }

    public static int count() {
        return items.size();
    }

    public static List<Item> getAll() {
        List<Item> copyItems = new ArrayList<>();
        for (Item item : items) {
            copyItems.add(new Item(item));
        }

        return copyItems;
    }

    public static Item getItemById(int id) {
        for (Item item : items) {
            if (id == item.getId()) {
                return new Item(item);
            }
        }

        return null;
    }

    public static int createItem(Item item) {
        index++;
        item.setId(index);
        items.add(item);
        return index;
    }

    public static void updateItem(Item item) {
        for (Item dbItem : items) {
            if (item.getId() == dbItem.getId()) {
                dbItem.setName(item.getName());
                dbItem.setPrice(item.getPrice());
                dbItem.setQualitytNWeight(item.getQualitytNWeight());
                dbItem.setStock(item.getStock());
                dbItem.setTag(item.getTag());

                break;
            }
        }
    }

    public static void removeItem(Item item) {
        items.remove(item);
    }
}
