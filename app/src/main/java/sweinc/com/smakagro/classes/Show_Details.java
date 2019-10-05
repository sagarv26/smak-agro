package sweinc.com.smakagro.classes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import sweinc.com.smakagro.R;
import sweinc.com.smakagro.database.Cart_Database;


public class Show_Details extends AppCompatActivity{
    static String material_des = null;
    static String procedure = null;
    static String recipe_title = null;
    static int pic;

    Cart_Database cart_database;

    EditText input_quantity;

//    private Button add_cart;
//    private Button what_you_expect;
//    final Context mContext = this;


//    class C02972 implements OnClickListener {
//
//        class C02951 implements DialogInterface.OnClickListener {
//            C02951() {
//            }
//
//            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(Show_Details.this.mContext, "Cancelled", Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        }
//
//        class C02962 implements DialogInterface.OnClickListener {
//            C02962() {
//            }
//
//            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(Show_Details.this.mContext, "Added", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        C02972() {
//        }
//
//        public void onClick(View v) {
//            View promptsView = LayoutInflater.from(Show_Details.this.mContext).inflate(R.layout.what_you_expect, null);
//            Builder alertDialogBuilder = new Builder(Show_Details.this.mContext);
//            alertDialogBuilder.setView(promptsView);
//            EditText userInput = promptsView.findViewById(R.id.what_you_expect_field);
//
//            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new C02962()).setNegativeButton("Cancel", new C02951());
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//            alertDialog.getButton(-2).setBackgroundColor(-1);
//            alertDialog.getButton(-2).setLeft(10);
//            alertDialog.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
//            alertDialog.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
//            alertDialog.getButton(-1).setBackgroundColor(-1);
//        }
//    }
//
//    class C02983 implements OnClickListener {
//        C02983() {
//        }
//
//        public void onClick(View v) {
//            cart_database = Show_Details.this.cart_database;
//            if(input_quantity.getText().toString().matches("") || input_quantity.getText().toString().matches("0")){
//                Toast.makeText(Show_Details.this.getApplicationContext(), "Please provide required Quantity", Toast.LENGTH_SHORT).show();
//            }else{
//                if (Cart_Database.validate(Show_Details.recipe_title) != 0) {
//                    if (Long.valueOf(Show_Details.this.cart_database.update(Show_Details.recipe_title, input_quantity.getText().toString().trim(), String.valueOf(200*Integer.valueOf(input_quantity.getText().toString().trim())))).longValue() == -1) {
//                        Toast.makeText(Show_Details.this.getApplicationContext(), Show_Details.recipe_title + " : Not Updated to Cart", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(Show_Details.this.getApplicationContext(), Show_Details.recipe_title + " Value Updated to Cart", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (Long.valueOf(Show_Details.this.cart_database.create(Show_Details.recipe_title, input_quantity.getText().toString().trim(), String.valueOf(200*Integer.valueOf(input_quantity.getText().toString().trim())))).longValue() == -1) {
//                    Toast.makeText(Show_Details.this.getApplicationContext(), Show_Details.recipe_title + " : Not Added to Cart", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(Show_Details.this.getApplicationContext(), Show_Details.recipe_title + " Added to Cart", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
//    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipes);

        cart_database = new Cart_Database(getApplicationContext());

        ImageView image = findViewById(R.id.detail_image);
        TextView title = findViewById(R.id.title);
        TextView procedureDescription = findViewById(R.id.procedureDescription);
        TextView materialDescription = findViewById(R.id.materialDescription);

//        add_cart = findViewById(R.id.add_cart);
//        what_you_expect = findViewById(R.id.button_what_you_expect);

        input_quantity = findViewById(R.id.input_quantity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        Intent intent = getIntent();
        material_des = String.valueOf(intent.getStringExtra("materialDes"));
        procedure = String.valueOf(intent.getStringExtra("procedure"));
        recipe_title = String.valueOf(intent.getStringExtra("keyTitle"));
        pic = getIntent().getExtras().getInt("keyImage");

        materialDescription.setText(material_des);
        procedureDescription.setText(procedure);
        title.setText(recipe_title);
        image.setImageResource(pic);
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        toolbar.setTitle(recipe_title+" Recipe");

//        what_you_expect.setOnClickListener(new C02972());
//        this.add_cart.setOnClickListener(new C02983());

    }
//
//    class addToCart implements OnClickListener {
//
//        class Cancelling implements DialogInterface.OnClickListener {
//            Cancelling() {
//            }
//
//            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(Show_Details.this.mContext, "Cancelled", Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        }
//
//        class Adding implements DialogInterface.OnClickListener {
//            Adding() {
//            }
//
//            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(Show_Details.this.mContext, "Added", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        addToCart() {
//        }
//
//        public void onClick(View v) {
//            View promptsView = LayoutInflater.from(Show_Details.this.mContext).inflate(R.layout.cart_bill, null);
//            Builder alertDialogBuilder = new Builder(Show_Details.this.mContext);
//            alertDialogBuilder.setView(promptsView);
//            TextView totalQuantity = promptsView.findViewById(R.id.total_quantity);
//            TextView totalAmount= promptsView.findViewById(R.id.total_amount);
//
//            totalQuantity.setText(userQuantity+"kg");
//
//            int q = Integer.valueOf(userQuantity);
//            int amount = 200 * q;
//
//            totalAmount.setText(String.valueOf(amount));
//            alertDialogBuilder.setCancelable(false).setPositiveButton("Confirm", new Adding()).setNegativeButton("Cancel", new Cancelling());
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//            alertDialog.getButton(-2).setBackgroundColor(-1);
//            alertDialog.getButton(-2).setLeft(10);
//            alertDialog.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
//            alertDialog.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
//            alertDialog.getButton(-1).setBackgroundColor(-1);
//        }
//    }

    protected void onStart() {
        super.onStart();
        this.cart_database.openDB();
    }

    protected void onStop() {
        super.onStop();
        this.cart_database.closeDB();
    }
}
