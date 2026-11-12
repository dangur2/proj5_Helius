
import java.awt.Point;

public class RenderObjects {
    private final int y;
    private final Object object;
    private final Point location;

    public RenderObjects(int y, Point location, Object object){
        this.y = y;
        this.location = location;
        this.object = object;
    }

    public int getY(){
        return y;
    }
    public Object getObject(){
        return object;
    }
    public Point getLocation(){
        return location;
    }
}
