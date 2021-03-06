package enums;

/**
 * @author waxnkw
 * @version 2018.9.24
 * 当前绘制状态的枚举类型
 * */
public enum  PaintStateEnum {
    TO_PAINT,//将要绘画,但未绘画
    PAINTING;//正在绘画
    public String getText(){
        switch (this){
            case TO_PAINT:
                return "绘画";
            case PAINTING:
                return "结束";
        }
        return null;
    }
}
