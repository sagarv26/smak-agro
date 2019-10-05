package sweinc.com.smakagro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import sweinc.com.smakagro.R;

public class FlipperAdapter extends BaseAdapter {
    int[] images;
    LayoutInflater inflater;
    Context mContext;

    public FlipperAdapter(Context mContext, int[] images) {
        this.mContext = mContext;
        this.images = images;
        this.inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return this.images.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.inflater.inflate(R.layout.image_content, null);
        ((ImageView) view.findViewById(R.id.slideImageView)).setImageResource(this.images[position]);
        return view;
    }
}

