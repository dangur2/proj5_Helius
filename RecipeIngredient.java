public class RecipeIngredient {

    private final String name;
    private final int quantity;

    public RecipeIngredient(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String getName(){
        return this.name;
    }
    public int getQuantity(){
        return this.quantity;
    }
}
