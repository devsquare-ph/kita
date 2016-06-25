package ph.kita.devsquare.com.kita;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ph.kita.devsquare.com.fragment.InventoryFragment;
import ph.kita.devsquare.com.fragment.InventoryItemFragment;
import ph.kita.devsquare.com.fragment.PosCartFragment;
import ph.kita.devsquare.com.fragment.PosFragment;
import ph.kita.devsquare.com.fragment.PosReceiptFragment;
import ph.kita.devsquare.com.objects.Item;
import ph.kita.devsquare.com.utils.Constant;

public class MainActivity extends FragmentActivity implements PosFragment.OnFragmentPOSListener, PosCartFragment.OnFragmentPOSCartListener, InventoryFragment.OnFragmentInventoryListener, InventoryItemFragment.OnFragmentInventoryItemListener, PosReceiptFragment.OnFragmentReceiptListener {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate MainACtivtiy");
         /*Set the Home Fragment*/
        Fragment fragment = new Fragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new PosFragment();
            fragmentTransaction.replace(R.id.main, fragment, PosFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }

        new DrawerBuilder().withActivity(this).build();

        PrimaryDrawerItem drawerPos = new PrimaryDrawerItem().withName(R.string.drawer_item_pos);
        PrimaryDrawerItem drawerInventory = new PrimaryDrawerItem().withName(R.string.drawer_item_inventory);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        drawerPos,
                        drawerInventory
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Log.d(TAG, "POSITION " + position);

                        Fragment fragment = new Fragment();
                        String tagFragment = "";
                        switch (position) {

                            case Constant.DRAWER_POS:
                                fragment = new PosFragment();
                                tagFragment = PosFragment.class.getSimpleName();
                                break;
                            case Constant.DRAWER_INVENTORY:
                                fragment = new InventoryFragment();
                                tagFragment = InventoryFragment.class.getSimpleName();
                                break;
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main, fragment, tagFragment);
                            fragmentTransaction.commit();
                        }

                        return false;
                    }
                })
                .build();


    }

    @Override
    public void onCart(Item item) {
        Log.d(TAG, "onCart");
        try {
            Fragment fragment = PosCartFragment.newInstance(item.getId(), item.getName(), item.getPrice(), item.getImageURL(), item.getQualitytNWeight(), item.getTag(), item.getStock());
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, fragment, PosCartFragment.class.getSimpleName());
                fragmentTransaction.addToBackStack(PosCartFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        } catch (IOException e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCheckOut(ArrayList<Item> items) {
        Log.d(TAG, "onCheckOut");
            Fragment fragment = PosReceiptFragment.newInstance(items);
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, fragment, PosReceiptFragment.class.getSimpleName());
                fragmentTransaction.addToBackStack(PosReceiptFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
    }

    @Override
    public void onAddToCart(final Item item) {
        Log.d(TAG, "price: " + item.getPrice());
        Toast.makeText(this,"addToCart",Toast.LENGTH_SHORT).show();

        PosFragment posCartFragment = (PosFragment) getSupportFragmentManager().findFragmentByTag(PosFragment.class.getSimpleName());

        posCartFragment.addPosCart(item);

        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onAddItem() {
        Log.d(TAG, "onCheckOut");
        Fragment fragment = InventoryItemFragment.newInstance();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main, fragment, InventoryItemFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(InventoryItemFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onSaveItem(Item item) {
        PosFragment.dumyPOSItems.add(item);

        Toast.makeText(this,"itemsize: " + PosFragment.dumyPOSItems.size(), Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSave(List<Item> item) {
        Toast.makeText(this, "save purchase items", Toast.LENGTH_SHORT).show();
        PosFragment posFragment = (PosFragment) getSupportFragmentManager().findFragmentByTag(PosFragment.class.getSimpleName());
        posFragment.clear();
        getSupportFragmentManager().popBackStack();
    }
}
