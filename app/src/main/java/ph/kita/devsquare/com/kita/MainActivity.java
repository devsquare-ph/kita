package ph.kita.devsquare.com.kita;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ph.kita.devsquare.com.fragment.InventoryFragment;
import ph.kita.devsquare.com.fragment.PosFragment;
import ph.kita.devsquare.com.utils.Constant;

public class MainActivity extends FragmentActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /*Set the Home Fragment*/
        Fragment fragment = new Fragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new PosFragment();
            fragmentTransaction.replace(R.id.main, fragment);
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

                        switch (position) {

                            case Constant.DRAWER_POS:
                                fragment = new PosFragment();
                                break;
                            case Constant.DRAWER_INVENTORY:
                                fragment = new InventoryFragment();
                                break;
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main, fragment);
                            fragmentTransaction.commit();
                        }

                        return false;
                    }
                })
                .build();


    }
}
