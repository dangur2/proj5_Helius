



public class RenderObjects {
    private final int y;
    private final Object object;
    private final int x;

    public RenderObjects(int y, int x, Object object){
        this.y = y;
        this.x = x;
        this.object = object;
    }

    public int getY(){
        return y;
    }
    public Object getObject(){
        return object;
    }
    public int getX(){
        return x;
    }
}
