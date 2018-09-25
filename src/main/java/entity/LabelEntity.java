package entity;

import enums.ShapeLabelEnum;

import java.io.Serializable;

/**
 * @author waxnkw
 * @version 2018.9.24
 *图形的标签信息
 * */
public class LabelEntity implements Serializable {

    private static final long serialVersionUID = -7888605236883463655L;

    //label在画板中的位置
    private int x;
    private int y;
    //具体的形状
    private ShapeLabelEnum labelType;

    /**
     * getter and setter
     * */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLabel(ShapeLabelEnum labelType) {
        this.labelType = labelType;
    }

    /**
     * 得到当前标签的文本信息
     * @return 当前标签的文本
     * */
    public String getText(){
        return labelType.getText();
    }

}
