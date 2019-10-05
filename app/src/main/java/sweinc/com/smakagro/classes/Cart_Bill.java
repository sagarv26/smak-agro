package sweinc.com.smakagro.classes;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import sweinc.com.smakagro.R;
import sweinc.com.smakagro.database.Cart_Database;

public class Cart_Bill extends AppCompatActivity {

    Cart_Database cart_database;
    String[] Storage_Permission = new String[]{"android.permission.SEND_SMS", "android.permission.CALL_PHONE","android.permission.WRITE_SMS","android.permission.READ_SMS"};
    StringBuilder sb;
    final static int REQUESTCODE_PERMISSION_SMS = 301;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_bill);
        sb = new StringBuilder();
        this.cart_database = new Cart_Database(getApplicationContext());
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNav());

        TextView recipe_bill = findViewById(R.id.recipe_bill);
        TextView total_amount = findViewById(R.id.total_amount);
        int amount = 0;
        Cursor res = this.cart_database.read();
        while (res.moveToNext()) {

            String name = res.getString(res.getColumnIndex("name"));
            String cost = res.getString(res.getColumnIndex("cost"));
            String quantity = res.getString(res.getColumnIndex("quantity"));
            amount += Integer.valueOf(cost);
            sb.append("Recipe : "+name+"\nQuantity : "+quantity+"kgs\nCost : "+"₹"+cost+"\r\n\n");

        }

        recipe_bill.setText(sb);
        total_amount.setText("₹"+amount);

        if (ContextCompat.checkSelfPermission(this, "android.permission.SEND_SMS") != 0) {
            ActivityCompat.requestPermissions(this, this.Storage_Permission, 1);
        }


        if (Build.VERSION.SDK_INT >= 28) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUESTCODE_PERMISSION_SMS);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUESTCODE_PERMISSION_SMS);
        }
    }

    class BottomNav implements BottomNavigationView.OnNavigationItemSelectedListener {
        BottomNav() {
        }

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String ordered_list = "SMAK AGRO : Best In Market \n\nUser Ordered List\n\n";
            switch (item.getItemId()) {
                case R.id.actionOnClick1:
                    Intent share = new Intent("android.intent.action.SEND");
                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share.setType("text/plain");
                    share.putExtra("android.intent.extra.TEXT", ordered_list +sb);
                    getApplicationContext().startActivity(share);
                    break;
                case R.id.actionOnClick2:
                    try {
                        String mobile = "919845779102";
                        String msg = ordered_list +sb;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mobile + "&text=" + msg)));
                    }catch (Exception e){
                        Toast.makeText(Cart_Bill.this,"Whats App not Installed",Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.actionOnClick3:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/email");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"smakagroenterprises@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Order List");
                    intent.putExtra(Intent.EXTRA_TEXT, ordered_list +sb);
                    intent.setPackage("com.google.android.gm");
                    intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(Intent.createChooser(intent, "Order List:"));
                    break;
            }
            return true;
        }
    }

}
