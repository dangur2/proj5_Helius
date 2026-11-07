public class FurnaceRecipe {
    
    private final Item resource;
    private final int resourcequantity;
    private final Item fuel;
    private final int fuelquantity;
    private final Item result;
    private final int time;

    public FurnaceRecipe(Item resource, int resourcequantity, Item fuel, int fuelquantity, Item result, int time){
        this.resource = resource;
        this.resourcequantity = resourcequantity;
        this.fuel = fuel;
        this.fuelquantity = fuelquantity;
        this.result = result;
        this.time = time;
    }

    public Item getResource() {
        return resource;
    }

    public int getResourcequantity() {
        return resourcequantity;
    }

    public Item getFuel() {
        return fuel;
    }

    public int getFuelquantity() {
        return fuelquantity;
    }

    public Item getResult() {
        return result;
    }

    public int getTime() {
        return time;
    }
}
