
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener{

    private ClickListener active;

    public MouseHandler() {
        
    }

    public void setActive(ClickListener active){
        this.active = active;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                active.OnLeftClick(e.getX(), e.getY());
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                active.OnRightClick(e.getX(), e.getY());
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        active.OnHover(e.getX(), e.getY());
    }
}
