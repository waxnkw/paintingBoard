package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author waxnkw
 * @version 2018.9.24
 *画板,内部包含多个图形
 * */
public class PaintBoardEntity implements Serializable {

    private static final long serialVersionUID = 3136118935588519441L;

    private List<PaintShapeEntity> shapes;

    public PaintBoardEntity(){
        shapes = new ArrayList<>();
    }

    public List<PaintShapeEntity> getShapes() {
        return shapes;
    }

    /**
     * 新增shape
     * @param shape 新增的shape
     * */
    public void addShape(PaintShapeEntity shape){
        shapes.add(shape);
    }
}
