package entity;

import java.io.Serializable;

/**
 * @author waxnkw
 * @version 2018.9.24
 * 笔划,内部包含很多点
 * */
public class PaintStrokeEntity implements Serializable {
    private static final long serialVersionUID = -6596828031325134527L;

    private int [][] pos;
    private int index;

    public PaintStrokeEntity(){
        pos = new int[800][2];
        index = 0;
    }

    /**
     * 新增点
     * */
    public void addPoint(int x, int y){
        pos[index][0] = x;
        pos[index][1] = y;
        index++;
    }

    public int[][] getPos() {
        return pos;
    }

    /**
     * 得到当前笔划内点的数目
     * */
    public int getPointsNum(){
        return index;
    }

    /**
     * 得到位置i处的点坐标
     * @param i 位置i
     * @return 位置i点的坐标
     * */
    public int[] getPos(int i){
        return pos[i];
    }
}
