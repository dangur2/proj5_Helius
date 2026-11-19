
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

    public TileManager(GamePanel gp, MapType mapType) {
        this.gp = gp;
        tile = new Tile[15];
        this.mapTileNum = new int[gp.worldCol][gp.worldRow];
        this.decorationTileNum = new int[gp.worldCol][gp.worldRow];
        this.isBlocked = new boolean[gp.worldSizeX][gp.worldSizeX];

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

    public boolean IsBlocked(int x, int y) {
        return isBlocked[x][y] == true;
    }

    public Tile getTileNum(int x, int y) {
        return tc.getTile(mapTileNum[(int) x][(int) y]);
    }

    public boolean isSolid(double worldX, double worldY) {
        //kollar bounds åt spelaren, sålänge den inte överskrider världstorleken
        if (worldX < 0 || worldY < 0 || worldX >= gp.worldSizeX || worldY >= gp.worldSizeY) {
            return true;
        }
        return tc.getTile(mapTileNum[(int) worldX / gp.tileSize][(int) worldY / gp.tileSize]).collision || IsBlocked((int) worldX, (int) worldY);
    }

    public void setTile(int x, int y, int toTile) {
        if (x <= gp.worldCol && x > 0 || y > 0 && y <= gp.worldRow) {
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
        return !(isSolid(leftX, topY) || isSolid(rightX, topY) || isSolid(leftX, bottomY) || isSolid(rightX, bottomY));
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
                } else if (value < 0.50) {
                    mapTileNum[j][i] = 18;//gräs2
                } else if (value < 0.60) {
                    mapTileNum[j][i] = 17;//gräs3
                } else if (value < 0.70) {
                    mapTileNum[j][i] = 16;//gräs4
                } else if (value < 0.85) {
                    mapTileNum[j][i] = 0;//gräs
                } else {
                    mapTileNum[j][i] = 4;//jord
                }
            }
        }
    }

    private void smoothMap() {
        int[][] tilesToSwap = new int[gp.worldCol][gp.worldRow];
        for (int i = 0; i < gp.worldRow; i++) {
            for (int j = 0; j < gp.worldCol; j++) {
                int currTile = mapTileNum[j][i];
                if (currTile != 1) {
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
                    if (borders[7] != 1 && borders[6] != 1 && borders[5] != 1) { //north, south, west
                        tilesToSwap[j][i] = 3; //23
                    } else if (borders[7] != 1 && borders[6] != 1 && borders[4] != 1) {
                        tilesToSwap[j][i] = 3; //22
                    } else if (borders[5] != 1 && borders[6] != 1 && borders[4] != 1) {
                        tilesToSwap[j][i] = 3; //24
                    } else if (borders[5] != 1 && borders[7] != 1 && borders[4] != 1) {
                        tilesToSwap[j][i] = 3; //21
                    } else if (borders[7] != 1 && borders[6] != 1) { //north south
                        tilesToSwap[j][i] = 3; //34
                    } else if (borders[4] != 1 && borders[5] != 1) { //east west
                        tilesToSwap[j][i] = 3; //35
                    } else if (borders[5] != 1 && borders[7] != 1) { //west north
                        tilesToSwap[j][i] = 26;
                    } else if (borders[4] != 1 && borders[7] != 1) { //east north
                        tilesToSwap[j][i] = 25;
                    } else if (borders[4] != 1 && borders[6] != 1) { // east south
                        tilesToSwap[j][i] = 27;
                    } else if (borders[5] != 1 && borders[6] != 1) { //west south
                        tilesToSwap[j][i] = 28;
                    } else if (borders[5] != 1) { //w
                        tilesToSwap[j][i] = 22;
                    } else if (borders[4] != 1) { //e
                        tilesToSwap[j][i] = 21;
                    } else if (borders[7] != 1) { //n
                        tilesToSwap[j][i] = 24;
                    } else if (borders[6] != 1) { //s
                        tilesToSwap[j][i] = 23;
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
