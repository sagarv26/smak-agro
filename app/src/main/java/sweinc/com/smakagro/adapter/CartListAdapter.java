package sweinc.com.smakagro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import sweinc.com.smakagro.R;
import sweinc.com.smakagro.model.CartItem;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {
    private List<CartItem> cartList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView order_Title;
        public TextView quantity, cost;
        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            this.order_Title = view.findViewById(R.id.name);
            this.quantity = view.findViewById(R.id.quantity);
            this.cost = view.findViewById(R.id.cost);
            this.viewBackground = view.findViewById(R.id.view_background);
            this.viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public CartListAdapter(Context context, List<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        CartItem item = this.cartList.get(position);
        holder.order_Title.setText(item.getCartOrderName());
        holder.quantity.setText(item.getCartQuantityName());
        holder.cost.setText(item.getCartOrderCost());
    }

    public int getItemCount() {
        return this.cartList.size();
    }

    public void removeItem(int position) {
        this.cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(CartItem item, int position) {
        this.cartList.add(position, item);
        notifyItemInserted(position);
    }
}
