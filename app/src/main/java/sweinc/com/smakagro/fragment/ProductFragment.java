package sweinc.com.smakagro.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sweinc.com.smakagro.R;
import sweinc.com.smakagro.classes.Cart_Page;
import sweinc.com.smakagro.classes.Show_Details;
import sweinc.com.smakagro.database.Cart_Database;

public class ProductFragment extends Fragment {

    private Button add_cart;
    EditText input_quantity;
    Cart_Database cart_database;

    static String recipe_title = "Organic Desiccated Coconut";

    class C02983 implements View.OnClickListener {
        C02983() {
        }

        public void onClick(View v) {
            cart_database = new Cart_Database(getContext());
            if(input_quantity.getText().toString().matches("") || input_quantity.getText().toString().matches("0")){
                Toast.makeText(getContext(), "Please provide required Quantity", Toast.LENGTH_SHORT).show();
            }else{
                if (Cart_Database.validate(recipe_title) != 0) {
                    if (Long.valueOf(cart_database.update(recipe_title, input_quantity.getText().toString().trim(), String.valueOf(200*Integer.valueOf(input_quantity.getText().toString().trim())))).longValue() == -1) {
                        Toast.makeText(getContext(), recipe_title + " : Not Updated to Cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), recipe_title + " Value Updated to Cart", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Cart_Page.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else if (Long.valueOf(cart_database.create(recipe_title, input_quantity.getText().toString().trim(), String.valueOf(200*Integer.valueOf(input_quantity.getText().toString().trim())))).longValue() == -1) {
                    Toast.makeText(getContext(), recipe_title + " : Not Added to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), recipe_title + " Added to Cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Cart_Page.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);
        cart_database = new Cart_Database(getContext());

        add_cart = rootView.findViewById(R.id.add_cart);

        input_quantity = rootView.findViewById(R.id.input_quantity);

        add_cart.setOnClickListener(new C02983());

        return rootView;
    }
}
