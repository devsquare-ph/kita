package ph.kita.devsquare.com;

import android.app.Application;

import ph.kita.devsquare.com.dummy.ItemDummyDb;

/**
 * Created by Alex on 12/07/2016.
 */
public class KitaApplication extends Application {

    private ItemDummyDb itemDummyDb;


    @Override
    public void onCreate() {
        super.onCreate();
        itemDummyDb = new ItemDummyDb();
    }

    public ItemDummyDb getItemDummyDb() {
        return itemDummyDb;
    }
}
