
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean aPressed, dPressed, wPressed, sPressed, ePressed, escPressed;
    private double dx, dy;
    private final GameEngine ge;
    private final InventoryView iv;

    public KeyHandler(GameEngine ge, InventoryView iv) {
        this.ge = ge;
        this.iv = iv;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean isdPressed() {
        return dPressed;
    }

    public boolean iswPressed() {
        return wPressed;
    }

    public boolean issPressed() {
        return sPressed;
    }

    public boolean isePressed() {
        return ePressed;
    }

    public boolean isescPressed() {
        return escPressed;
    }

    public void setescPressedFalse() {
        this.escPressed = false;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A ->
                aPressed = true;
            case KeyEvent.VK_D ->
                dPressed = true;
            case KeyEvent.VK_W ->
                wPressed = true;
            case KeyEvent.VK_S ->
                sPressed = true;
            case KeyEvent.VK_E ->
                ePressed = true;
            case KeyEvent.VK_1 ->
                iv.setSelectItem(0);
            case KeyEvent.VK_2 ->
                iv.setSelectItem(1);
            case KeyEvent.VK_3 ->
                iv.setSelectItem(2);
            case KeyEvent.VK_4 ->
                iv.setSelectItem(3);
            case KeyEvent.VK_5 ->
                iv.setSelectItem(4);
            case KeyEvent.VK_6 ->
                iv.setSelectItem(5);
            case KeyEvent.VK_7 ->
                iv.setSelectItem(6);
            case KeyEvent.VK_8 ->
                iv.setSelectItem(7);
            case KeyEvent.VK_9 ->
                iv.setSelectItem(8);
            case KeyEvent.VK_ESCAPE -> {
                if (ge.getState() == GameState.PLAYING) {
                    ge.setCurrentState(GameState.PAUSE);
                } else if (ge.getState() == GameState.PAUSE) {
                    ge.setCurrentState(GameState.PLAYING);
                }
            }
        }
        updateDirection();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A ->
                aPressed = false;
            case KeyEvent.VK_D ->
                dPressed = false;
            case KeyEvent.VK_W ->
                wPressed = false;
            case KeyEvent.VK_S ->
                sPressed = false;
            case KeyEvent.VK_E ->
                ePressed = false;
        }
        updateDirection();
    }

    private void updateDirection() {
        dx = 0;
        dy = 0;

        if (wPressed && !sPressed) {
            dy = -1;
        } else if (sPressed && !wPressed) {
            dy = 1;
        }
        if (aPressed && !dPressed) {
            dx = -1;
        } else if (dPressed && !aPressed) {
            dx = 1;
        }
    }
}
