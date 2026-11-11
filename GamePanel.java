
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ClickListener {

    public final int tileSize = 32;
    public final int screenCol = 800 / tileSize;
    public final int screenRow = 480 / tileSize;
    public final int screenSizeX = screenCol * tileSize;
    public final int screenSizeY = screenRow * tileSize;
    public final int worldCol = 150;
    public final int worldRow = 150;
    public final int worldSizeX = worldCol * tileSize;
    public final int worldSizeY = worldRow * tileSize;

    private final KeyHandler kh;
    private final TileManager tileManager;
    private final Player player;
    private final Camera camera;
    private final HudView hudView;
    private final WorldSpawn worldSpawn;
    private final PlayerHealthView playerHealthView;
    private final GameEngine gameEngine;
    private final PauseMenuView pauseMenu;
    private final MouseHandler mouseHandler;
    private final InventoryViewManager inventoryViewManager;
    private final ItemCatalog itemCatalog;

    private ArrayList<RenderObjects> ySort = new ArrayList<>();

    public GamePanel(MapType mapType, MouseHandler mh, GameEngine ge) {
        this.itemCatalog = new ItemCatalog();
        this.tileManager = new TileManager(this, mapType);
        this.player = new Player(tileManager, this);
        this.camera = new Camera(worldSizeX, worldSizeY, screenSizeX, screenSizeY);
        this.playerHealthView = new PlayerHealthView();
        this.hudView = new HudView(player, this);
        this.gameEngine = ge;
        this.pauseMenu = new PauseMenuView(ge);
        this.mouseHandler = mh;
        this.worldSpawn = new WorldSpawn(tileManager, this, itemCatalog, player);
        this.inventoryViewManager = new InventoryViewManager(player.getInventory(), itemCatalog);
        this.kh = new KeyHandler(ge, hudView, inventoryViewManager);

        this.addKeyListener(kh);
        this.addMouseListener(mh);
        this.addMouseMotionListener(mh);

        setPreferredSize(new Dimension(screenSizeX, screenSizeY));
    }

    public void setRenderObjects(){
        ySort = new ArrayList<>();
        for (Point i : worldSpawn.getGroundResources().keySet()) {
            ySort.add(new RenderObjects((int) i.getY(),(int) i.getX(), worldSpawn.getGroundResources().get(i)));
        }
        for (Point i : worldSpawn.getPlacedFurniture().keySet()) {
            ySort.add(new RenderObjects((int) i.getY(),(int) i.getX(), worldSpawn.getPlacedFurniture().get(i)));
        }
        ySort.add(new RenderObjects( (int) player.getY()+player.getPlayerY(), (int) player.getX(), player));
        ySort.sort(Comparator.comparingInt(r -> r.getY()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2, camera);
        for (RenderObjects i : ySort) {
            if (i.getObject() instanceof Player) {
                player.draw(g2, camera);
            } else if (i.getObject() instanceof PlacedFurniture currFur){
                currFur.draw(g2, camera, (int) i.getX(), (int) i.getY());
            } else if (i.getObject() instanceof PlacedResource resource){
                g2.drawImage(resource.getResource().image, (int) i.getX(), (int) i.getY(), null);
            }
        }
        if (!inventoryViewManager.currentlyDrawing()) {
            hudView.draw(g2);
            playerHealthView.draw(g2, player.getHealth(), player.getStamina());
        }
        if (inventoryViewManager.currentlyDrawing()) {
            inventoryViewManager.draw(g2);
        }
        if (gameEngine.getState() == GameState.PAUSE) {
            pauseMenu.draw(g2);
        }

    }

    public void tick(double dt) {
        setRenderObjects();
        if (!inventoryViewManager.currentlyDrawing()) {
            player.move(dt, kh.getDx(), kh.getDy(), worldSizeX, worldSizeY, tileManager);
            player.updateTarget(mouseHandler.getMx(), mouseHandler.getMy(), camera.getTx(), camera.getTy(), hudView.getSelectedItem());
            player.updateAttack();
            camera.follow(player);
            worldSpawn.handleCollision(player, kh.isePressed(), this, mouseHandler.getMx(), mouseHandler.getMy());
            worldSpawn.tick(dt);
        }
        repaint();
    }

    public void isPaused() {
        repaint();
    }

    @Override
    public void OnLeftClick(int x, int y) {
        switch (gameEngine.getState()) {
            case GameState.PLAYING -> {
                if (inventoryViewManager.currentlyDrawing()) {
                    inventoryViewManager.LeftClick(x, y);
                } else {
                    hudView.LeftClick(x, y);
                    player.LeftClick(x, y, hudView.getSelectedSlot(), hudView.getSlots(), worldSpawn);
                }
            }
            case GameState.PAUSE -> {
                pauseMenu.LeftClick(x, y);
            }
            case GameState.LOSE -> {
            }
            case GameState.MENU -> {
            }
        }
    }

    @Override
    public void OnRightClick(int x, int y) {
        switch (gameEngine.getState()) {
            case GameState.PLAYING -> {
                if (!inventoryViewManager.currentlyDrawing()) {
                    hudView.RightClick(x, y);
                    player.RightClick(x, y, worldSpawn, hudView.getSlots());
                    if (worldSpawn.getChest() != null) {
                        inventoryViewManager.setChest(worldSpawn.getChest());
                    }
                } else {
                    inventoryViewManager.RightClick(x, y);
                }
            }
            case GameState.PAUSE -> {

            }
            case GameState.LOSE -> {
            }
            case GameState.MENU -> {
            }
        }
    }

    @Override
    public void OnHover(int x, int y) {
        switch (gameEngine.getState()) {
            case GameState.PLAYING -> {
                if (inventoryViewManager.currentlyDrawing()) {
                    inventoryViewManager.Hover(x, y);
                } else {
                    hudView.Hover(x, y);
                }

            }
            case GameState.PAUSE -> {
            }
            case GameState.LOSE -> {
            }
            case GameState.MENU -> {
            }
        }
    }

    public void playSound(SoundEffect i) {
        gameEngine.playSound(i);
    }

}
