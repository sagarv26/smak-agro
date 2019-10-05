package sweinc.com.smakagro.classes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sweinc.com.smakagro.MainActivity;
import sweinc.com.smakagro.R;
import sweinc.com.smakagro.adapter.CartListAdapter;
import sweinc.com.smakagro.adapter.RecyclerItemTouchHelper;
import sweinc.com.smakagro.database.Cart_Database;
import sweinc.com.smakagro.model.CartItem;

public class Cart_Page extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private CartListAdapter adapter;
    Cart_Database bf_database;

    private int countOrderedList = 0;
    private List<CartItem> listItem;
    private String ordered_list = "UR Caterers : Best In Market \n\nUser Ordered List\n\n";
    private RecyclerView recyclerView;
    Button last_step;
    Cursor res;

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % this.spanCount;
            if (this.includeEdge) {
                outRect.left = this.spacing - ((this.spacing * column) / this.spanCount);
                outRect.right = ((column + 1) * this.spacing) / this.spanCount;
                if (position < this.spanCount) {
                    outRect.top = this.spacing;
                }
                outRect.bottom = this.spacing;
                return;
            }
            outRect.left = (this.spacing * column) / this.spanCount;
            outRect.right = this.spacing - (((column + 1) * this.spacing) / this.spanCount);
            if (position >= this.spanCount) {
                outRect.top = this.spacing;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        last_step = findViewById(R.id.last_step);
        ActionBar toolbar = getSupportActionBar();
        assert toolbar != null;
        toolbar.setTitle("SMAK Agro Cart");
        this.bf_database = new Cart_Database(getApplicationContext());
        this.listItem = new ArrayList();
        res = this.bf_database.read();
        while (res.moveToNext()) {
            this.countOrderedList++;
            StringBuilder append = new StringBuilder().append(this.ordered_list).append(this.countOrderedList).append(". ");
            Cart_Database breakfast_Database = this.bf_database;
            append = append.append(res.getString(res.getColumnIndex("quantity"))).append(" : ");
            breakfast_Database = this.bf_database;
            this.ordered_list = append.append(res.getString(res.getColumnIndex("name"))).append("\n").toString();
            Cart_Database breakfast_Database2 = this.bf_database;
            String string = res.getString(res.getColumnIndex("name"));
            breakfast_Database = this.bf_database;
            this.listItem.add(new CartItem(string, "kgs: "+res.getString(res.getColumnIndex("quantity")), "₹"+res.getString(res.getColumnIndex("cost"))));
        }
        this.adapter = new CartListAdapter(getApplication(), this.listItem);
        this.recyclerView = findViewById(R.id.cartRecyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.adapter);
        new ItemTouchHelper(new RecyclerItemTouchHelper(0, 4, this)).attachToRecyclerView(this.recyclerView);

        last_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res = bf_database.read();
                if(!res.moveToNext()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Cart_Page.this);
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
                    Intent intent = new Intent(getApplicationContext(), Cart_Bill.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartListAdapter.MyViewHolder) {
            final String name = this.listItem.get(viewHolder.getAdapterPosition()).getCartOrderName();
            final String cost = this.listItem.get(viewHolder.getAdapterPosition()).getCartOrderCost().substring(1);
            final String quantity = this.listItem.get(viewHolder.getAdapterPosition()).getCartQuantityName().substring(5);
            final CartItem deletedItem = this.listItem.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            this.adapter.removeItem(viewHolder.getAdapterPosition());
            if (Long.valueOf(this.bf_database.delete(name)).longValue() == -1) {
                Toast.makeText(this, "Data not Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
            }
            Snackbar snackbar = Snackbar.make(findViewById(R.id.fav_rec_view), name + " removed from cart!", 0);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                public void onClick(View view) {
//                    Log.e("Cart","Cost:"+cost+" Quantity:"+quantity);
                    Cart_Page.this.adapter.restoreItem(deletedItem, deletedIndex);
                    if (Long.valueOf(Cart_Page.this.bf_database.create(name, quantity, cost)).longValue() == -1) {
                        Toast.makeText(Cart_Page.this.getApplicationContext(), name + " : Not Added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Cart_Page.this.getApplicationContext(), name + " : Added back to Cart", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            snackbar.setActionTextColor(InputDeviceCompat.SOURCE_ANY);
            snackbar.show();
        }
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }
}