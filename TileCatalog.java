import java.io.IOException;
import javax.imageio.ImageIO;

public class TileCatalog {
    Tile[] tile;

    public TileCatalog(){
        tile = new Tile[15];
        setTiles();
    
    }
    public Tile getTile(int x){
        return tile[x];
    }

    private void setTiles() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tile.png"));
            tile[0].collision = false;
            tile[0].requiredTool = ItemType.HOE;
            tile[0].setTile = 4;
            tile[0].health = 1;
            tile[0].name = "grass";

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/water_tile.png"));
            tile[1].collision = true;
            tile[1].requiredTool = null;
            tile[1].setTile = 1;
            tile[1].health = 0;
            tile[1].name = "water";

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/brick_tile.png"));
            tile[2].collision = true;
            tile[2].requiredTool = null;
            tile[2].setTile = 2;
            tile[2].health = 0;
            tile[2].name = "brick";

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tile.png"));
            tile[3].collision = false;
            tile[3].requiredTool = ItemType.SHOVEL;
            tile[3].setTile = 6;
            tile[3].health = 0;
            tile[3].name = "sand";

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tile.png"));
            tile[4].collision = false;
            tile[4].requiredTool = ItemType.SHOVEL;
            tile[4].setTile = 5;
            tile[4].health = 0;
            tile[4].name = "dirt";

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dugdirt_tile.png"));
            tile[5].collision = false;
            tile[5].requiredTool = null;
            tile[5].setTile = 5;
            tile[5].health = 0;
            tile[5].name = "dugdirt";

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dugsand_tile.png"));
            tile[6].collision = false;
            tile[6].requiredTool = null;
            tile[6].setTile = 6;
            tile[6].health = 0;
            tile[6].name = "dugsand";

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/rose_flower.png"));
            tile[7].collision = false;
            tile[7].requiredTool = ItemType.HOE;
            tile[7].setTile = 4;
            tile[7].health = 0;
            tile[7].name = "rose";

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/rose_flower_2.png"));
            tile[8].collision = false;
            tile[8].requiredTool = ItemType.HOE;
            tile[8].setTile = 4;
            tile[8].health = 0;
            tile[8].name = "rosepatch";

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/cactus_flower.png"));
            tile[9].collision = false;
            tile[9].requiredTool = ItemType.SWORD;
            tile[9].setTile = 3;
            tile[9].health = 0;
            tile[9].name = "cactus";

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/daisy_flower.png"));
            tile[10].collision = false;
            tile[10].requiredTool = ItemType.HOE;
            tile[10].setTile = 4;
            tile[10].health = 0;
            tile[10].name = "daisy";

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/daisy_flower_2.png"));
            tile[11].collision = false;
            tile[11].requiredTool = ItemType.HOE;
            tile[11].setTile = 4;
            tile[11].health = 0;
            tile[11].name = "daisypatch";
        } catch (IOException e) {
        }
    }
}
