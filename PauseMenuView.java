import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PauseMenuView{
    private final ArrayList<Rectangle> buttons = new ArrayList<>();
    private BufferedImage pauseBackground, unpauseButton, exitButton, gamePaused;
    private final GameEngine ge;

    public PauseMenuView(GameEngine ge){
        this.ge = ge;
        loadSprites();
        createBounds();
    }

    private void createBounds() {
        for (int i = 0; i < 2; i++) {
            buttons.add(new Rectangle(350,190 + (i * 60),100,60));
        }
    }

    private void loadSprites() {
        try {
            pauseBackground = ImageIO.read(getClass().getResourceAsStream("/assets/Background/pauseScreen.png"));
            unpauseButton = ImageIO.read(getClass().getResourceAsStream("/assets/Font/resumeButton.png"));
            exitButton = ImageIO.read(getClass().getResourceAsStream("/assets/Font/pauseExitButton.png"));
            gamePaused = ImageIO.read(getClass().getResourceAsStream("/assets/Font/gamePaused.png"));
        } catch (Exception e) {
        }
    }
    public void LeftClick(int x, int y){
        for (int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).contains(x,y)){
                switch (i) {
                    case 0 -> { //resume
                            ge.setCurrentState(GameState.PLAYING);
                    }
                    case 1 -> { //exit
                        ge.setCurrentState(GameState.MENU);
                    }
                }
            }
        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(pauseBackground, 300, 100, null);
        g2.drawImage(gamePaused, 317, 132, null);
        g2.drawImage(unpauseButton, 350, 190, null);
        g2.drawImage(exitButton, 350, 250, null);
    }
}