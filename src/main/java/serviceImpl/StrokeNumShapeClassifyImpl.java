package serviceImpl;

import entity.PaintShapeEntity;
import enums.ShapeLabelEnum;
import service.ShapeClassifyService;

/**
 * @author zhangao
 * @version 2018.9.19
 * 基于笔画的简单形状识别
 * */
public class StrokeNumShapeClassifyImpl implements ShapeClassifyService {
    @Override
    public ShapeLabelEnum classifyShape(PaintShapeEntity shape) {
            switch (shape.getStrokeNum()){
                case 1:
                    return ShapeLabelEnum.CIRCLE;
                case 3:
                    return ShapeLabelEnum.TRIANGLE;
                case 4:
                    return ShapeLabelEnum.SQUARE;
                default:
                    return ShapeLabelEnum.UNKNOWN;
            }
    }
}
