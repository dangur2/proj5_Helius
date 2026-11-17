import java.io.IOException;
import javax.imageio.ImageIO;

public class TileCatalog {
    Tile[] tile;

    public TileCatalog(){
        tile = new Tile[22];
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
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/leaves_decal.png"));
            tile[9].collision = false;
            tile[9].requiredTool = ItemType.HOE;
            tile[9].setTile = 4;
            tile[9].health = 0;
            tile[9].name = "leaves";

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

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/leaves_decal2.png"));
            tile[12].collision = false;
            tile[12].requiredTool = ItemType.HOE;
            tile[12].setTile = 4;
            tile[12].health = 0;
            tile[12].name = "leaves";

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_patch1.png"));
            tile[13].collision = false;
            tile[13].requiredTool = ItemType.HOE;
            tile[13].setTile = 4;
            tile[13].health = 0;
            tile[13].name = "grasspatch1";

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_patch2.png"));
            tile[14].collision = false;
            tile[14].requiredTool = ItemType.HOE;
            tile[14].setTile = 4;
            tile[14].health = 0;
            tile[14].name = "grasspatch2";

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_patch3.png"));
            tile[15].collision = false;
            tile[15].requiredTool = ItemType.HOE;
            tile[15].setTile = 4;
            tile[15].health = 0;
            tile[15].name = "grasspatch3";

            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tile2.png"));
            tile[16].collision = false;
            tile[16].requiredTool = ItemType.HOE;
            tile[16].setTile = 4;
            tile[16].health = 1;
            tile[16].name = "grass2";

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tile3.png"));
            tile[17].collision = false;
            tile[17].requiredTool = ItemType.HOE;
            tile[17].setTile = 4;
            tile[17].health = 1;
            tile[17].name = "grass3";

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tile4.png"));
            tile[18].collision = false;
            tile[18].requiredTool = ItemType.HOE;
            tile[18].setTile = 4;
            tile[18].health = 1;
            tile[18].name = "grass4";

            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_patch4.png"));
            tile[19].collision = false;
            tile[19].requiredTool = ItemType.HOE;
            tile[19].setTile = 4;
            tile[19].health = 0;
            tile[19].name = "grasspatch4";

            tile[20] = new Tile();
            tile[20].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_patch5.png"));
            tile[20].collision = false;
            tile[20].requiredTool = ItemType.HOE;
            tile[20].setTile = 4;
            tile[20].health = 0;
            tile[20].name = "grasspatch5";

            tile[21] = new Tile();
            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/water_tile1.png"));
            tile[21].collision = true;
            tile[21].requiredTool = null;
            tile[21].setTile = 1;
            tile[21].health = 0;
            tile[21].name = "wateredge";

        } catch (IOException e) {
        }
    }
}
