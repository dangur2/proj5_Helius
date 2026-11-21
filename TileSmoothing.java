public class TileSmoothing {
    private final int baseTile;
    private final int toTile;
    private final int[] smoothAgainst;
    private final int nTile;
    private final int sTile;
    private final int eTile;
    private final int wTile;
    private final int neTile;
    private final int nwTile;
    private final int seTile;
    private final int swTile;

    public TileSmoothing(int baseTile, int toTile, int[] smoothAgainst, int nTile, int sTile, int eTile, int wTile, int neTile, int nwTile, int seTile, int swTile){
        this.baseTile = baseTile;
        this.toTile = toTile;
        this.smoothAgainst = smoothAgainst;
        this.nTile = nTile;
        this.sTile = sTile;
        this.eTile = eTile;
        this.wTile = wTile;
        this.neTile = neTile;
        this.nwTile = nwTile;
        this.seTile = seTile;
        this.swTile = swTile;
    }

    public int getBaseTile() {
        return baseTile;
    }

    public int getToTile() {
        return toTile;
    }
    public int[] getSmoothAgainst(){
        return smoothAgainst;
    }

    public int getnTile() {
        return nTile;
    }

    public int getsTile() {
        return sTile;
    }

    public int geteTile() {
        return eTile;
    }

    public int getwTile() {
        return wTile;
    }

    public int getNeTile() {
        return neTile;
    }

    public int getNwTile() {
        return nwTile;
    }

    public int getSeTile() {
        return seTile;
    }

    public int getSwTile() {
        return swTile;
    }
}
