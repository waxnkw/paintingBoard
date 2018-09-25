package dao;

import entity.PaintBoardEntity;

/**
 * @author waxnkw
 * @version 2018.9.24
 *画板信息存储和单开的dao
 * */
public interface PaintBoardDao {
    /**
     * 根据路径打开文件
     * @param path 打开的路径
     * @return 读入的画板信息
     * */
    PaintBoardEntity openPaintBoardByPath(String path);

    /**
     * 根据路径储存文件
     * @param path 存储路径
     * @param board 需要存储的画板信息
     * */
    void savePaintBoardByPath(PaintBoardEntity board, String path);
}
