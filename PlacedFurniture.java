

public class PlacedFurniture {
    private FurnitureType furniture;
    private int currentHealth;
    private int useTime;

    public FurnitureType getFurniture(){
        return furniture;
    }
    public void setFurniture(FurnitureType furniture){
        this.furniture = furniture;
        this.useTime = furniture.useTime;
        this.currentHealth = furniture.health;
    }

    public int getHealth(){
        return currentHealth;
    }
    public void setHealth(int x){
        this.currentHealth += x;
    }
    public int getUseTime(){
        return useTime;
    }
    public void increaseUseTime(int x){
        this.useTime += x;
    }
}
