package sweinc.com.smakagro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import sweinc.com.smakagro.classes.Cart_Page;
import sweinc.com.smakagro.classes.Recipes_Page;
import sweinc.com.smakagro.classes.Show_Web;
import sweinc.com.smakagro.database.Cart_Database;
import sweinc.com.smakagro.fragment.HomeFragment;
import sweinc.com.smakagro.fragment.ProductFragment;
import sweinc.com.smakagro.fragment.RecipesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    Cart_Database cart_database;
    Cursor res;
    static String number="9845779102";
    static String address="14.475004,75.888955";
    static String head="7-K2, Near DC Office";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        cart_database = new Cart_Database(getApplicationContext());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res = cart_database.read();
                if(!res.moveToNext()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Your Cart is Empty....Add recipes to it now")
                            .setTitle("Empty")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(-2).setBackgroundColor(-1);
                    alert.getButton(-2).setLeft(10);
                    alert.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    alert.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    alert.getButton(-1).setBackgroundColor(-1);
                } else{
                    Intent intent = new Intent(getApplicationContext(), Cart_Page.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);

    }

    protected void onStart() {
        super.onStart();
        this.cart_database.openDB();
    }

    protected void onStop() {
        super.onStop();
        this.cart_database.closeDB();
    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Show_Web.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("keyTitle", "About Us");
            intent.putExtra("keyFile", "file:///android_asset/AboutUs.html");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ProductFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return 2;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Product";
                default:
                    return null;
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_addContact) {
            Intent contactIntent = new Intent("android.intent.action.INSERT");
            contactIntent.setType("vnd.android.cursor.dir/raw_contact");
            contactIntent.putExtra("name", "SMAK AGRO").putExtra("phone", "9845779102").putExtra("com.android.contacts.action.ATTACH_IMAGE", "@drawable/ic_contact_phone_black");
            startActivityForResult(contactIntent, 1);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, Show_Web.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("keyTitle", "About Us");
            intent.putExtra("keyFile", "file:///android_asset/AboutUs.html");
            this.startActivity(intent);
        } else if (id == R.id.nav_recipe) {
            Intent intent = new Intent(this, Recipes_Page.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            res = cart_database.read();
            if(!res.moveToNext()){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Your Cart is Empty....Add recipes to it now")
                        .setTitle("Empty")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(-2).setBackgroundColor(-1);
                alert.getButton(-2).setLeft(10);
                alert.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                alert.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                alert.getButton(-1).setBackgroundColor(-1);
            } else{
                Intent intent = new Intent(getApplicationContext(), Cart_Page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/email");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"smakagroenterprises@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            intent.setPackage("com.google.android.gm");
            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(Intent.createChooser(intent, "Send Feedback:"));
        }  else if (id == R.id.nav_share) {
            Intent share = new Intent("android.intent.action.SEND");
            share.setType("text/plain");
            share.putExtra("android.intent.extra.TEXT", "SMAK AGRO : Best In Market\n\nhttps://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            startActivity(share);
        }  else if (id == R.id.nav_call) {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:"+number));
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Please Grant the Permission to call",Toast.LENGTH_LONG).show();
                requestPermissions();
            } else {
                startActivity(i);
            }
        } else if (id == R.id.nav_address) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("geo:"+address+"?q=("+head+")@"+address));
            Intent chooser = Intent.createChooser(i, "Launch Maps");
            startActivity(chooser);
        }

        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

}
