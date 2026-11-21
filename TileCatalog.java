import java.io.IOException;
import javax.imageio.ImageIO;

public class TileCatalog {
    Tile[] tile;

    public TileCatalog(){
        tile = new Tile[50];
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
            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileE.png"));
            tile[21].collision = true;
            tile[21].requiredTool = null;
            tile[21].setTile = 1;
            tile[21].health = 0;
            tile[21].name = "sandE";

            tile[22] = new Tile();
            tile[22].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileW.png"));
            tile[22].collision = true;
            tile[22].requiredTool = null;
            tile[22].setTile = 1;
            tile[22].health = 0;
            tile[22].name = "sandW";

            tile[23] = new Tile();
            tile[23].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileS.png"));
            tile[23].collision = true;
            tile[23].requiredTool = null;
            tile[23].setTile = 1;
            tile[23].health = 0;
            tile[23].name = "sandS";

            tile[24] = new Tile();
            tile[24].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileN.png"));
            tile[24].collision = true;
            tile[24].requiredTool = null;
            tile[24].setTile = 1;
            tile[24].health = 0;
            tile[24].name = "sandN";

            tile[25] = new Tile();
            tile[25].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileNE.png"));
            tile[25].collision = true;
            tile[25].requiredTool = null;
            tile[25].setTile = 1;
            tile[25].health = 0;
            tile[25].name = "sandNE";

            tile[26] = new Tile();
            tile[26].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileNW.png"));
            tile[26].collision = true;
            tile[26].requiredTool = null;
            tile[26].setTile = 1;
            tile[26].health = 0;
            tile[26].name = "sandNW";

            tile[27] = new Tile();
            tile[27].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileSE.png"));
            tile[27].collision = true;
            tile[27].requiredTool = null;
            tile[27].setTile = 1;
            tile[27].health = 0;
            tile[27].name = "sandSE";

            tile[28] = new Tile();
            tile[28].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/sand_tileSW.png"));
            tile[28].collision = true;
            tile[28].requiredTool = null;
            tile[28].setTile = 1;
            tile[28].health = 0;
            tile[28].name = "sandSW";

            tile[29] = new Tile();
            tile[29].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/water_tile2.png"));
            tile[29].collision = true;
            tile[29].requiredTool = null;
            tile[29].setTile = 1;
            tile[29].health = 0;
            tile[29].name = "water2";

            tile[30] = new Tile();
            tile[30].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/shell_decal1.png"));
            tile[30].collision = true;
            tile[30].requiredTool = null;
            tile[30].setTile = 3;
            tile[30].health = 0;
            tile[30].name = "shell1";

            tile[31] = new Tile();
            tile[31].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/shell_decal2.png"));
            tile[31].collision = true;
            tile[31].requiredTool = null;
            tile[31].setTile = 3;
            tile[31].health = 0;
            tile[31].name = "shell2";

            tile[32] = new Tile();
            tile[32].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/shell_decal3.png"));
            tile[32].collision = true;
            tile[32].requiredTool = null;
            tile[32].setTile = 3;
            tile[32].health = 0;
            tile[32].name = "shell3";

            tile[33] = new Tile();
            tile[33].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileN.png"));
            tile[33].collision = false;
            tile[33].requiredTool = null;
            tile[33].setTile = 1;
            tile[33].health = 0;
            tile[33].name = "grassN";

            tile[34] = new Tile();
            tile[34].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileS.png"));
            tile[34].collision = false;
            tile[34].requiredTool = null;
            tile[34].setTile = 1;
            tile[34].health = 0;
            tile[34].name = "grassS";

            tile[35] = new Tile();
            tile[35].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileE.png"));
            tile[35].collision = false;
            tile[35].requiredTool = null;
            tile[35].setTile = 1;
            tile[35].health = 0;
            tile[35].name = "grassE";

            tile[36] = new Tile();
            tile[36].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileW.png"));
            tile[36].collision = false;
            tile[36].requiredTool = null;
            tile[36].setTile = 1;
            tile[36].health = 0;
            tile[36].name = "grassW";

            tile[37] = new Tile();
            tile[37].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileNW.png"));
            tile[37].collision = false;
            tile[37].requiredTool = null;
            tile[37].setTile = 1;
            tile[37].health = 0;
            tile[37].name = "grassNW";

            tile[38] = new Tile();
            tile[38].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileNE.png"));
            tile[38].collision = false;
            tile[38].requiredTool = null;
            tile[38].setTile = 1;
            tile[38].health = 0;
            tile[38].name = "grassNE";

            tile[39] = new Tile();
            tile[39].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileSE.png"));
            tile[39].collision = false;
            tile[39].requiredTool = null;
            tile[39].setTile = 1;
            tile[39].health = 0;
            tile[39].name = "grassSE";

            tile[40] = new Tile();
            tile[40].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/grass_tileSW.png"));
            tile[40].collision = false;
            tile[40].requiredTool = null;
            tile[40].setTile = 1;
            tile[40].health = 0;
            tile[40].name = "grassSW";

            tile[41] = new Tile();
            tile[41].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileN.png"));
            tile[41].collision = false;
            tile[41].requiredTool = null;
            tile[41].setTile = 1;
            tile[41].health = 0;
            tile[41].name = "dirtN";

            tile[42] = new Tile();
            tile[42].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileS.png"));
            tile[42].collision = false;
            tile[42].requiredTool = null;
            tile[42].setTile = 1;
            tile[42].health = 0;
            tile[42].name = "dirtS";

            tile[43] = new Tile();
            tile[43].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileE.png"));
            tile[43].collision = false;
            tile[43].requiredTool = null;
            tile[43].setTile = 1;
            tile[43].health = 0;
            tile[43].name = "dirtE";

            tile[44] = new Tile();
            tile[44].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileW.png"));
            tile[44].collision = false;
            tile[44].requiredTool = null;
            tile[44].setTile = 1;
            tile[44].health = 0;
            tile[44].name = "dirtW";

            tile[45] = new Tile();
            tile[45].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileNE.png"));
            tile[45].collision = false;
            tile[45].requiredTool = null;
            tile[45].setTile = 1;
            tile[45].health = 0;
            tile[45].name = "dirtNE";

            tile[46] = new Tile();
            tile[46].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileNW.png"));
            tile[46].collision = false;
            tile[46].requiredTool = null;
            tile[46].setTile = 1;
            tile[46].health = 0;
            tile[46].name = "dirtNW";

            tile[47] = new Tile();
            tile[47].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileSE.png"));
            tile[47].collision = false;
            tile[47].requiredTool = null;
            tile[47].setTile = 1;
            tile[47].health = 0;
            tile[47].name = "dirtSE";

            tile[48] = new Tile();
            tile[48].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/dirt_tileSW.png"));
            tile[48].collision = false;
            tile[48].requiredTool = null;
            tile[48].setTile = 1;
            tile[48].health = 0;
            tile[48].name = "dirtSW";

            tile[49] = new Tile();
            tile[49].image = ImageIO.read(getClass().getResourceAsStream("/assets/Tiles/bridge_tile.png"));
            tile[49].collision = false;
            tile[49].requiredTool = ItemType.AXE;
            tile[49].setTile = 1;
            tile[49].health = 0;
            tile[49].name = "bridge";

        } catch (IOException e) {
        }
    }
    public boolean isWaterTile(Tile tile){
        return tile.name.equals("water") || tile.name.equals("sandNE") || tile.name.equals("sandN") || 
        tile.name.equals("water2") || tile.name.equals("sandE") || tile.name.equals("sandW") || 
        tile.name.equals("sandS") || tile.name.equals("sandNW") || tile.name.equals("sandSW") || tile.name.equals("sandSE");
    }
}
