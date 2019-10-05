package sweinc.com.smakagro.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Properties;

import sweinc.com.smakagro.R;
import sweinc.com.smakagro.adapter.RecipesAdapter;
import sweinc.com.smakagro.classes.PropertyFile;
import sweinc.com.smakagro.model.RecyclerItem;

public class RecipesFragment extends Fragment {

    private ArrayList<String> materialDes;
    private ArrayList<String> procedure;
    private ArrayList<String> title;
    private ArrayList listItem;
    private RecipesAdapter urAdapter_basic;
    static int count;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        this.listItem = new ArrayList();
        this.urAdapter_basic = new RecipesAdapter(this.listItem, getActivity());
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this.urAdapter_basic);

        materialDes = new ArrayList<>();
        procedure = new ArrayList<>();
        title = new ArrayList<>();
        PropertyFile propertyFile = new PropertyFile(getContext());
        Properties prop = propertyFile.getProperties("recipes.properties");
        count = Integer.parseInt(prop.getProperty("option"));

        for (int i = 1; i <= count; i=i+1) {
            materialDes.add(prop.getProperty("materialDes" + i));
            procedure.add(prop.getProperty("procedure" + i));
            title.add(prop.getProperty("title" + i));
        }
        prepareAlbums();
        return rootView;
    }

    private void prepareAlbums() {
        int[] covers = new int[]{R.drawable.laddu, R.drawable.coconut_burfi};
//        listItem.add(new RecyclerItem("Coconut Burfi", String.valueOf(R.string.material_barfi), String.valueOf(R.string.procedure_barfi), covers[1]));
        for (int k = 0; k <= count-1; k++) {
            listItem.add(new RecyclerItem(title.get(k), materialDes.get(k), procedure.get(k), covers[k]));
        }
        this.urAdapter_basic.notifyDataSetChanged();
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }
}
