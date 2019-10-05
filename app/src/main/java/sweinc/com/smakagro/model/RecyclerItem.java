package sweinc.com.smakagro.model;

public class RecyclerItem {

    private String index_name;
    private String index_material;
    private String index_procedure;
    private int index_thumbnail;

    public RecyclerItem(String index_name, String index_material, String index_procedure, int index_thumbnail) {
        this.index_name = index_name;
        this.index_material = index_material;
        this.index_procedure = index_procedure;
        this.index_thumbnail = index_thumbnail;
    }

    public String getIndex_name() {
        return index_name;
    }

    public void setIndex_name(String index_name) {
        this.index_name = index_name;
    }

    public String getIndex_material() {
        return index_material;
    }

    public void setIndex_material(String index_material) {
        this.index_material = index_material;
    }

    public String getIndex_procedure() {
        return index_procedure;
    }

    public void setIndex_procedure(String index_procedure) {
        this.index_procedure = index_procedure;
    }

    public int getIndex_thumbnail() {
        return index_thumbnail;
    }

    public void setIndex_thumbnail(int index_thumbnail) {
        this.index_thumbnail = index_thumbnail;
    }
}