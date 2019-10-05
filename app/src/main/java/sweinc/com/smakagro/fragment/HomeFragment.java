package sweinc.com.smakagro.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;

import sweinc.com.smakagro.R;
import sweinc.com.smakagro.adapter.FlipperAdapter;
import sweinc.com.smakagro.classes.Recipes_Page;
import sweinc.com.smakagro.classes.Show_Web;

public class HomeFragment extends Fragment {

    private int[] imageArray = new int[]{R.drawable.coco_about, R.drawable.organic_desiccated_coconut, R.drawable.laddu, R.drawable.coconut_burfi};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        AdapterViewFlipper adapterView = rootView.findViewById(R.id.adapterFlipper);
        adapterView.setAdapter(new FlipperAdapter(getActivity(), this.imageArray));
        adapterView.setAutoStart(true);
        Button about_us = rootView.findViewById(R.id.about_us);
        Button about_coco = rootView.findViewById(R.id.about_coco);
        Button about_recipe = rootView.findViewById(R.id.about_recipe);

        about_coco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Show_Web.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("keyTitle", "About Coconut");
                intent.putExtra("keyFile", "file:///android_asset/AboutCoco.html");
                getContext().startActivity(intent);
            }
        });

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Show_Web.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("keyTitle", "About Us");
                intent.putExtra("keyFile", "file:///android_asset/AboutUs.html");
                getContext().startActivity(intent);
            }
        });

        about_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Recipes_Page.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
