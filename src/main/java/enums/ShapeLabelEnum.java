package enums;

/**
 * @author waxnkw
 * @version 2018.9.24
 * 图形形状的枚举类型
 * */
public enum ShapeLabelEnum {
    UNKNOWN,SQUARE,CIRCLE,TRIANGLE;
    public String getText(){
        switch (this){
            case CIRCLE:
                return "圆";
            case SQUARE:
                return "矩形";
            case TRIANGLE:
                return "三角";
            case UNKNOWN:
                return "不知道";
        }
        return null;
    }
}
