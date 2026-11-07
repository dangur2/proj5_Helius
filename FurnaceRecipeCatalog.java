public class FurnaceRecipeCatalog {
    private final FurnaceRecipe[] furnaceRecipe;
    private final ItemCatalog itemCatalog;
    public FurnaceRecipeCatalog(ItemCatalog itemCatalog){
        this.itemCatalog = itemCatalog;
        furnaceRecipe = new FurnaceRecipe[1];
        loadRecipes();
    }

    private void loadRecipes() {
        furnaceRecipe[0] = new FurnaceRecipe(itemCatalog.getByName("Copper nugget"), 1, itemCatalog.getByName("Stick"), 5, itemCatalog.getByName("Copper bar"), 5);
    }

    public FurnaceRecipe getRecipe(int x){
        return furnaceRecipe[x];
    }
    public FurnaceRecipe getRecipeByName(String name){
        for (FurnaceRecipe i : furnaceRecipe) {
            if (i.getResource().getItemName().equals(name)) {
                return i;
            }
        }
        return null;
    }
    public int getSize(){
        return furnaceRecipe.length;
    }

}
