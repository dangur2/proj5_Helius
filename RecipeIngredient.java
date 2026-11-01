public class RecipeIngredient {

    private final Item item;
    private final int quantity;

    public RecipeIngredient(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem(){
        return this.item;
    }
    public int getQuantity(){
        return this.quantity;
    }
}
