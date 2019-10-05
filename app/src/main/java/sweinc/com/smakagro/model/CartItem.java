package sweinc.com.smakagro.model;

public class CartItem {
    private String cartOrderName;
    private String cartOrderCost;
    private String cartQuantityName;

    public CartItem(String cartOrderName, String cartQuantityName, String cartOrderCost) {
        this.cartOrderName = cartOrderName;
        this.cartQuantityName = cartQuantityName;
        this.cartOrderCost = cartOrderCost;

    }

    public String getCartOrderName() {
        return cartOrderName;
    }

    public void setCartOrderName(String cartOrderName) {
        this.cartOrderName = cartOrderName;
    }

    public String getCartQuantityName() {
        return cartQuantityName;
    }

    public void setCartQuantityName(String cartQuantityName) {
        this.cartQuantityName = cartQuantityName;
    }

    public String getCartOrderCost() {
        return cartOrderCost;
    }

    public void setCartOrderCost(String cartOrderCost) {
        this.cartOrderCost = cartOrderCost;
    }

}

