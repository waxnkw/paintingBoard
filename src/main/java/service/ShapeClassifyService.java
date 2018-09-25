package service;

import entity.PaintShapeEntity;
import enums.ShapeLabelEnum;

/**
 * @author zhangao
 * @version 2018.9.19
 * 形状识别
 * */
public interface ShapeClassifyService {
    /**
     * 识别该shape的具体形状
     * @param shape 待识别的形状
     * */
    ShapeLabelEnum classifyShape(PaintShapeEntity shape);

}
