package entity;

import enums.ShapeLabelEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author waxnkw
 * @version 2018.9.24
 * 图形,内部包含很多笔画和所述的形状种类(三角,矩形,圆)
 * */
public class PaintShapeEntity implements Serializable {
    private static final long serialVersionUID = 6983661637480340423L;

    private List<PaintStrokeEntity> strokes;
    private LabelEntity label;


    public PaintShapeEntity(){
        strokes = new ArrayList<>();
        label = new LabelEntity();
    }

    /**
     * getter and setter
     * */
    public List<PaintStrokeEntity> getStrokes() {
        return strokes;
    }

    public LabelEntity getLabel() {
        return label;
    }


    /**
     * 得到当前笔划数目
     * */
    public int getStrokeNum(){
        return strokes.size();
    }

    /**
     * 新增笔划
     * */
    public void addStroke(PaintStrokeEntity stroke){
        strokes.add(stroke);
    }

    /**
     * 根据label的Type确定具体的Label信息,包括label的位置
     * @param labelType label的种类
     * */
    public void setLabel(ShapeLabelEnum labelType) {
        label.setLabel(labelType);

        int x = 0;
        int y = 0;
        int len = strokes.size();
        //取每一笔划的中间点位置平均值,使其尽量在图形内
        for (PaintStrokeEntity stroke: strokes){
            int num = stroke.getPointsNum();
            int midPointX = stroke.getPos(num/2)[0];
            int midPointY = stroke.getPos(num/2)[1];
            x += midPointX;
            y += midPointY;
        }
        if (len == 0){return;}
        x/=len;
        y/=len;

        //如果是圆形则额外处理
        if (getStrokeNum()==1){
            int half = strokes.get(0).getPointsNum()/2;
            x = (strokes.get(0).getPos(0)[0]+strokes.get(0).getPos(half)[0])/2;
            y = (strokes.get(0).getPos(0)[1]+strokes.get(0).getPos(half)[1])/2;
        }

        label.setX(x);
        label.setY(y);
    }
}
