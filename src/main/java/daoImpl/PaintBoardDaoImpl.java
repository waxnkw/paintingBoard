package daoImpl;

import dao.PaintBoardDao;
import entity.PaintBoardEntity;
import util.SerFileUtility;

/**
 * @author waxnkw
 * @version 2018.9.24
 *画板信息存储和单开的daoImpl
 * */
public class PaintBoardDaoImpl implements PaintBoardDao {
    private SerFileUtility serFileUtility = SerFileUtility.getInstance();

    public PaintBoardEntity openPaintBoardByPath(String path) {
        PaintBoardEntity board = (PaintBoardEntity)serFileUtility.read(path);
        return board;
    }

    public void savePaintBoardByPath(PaintBoardEntity board, String path) {
        serFileUtility.write(board, path);
    }
}
