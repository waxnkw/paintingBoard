import entity.PaintBoardEntity;
import entity.PaintShapeEntity;
import entity.PaintStrokeEntity;
import org.junit.Test;

public class ReadAndWriteSerTest {
    @Test
    public void testWriteSer(){
        PaintBoardEntity board = new PaintBoardEntity();
        PaintShapeEntity shape1 = new PaintShapeEntity();
        PaintStrokeEntity stroke11 = new PaintStrokeEntity();
        stroke11.addPoint(4,5 );
        shape1.addStroke(stroke11);
        board.addShape(shape1);

    }
}
