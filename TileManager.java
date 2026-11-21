
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class TileManager {

    private final Tile[] tile;
    private final GamePanel gp;
    private final int mapTileNum[][];
    private final int decorationTileNum[][];
    private final TileCatalog tc = new TileCatalog();
    private final Random random = new Random(123);
    private boolean isBlocked[][];
    private boolean hasBridge[][];

    public TileManager(GamePanel gp, MapType mapType) {
        this.gp = gp;
        tile = new Tile[15];
        this.mapTileNum = new int[gp.worldCol][gp.worldRow];
        this.decorationTileNum = new int[gp.worldCol][gp.worldRow];
        this.isBlocked = new boolean[gp.worldSizeX][gp.worldSizeX];
        this.hasBridge = new boolean[gp.worldSizeX][gp.worldSizeX];

        switch (mapType) {
            case GENERATED -> {
                generativeMap();
                generateFlora();
            }
            case MAP ->
                loadMap();
        }
        smoothMap();

    }

    public void setIsBlocked(Rectangle collision) {
        for (int i = (int) collision.getY(); i < (int) collision.getY() + (int) collision.getHeight(); i++) {
            for (int j = (int) collision.getX(); j < (int) collision.getX() + (int) collision.getWidth(); j++) {
                isBlocked[j][i] = true;
            }
        }
    }

    public void removeIsBlocked(Rectangle collision) {
        for (int i = (int) collision.getY(); i < (int) collision.getY() + (int) collision.getHeight(); i++) {
            for (int j = (int) collision.getX(); j < (int) collision.getX() + (int) collision.getWidth(); j++) {
                isBlocked[j][i] = false;
            }
        }
    }
    public void setBridge(int x, int y){
        if (x > 0 && x < gp.worldCol && y > 0 && y < gp.worldRow) {
            hasBridge[x][y] = true;
        }
        
    }
    public void removeBridge(int x, int y){
        if (x > 0 && x < gp.worldCol && y > 0 && y < gp.worldRow) {
            hasBridge[x][y] = false;
        }
    }
    public boolean isBridge(int x, int y){
        return hasBridge[x][y] == true;
    }
    public boolean IsBlocked(int x, int y) {
        return isBlocked[x][y] == true;
    }

    public Tile getTileNum(int x, int y) {
        return tc.getTile(mapTileNum[(int) x][(int) y]);
    }
    public boolean isWaterTile(int x, int y){
        return tc.isWaterTile(getTileNum(x, y));
    }

    public boolean isSolid(double worldX, double worldY) {
        //kollar bounds åt spelaren, sålänge den inte överskrider världstorleken
        if (worldX < 0 || worldY < 0 || worldX >= gp.worldSizeX || worldY >= gp.worldSizeY) {
            return true;
        }
        if (isBridge((int)worldX / gp.tileSize, (int) worldY / gp.tileSize)) {
            return false;
        }
        return tc.getTile(mapTileNum[(int) worldX / gp.tileSize][(int) worldY / gp.tileSize]).collision || IsBlocked((int) worldX, (int) worldY);
    }

    public void setTile(int x, int y, int toTile) {
        if (x <= gp.worldCol && x > 0 && y > 0 && y <= gp.worldRow) {
            mapTileNum[x][y] = toTile;
            decorationTileNum[x][y] = 0;
        }
    }

    //kollar collision åt spelaren
    public boolean canStandAt(double x, double y, Rectangle Hitbox) {
        double leftX = x + Hitbox.x;
        double rightX = leftX + Hitbox.width - 1;
        double topY = y + Hitbox.y;
        double bottomY = topY + Hitbox.height - 1;

        //true om någon av hörnen inte nuddar en solid tile
        return !(isSolid(leftX, topY) || isSolid(rightX, topY) || isSolid(leftX, bottomY) || isSolid(rightX, bottomY)) ;
    }

    public Point findFreePosition() {
        int worldPadding = 15;
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(worldPadding, gp.worldCol - worldPadding) * gp.tileSize;
            int y = random.nextInt(worldPadding, gp.worldRow - worldPadding) * gp.tileSize;
            if (!(isSolid(x, y)) && !(getTileNum((int) x / gp.tileSize, (int) y / gp.tileSize) == tile[3])) {
                return new Point(x, y);
            }
        }
        return null;
    }

    private void generativeMap() {
        int spacing = 10;
        int gridCols = (gp.worldCol / spacing) + 2; //dela upp världen i en mindre rutnät: var tioende tile har en random siffra, som "flaggor"
        int gridRows = (gp.worldRow / spacing) + 2;
        double grid[][] = new double[gridCols][gridRows];

        for (int a = 0; a < gridRows; a++) {
            for (int b = 0; b < gridCols; b++) {
                grid[b][a] = random.nextDouble(); //sätter en random 0-1 siffra på varje "flagga" i världen
            }
        }
        for (int i = 0; i < gp.worldRow; i++) {
            for (int j = 0; j < gp.worldCol; j++) {
                int a = j / spacing; //vilken ruta den aktuella tilen tillhör i det mindre 9x9
                int b = i / spacing;

                //detta räknar ut tilens nuvarande position till flaggan, från 0 till 1. 0 = vänster flagga, 0.9 = höger flagga
                double tx = (j % spacing) / (double) spacing;  //2 % 10 = 2 / 10 = 0.2, alltså värdet på tilen i korrelation mellan vänster och höger flagga. 
                double ty = (i % spacing) / (double) spacing;  // samma som ovan för Y

                //linear interpolation
                double top = lerp(grid[a][b], grid[a + 1][b], tx); //rutans övre kant, som blandar värde mellan vänster och höger flagga
                double bottom = lerp(grid[a][b + 1], grid[a + 1][b + 1], tx); //rutans undre kant, gör samma sak
                double value = lerp(top, bottom, ty); //blandar övre och undre kanten för att mjukt värde

                if (value < 0.32) {
                    mapTileNum[j][i] = 1; //vatten
                } else if (value < 0.40) {
                    mapTileNum[j][i] = 3;//sand
                } else if (value < 0.85) {
                    mapTileNum[j][i] = 0;//gräs
                } else {
                    mapTileNum[j][i] = 4;//jord
                }
            }
        }
    }
    private boolean smoothAgainst(int[] smoothAgainst, int tile){ 
        for (int i : smoothAgainst) {
            if (i == tile) {
                return true;
            }
        } 
        return false;
    }
    private void smoothMap() {
        int[][] tilesToSwap = new int[gp.worldCol][gp.worldRow];
        int[][] tilesToSmooth = new int[gp.worldCol][gp.worldRow];
        ArrayList<TileSmoothing> smoothingTiles = new ArrayList<>();
        smoothingTiles.add(new TileSmoothing(1, 3,new int[]{3},24,23,21,22,25,26,27,28));
        smoothingTiles.add(new TileSmoothing(0, 3,new int[]{3},33,34,35,36,38,37,39,40));
        smoothingTiles.add(new TileSmoothing(4, 0,new int[]{0},41,42,43,44,45,46,47,48));
        for (int i = 0; i < gp.worldRow; i++) {
            for (int j = 0; j < gp.worldCol; j++) {
                int currTile = mapTileNum[j][i];
                for (TileSmoothing t : smoothingTiles) {
                    if (currTile != t.getBaseTile()) {
                        continue;
                    }
                    if (j > 0 && j < (gp.worldCol - 1) && i > 0 && i < (gp.worldRow - 1)) {
                    int[] borders = {
                        mapTileNum[j - 1][i - 1], //nw : 0
                        mapTileNum[j + 1][i - 1], //ne : 1
                        mapTileNum[j - 1][i + 1], //sw : 2
                        mapTileNum[j + 1][i + 1], //se : 3
                        mapTileNum[j + 1][i], //e : 4
                        mapTileNum[j - 1][i], //w : 5
                        mapTileNum[j][i + 1], //s : 6
                        mapTileNum[j][i - 1], //n : 7
                    };
                    if (smoothAgainst(t.getSmoothAgainst(), borders[7]) && smoothAgainst(t.getSmoothAgainst(), borders[6]) && smoothAgainst(t.getSmoothAgainst(), borders[5]) && smoothAgainst(t.getSmoothAgainst(), borders[4])) { //north, south, west
                        tilesToSmooth[j][i] = t.getToTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[7]) && smoothAgainst(t.getSmoothAgainst(), borders[6]) && smoothAgainst(t.getSmoothAgainst(), borders[5])) { //north, south, west
                        tilesToSmooth[j][i] = t.getToTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[7]) && smoothAgainst(t.getSmoothAgainst(), borders[6]) && smoothAgainst(t.getSmoothAgainst(), borders[4])) {
                        tilesToSmooth[j][i] = t.getToTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[5]) && smoothAgainst(t.getSmoothAgainst(), borders[6]) && smoothAgainst(t.getSmoothAgainst(), borders[4])) {
                        tilesToSmooth[j][i] = t.getToTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[5]) && smoothAgainst(t.getSmoothAgainst(), borders[7]) && smoothAgainst(t.getSmoothAgainst(), borders[4])) {
                        tilesToSmooth[j][i] = t.getToTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[7]) && smoothAgainst(t.getSmoothAgainst(), borders[6])) { //north south
                        tilesToSmooth[j][i] = t.getToTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[4]) && smoothAgainst(t.getSmoothAgainst(), borders[5])) { //east west
                        tilesToSmooth[j][i] = t.getToTile();
                    }
                }
            }
            }
        }
        for (int z = 0; z < gp.worldRow; z++) {
            for (int y = 0; y < gp.worldCol; y++) {
                if (tilesToSmooth[y][z] != 0) {
                    setTile(y, z, tilesToSmooth[y][z]);
                }
            }
        }
        for (int k = 0; k < gp.worldRow; k++) {
            for (int l = 0; l < gp.worldCol; l++) {
                int currTile = mapTileNum[l][k];
                for (TileSmoothing t : smoothingTiles) {
                    if (currTile != t.getBaseTile()) {
                    continue;
                }
                if (l > 0 && l < (gp.worldCol - 1) && k > 0 && k < (gp.worldRow - 1)) {
                    int[] borders = {
                        mapTileNum[l - 1][k - 1], //nw : 0
                        mapTileNum[l + 1][k - 1], //ne : 1
                        mapTileNum[l - 1][k + 1], //sw : 2
                        mapTileNum[l + 1][k + 1], //se : 3
                        mapTileNum[l + 1][k], //e : 4
                        mapTileNum[l - 1][k], //w : 5
                        mapTileNum[l][k + 1], //s : 6
                        mapTileNum[l][k - 1], //n : 7
                    };
                    if (smoothAgainst(t.getSmoothAgainst(), borders[5]) && smoothAgainst(t.getSmoothAgainst(), borders[7])) { //west north
                        tilesToSwap[l][k] = t.getNwTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[4]) && smoothAgainst(t.getSmoothAgainst(), borders[7])) { //east north
                        tilesToSwap[l][k] = t.getNeTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[4]) && smoothAgainst(t.getSmoothAgainst(), borders[6])) { // east south
                        tilesToSwap[l][k] = t.getSeTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[5]) && smoothAgainst(t.getSmoothAgainst(), borders[6])) { //west south
                        tilesToSwap[l][k] = t.getSwTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[5])) { //w
                        tilesToSwap[l][k] = t.getwTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[4])) { //e
                        tilesToSwap[l][k] = t.geteTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[7])) { //n
                        tilesToSwap[l][k] = t.getnTile();
                    } else if (smoothAgainst(t.getSmoothAgainst(), borders[6])) { //s
                        tilesToSwap[l][k] = t.getsTile();
                    }
                }
            }
                }
        }
        for (int z = 0; z < gp.worldRow; z++) {
            for (int y = 0; y < gp.worldCol; y++) {
                if (tilesToSwap[y][z] != 0) {
                    setTile(y, z, tilesToSwap[y][z]);
                }
            }
        }
    }

    private void generateFlora() {
        for (int i = 0; i < gp.worldRow; i++) {
            for (int j = 0; j < gp.worldCol; j++) {
                if ((mapTileNum[j][i] == 0 || mapTileNum[j][i] == 16 || mapTileNum[j][i] == 17 || mapTileNum[j][i] == 18) && !IsBlocked(j, i)) {
                    if (random.nextInt(15) == 0) {
                        if (random.nextInt(25) == 0) {
                            decorationTileNum[j][i] = 10; //poppy
                        }
                        if (random.nextInt(5) == 0) {
                            decorationTileNum[j][i] = 13; //grass1
                        }
                        if (random.nextInt(10) == 0) {
                            decorationTileNum[j][i] = 14; //grass2
                        }
                        if (random.nextInt(8) == 0) {
                            decorationTileNum[j][i] = 15; //grass3
                        }
                        if (random.nextInt(12) == 0) {
                            decorationTileNum[j][i] = 19; //grass3
                        }
                        if (random.nextInt(15) == 0) {
                            decorationTileNum[j][i] = 20; //grass3
                        }
                        if (random.nextInt(50) == 0) {
                            decorationTileNum[j][i] = 11; //poppypatch
                        }
                        if (random.nextInt(20) == 0) {
                            decorationTileNum[j][i] = 7; //rose
                        }
                        if (random.nextInt(50) == 0) {
                            decorationTileNum[j][i] = 8; //rosepatch
                        }
                        if (random.nextInt(40) == 0) {
                            decorationTileNum[j][i] = 9; //leaves
                        }
                        if (random.nextInt(50) == 0) {
                            decorationTileNum[j][i] = 12; //leaves
                        }
                    }
                }
                if (mapTileNum[j][i] == 1) {
                    if (random.nextInt(15) == 0) {
                        if (random.nextInt(5) == 0) {
                            decorationTileNum[j][i] = 29;
                        }
                    }
                }
                if (mapTileNum[j][i] == 3 && !IsBlocked(j, i)) {
                    if (random.nextInt(15) == 0) {
                        if (random.nextInt(15) == 0) {
                            decorationTileNum[j][i] = 30;
                        }
                        if (random.nextInt(16) == 0) {
                            decorationTileNum[j][i] = 31;
                        }
                        if (random.nextInt(17) == 0) {
                            decorationTileNum[j][i] = 32;
                        }
                    }
                }
            }
        }
    }

    private double lerp(double a, double b, double t) {
        return a + (b - a) * t; //Linjär interpolation för att räkna ut ett värde mellan punkt A och punkt B.
        //T finns för att bestämma hur långt vi kommit mellan A och B. T:0 = A, T:0.5 = halvvägs till B, T:1 = B
    }

    private void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/Maps/tile_Map2.txt");) { //laddar in din textfil
            if (is == null) {
                throw new Exception("Map is null");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 0; i < gp.worldRow; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new Exception("line är null");
                }
                String numbers[] = line.split(" ");
                for (int j = 0; j < gp.worldCol; j++) {
                    int num = Integer.parseInt(numbers[j]);
                    mapTileNum[j][i] = num;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, Camera camera) {

        int startCol = Math.max(0, (int) camera.getTx() / gp.tileSize);
        int endCol = Math.min(gp.worldCol, ((int) camera.getTx() + gp.screenSizeX) / gp.tileSize + 1);
        int startRow = Math.max(0, (int) camera.getTy() / gp.tileSize);
        int endRow = Math.min(gp.worldRow, ((int) camera.getTy() + gp.screenSizeY) / gp.tileSize + 1);

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                //normala tiles
                g2.drawImage(tc.getTile(mapTileNum[j][i]).image, (j * (gp.tileSize) - (int) camera.getTx()), (i * (gp.tileSize) - (int) camera.getTy()), gp.tileSize, gp.tileSize, null);
                //dekoration över, som blommor
                if (decorationTileNum[j][i] != 0) {
                    g2.drawImage(tc.getTile(decorationTileNum[j][i]).image, (j * (gp.tileSize) - (int) camera.getTx()), (i * (gp.tileSize) - (int) camera.getTy()), gp.tileSize, gp.tileSize, null);
                }
            }

        }
    }

    public boolean canResourceSpawn(ArrayList<Integer> spawnableTiles, Tile selectedTile) {
        for (int i : spawnableTiles) {
            if (tc.getTile(i) == selectedTile) {
                return true;
            }
        }
        return false;
    }
}
