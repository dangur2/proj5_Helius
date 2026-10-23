
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private final TileManager tm;
    private final Player player;
    private final Camera camera;
    private final InventoryView iv;
    private final WorldSpawn worldSpawn;
    private final PlayerHealthView phv;
    private final GameEngine ge;
    private final PauseMenuView pauseMenu;

    public GamePanel(MapType mapType, MouseHandler mh, GameEngine ge) {
        this.tm = new TileManager(this, mapType);
        this.player = new Player(tm, this);
        this.camera = new Camera(worldSizeX, worldSizeY, screenSizeX, screenSizeY);
        this.worldSpawn = new WorldSpawn(tm);
        this.phv = new PlayerHealthView();
        this.iv = new InventoryView(player, this);
        this.ge = ge;
        this.pauseMenu = new PauseMenuView(ge);
        this.kh = new KeyHandler(ge);

        this.addKeyListener(kh);
        this.addMouseListener(mh);
        this.addMouseMotionListener(mh);

        setPreferredSize(new Dimension(screenSizeX, screenSizeY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tm.draw(g2, camera);
        worldSpawn.draw(g2, camera);
        player.draw(g2, camera);
        iv.draw(g2);
        phv.draw(g2, player.getHealth());
        if (ge.getState() == GameState.PAUSE) {
            pauseMenu.draw(g2);
        }

    }

    public void tick(double dt) {
        //medans spelaren lever
        if (!player.isDead()) {
            player.move(dt, kh.getDx(), kh.getDy(), worldSizeX, worldSizeY, tm);
            player.updateAttack();
            camera.follow(player);
            worldSpawn.handleCollision(player, kh.isePressed(), this);
        }
        repaint();
    }

    public void isPaused() {
        repaint();
    }

    @Override
    public void OnLeftClick(int x, int y) {
        switch (ge.getState()) {
            case GameState.PLAYING -> {
                iv.LeftClick(x, y);
                player.LeftClick(x, y, iv.getSelectedItem(), iv.getSlots(), camera.getTx(), camera.getTy());
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
        switch (ge.getState()) {
            case GameState.PLAYING -> {
                iv.RightClick(x, y);
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
        switch (ge.getState()) {
            case GameState.PLAYING -> {
                iv.Hover(x, y);
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
        ge.playSound(i);
    }

}
