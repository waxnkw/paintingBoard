package ui;

import dao.PaintBoardDao;
import daoImpl.PaintBoardDaoImpl;
import entity.LabelEntity;
import entity.PaintBoardEntity;
import entity.PaintShapeEntity;
import entity.PaintStrokeEntity;
import enums.PaintStateEnum;
import enums.ShapeLabelEnum;
import service.ShapeClassifyService;
import serviceImpl.StrokeNumShapeClassifyImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class PaintingBoardFrame extends JFrame{
    /**
     * frame内部的组件
     * */
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JButton paintBtn;
    private DrawListener dl;
    private JFileChooser fileChooser;
    private JMenuBar menuBar;

    /**
     * 全局信息
     * */
    private String curFilePath;//当前存储的路径

    /**
     * 当前状态
     * */
    private boolean isEdited;//当前文件是否被更改过,用来判断是否需保存
    private PaintStateEnum curState;//当前的绘画状态

    /**
     * 当前画板内图片信息
     * */
    private PaintBoardEntity curBoard;
    private PaintShapeEntity curShape;
    private PaintStrokeEntity curStroke;

    /**
     * 需要使用的Service
     * */
    private ShapeClassifyService classifyService;
    private PaintBoardDao paintBoardDao;


    public PaintingBoardFrame(){
        super();
        this.setVisible(true);
    }

    public void init(){
        //当前绘画的状态
        this.curState = PaintStateEnum.TO_PAINT;

        //设置窗体
        initFrame();

        //设置中央绘画区域
        initCenterPanelAndDrawListener();

        //设置全局工作栏
        initLeftPanel();

        //设置存储和打开
        initMenu();

        //设置文件选择器
        initFileChooser();

        //设置当前画版的信息
        initPaintBoard();

        //设置服务信息
        initService();

        //莫名bug,要再次设置刷新一次
        this.setVisible(true);
    }

    /**
     * 设置当前窗体
     * */
    private void initFrame(){
        this.setTitle("画板");
        this.setDefaultCloseOperation(3);
        this.setLocation(550, 250);
        this.setSize(1000, 600);
        this.setLayout(new BorderLayout());
    }

    /**
     * 设置中央绘画区域以及绘画监听器
     * */
    private void initCenterPanelAndDrawListener(){
        //设置中央绘画区域
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        this.add(centerPanel, BorderLayout.CENTER);

        dl = new DrawListener();
        this.addMouseMotionListener(dl);
        this.addMouseListener(dl);

        Graphics2D g = (Graphics2D) this.getGraphics();
        dl.setGraphic(g);
        g.setColor(Color.BLACK);
        final Font font = new Font("BOLD",Font.BOLD,20);
        g.setFont(font);
        g.setStroke(new BasicStroke(2));
    }

    /**
     * 设置左侧绘画选择栏
     * */
    private void initLeftPanel(){
        final Font inputFont=new Font(Font.MONOSPACED,Font.BOLD,20);
        paintBtn = new JButton(curState.getText());
        paintBtn.setBackground(Color.white);
        paintBtn.setFont(inputFont);
        paintBtn.setSize(new Dimension(30, 20));
        paintBtn.addActionListener(new DrawButtonListener());

        leftPanel = new JPanel(new GridLayout(10, 0));
        for (int i=0; i<4; i++){leftPanel.add(new JLabel());}
        leftPanel.setBackground(Color.lightGray);
        this.add(leftPanel, BorderLayout.WEST);
        leftPanel.add(paintBtn);
    }

    /**
     * 设置文件选择器
     * */
    private void initFileChooser(){
        fileChooser = new JFileChooser();
        //限制ser文件类型
        fileChooser.setFileFilter(new FileNameExtensionFilter("ser","ser"));
    }

    /**
     * 设置文件menu
     * */
    private void initMenu(){
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        fileMenu.setSize(800, 20);

        JMenuItem newItem = new JMenuItem("新建");
        newItem.addActionListener(new NewItemActionListener());

        JMenuItem openItem = new JMenuItem("打开");
        openItem.addActionListener(new OpenItemActionListener());

        JMenuItem saveItem = new JMenuItem("保存");
        saveItem.addActionListener(new SaveItemActionListener());

        JMenuItem saveAsItem = new JMenuItem("另存为");
        saveAsItem.addActionListener(new SaveAsItemActionListener());

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);

        menuBar.add(fileMenu);
        menuBar.setBorderPainted(true);
        menuBar.setPreferredSize(new Dimension(10, 20));
        menuBar.setBackground(Color.getColor("silver"));
        this.setJMenuBar(menuBar);
    }

    /**
     * 初始化全局画板数据结构
     * */
    private void initPaintBoard(){
        curBoard = new PaintBoardEntity();
        curShape = new PaintShapeEntity();
        curStroke = new PaintStrokeEntity();
    }

    /**
     * 初始化相关服务
     * */
    private void initService(){
        classifyService = new StrokeNumShapeClassifyImpl();
        paintBoardDao = new PaintBoardDaoImpl();
    }

    /**
     * 绘画按钮的监听器
     * */
    class DrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (curState){
                case PAINTING:
                    //进入等待绘制状态
                    curState = PaintStateEnum.TO_PAINT;
                    paintBtn.setText("绘制");

                    //识别并绘制标记
                    ShapeLabelEnum labelType = classifyService.classifyShape(curShape);
                    curShape.setLabel(labelType);
                    dl.drawLabel(curShape.getLabel());

                    //结束此次图形绘制: 添加到画板并开始新的图形
                    curBoard.addShape(curShape);
                    curShape = new PaintShapeEntity();
                    break;
                case TO_PAINT:
                    //进入绘制状态
                    curState = PaintStateEnum.PAINTING;
                    paintBtn.setText(curState.getText());
                    break;
            }
        }
    }


    /**
     * 绘画的Listener
     * */
    class DrawListener extends MouseAdapter {
        private Graphics2D g;

        //临时存储绘画点
        int x1,x2,y1,y2;

        void setGraphic(Graphics2D g){
            this.g = g;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println(curState.name());
            //如果不是绘画状态就不能绘制
            if (curState == PaintStateEnum.TO_PAINT){return;}

            // 获取按下的坐标值
            x1 = getx(e);
            y1 = gety(e);

            //添加到当前笔画信息
            curStroke.addPoint(x1, y1);
        }

        @Override
        public void mouseDragged(MouseEvent e){
            //如果不是绘画状态就不能绘制
            if (curState == PaintStateEnum.TO_PAINT){return;}

            x2 = getx(e);
            y2 = gety(e);
            g.drawLine(x1, y1, x2, y2);

            // 绘制直线后交换坐标
            x1 = x2;
            y1 = y2;

            //添加到当前笔画信息
            curStroke.addPoint(x1, y1);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //如果不是绘画状态就不能绘制
            if (curState == PaintStateEnum.TO_PAINT){return;}

            x2 = getx(e);
            y2 = gety(e);

            curStroke.addPoint(x2, y2);

            //结束当前笔画.添加到图像里并开始新的笔画
            curShape.addStroke(curStroke);
            curStroke = new PaintStrokeEntity();

            //当前已经编辑过
            isEdited = true;
        }

        /**
         * 清空画板并重新绘制
         * @param board 需要重新绘制的board
         * */
        void redrawBoard(PaintBoardEntity board){
            clearBoard();
            drawBoard(board);
        }

        /**
         * 清空当前画板
         * */
        private void clearBoard(){
            update(g);
        }

        /**
         * 绘制画板
         * @param board 需要绘制的画板
         * */
        private void drawBoard(PaintBoardEntity board){
            List<PaintShapeEntity> shapes = board.getShapes();
            if (shapes==null){return;}
            for (PaintShapeEntity shape: shapes){
                drawShape(shape);
            }
        }

        void drawLabel(LabelEntity label){
            int x = label.getX();
            int y = label.getY();
            String text = label.getText();
            g.setColor(label.getColor());
            g.drawString(text, x, y);
            g.setColor(Color.BLACK);
        }

        private void drawShape(PaintShapeEntity shape){
            if (shape==null){return;}
            drawLabel(shape.getLabel());
            List<PaintStrokeEntity> strokes = shape.getStrokes();
            for (PaintStrokeEntity stroke: strokes){
                drawStroke(stroke);
            }
        }

        private void drawStroke(PaintStrokeEntity stroke){
            int [][] points = stroke.getPos();
            int index = stroke.getPointsNum();
            for (int i=0; i<index-1; i++){
                g.drawLine(points[i][0], points[i][1], points[i+1][0], points[i+1][1]);
            }
        }

        /**
         *得到经过坐标变换的x坐标
         * */
        private int getx(MouseEvent e){
            int x = e.getX();
            x = Math.max(x, leftPanel.getWidth());
            return x;
        }

        /**
         *得到经过坐标变换的y坐标
         * */
        private int gety(MouseEvent e){
            int y = e.getY();
            y = Math.max(y, menuBar.getHeight());
            return y;
        }
    }

    class NewItemActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.NO_OPTION;
            //没有edit时直接新建
            if (isEdited){
                choice = JOptionPane.showConfirmDialog(null,
                        "是否要保存当前画板",
                        "选择",
                        JOptionPane.YES_NO_CANCEL_OPTION);
            }
            if (choice==JOptionPane.CANCEL_OPTION){return;}
            if (choice==JOptionPane.YES_OPTION){
                boolean exec = saveCurFileByFileChooser();
                if (!exec){return;}
            }
            //新建
            openNew();
        }
    }

    class OpenItemActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.NO_OPTION;

            //若edit过需要询问用户是否保存
            if (isEdited){
                choice = JOptionPane.showConfirmDialog(null,
                        "是否要保存当前画板",
                        "选择",
                        JOptionPane.YES_NO_CANCEL_OPTION);
            }
            //取消选择
            if (choice==JOptionPane.CANCEL_OPTION){return;}
            //保存当前文件再open
            if (choice==JOptionPane.YES_OPTION) {
                boolean isSaveSucceed = saveCurFileByFileChooser();
                if (!isSaveSucceed){return;}
            }

            //得到需要打开的文件路径
            curFilePath = getOpenPathByFileChooser();
            if (curFilePath == null){return;}
            curBoard = paintBoardDao.openPaintBoardByPath(curFilePath);

            //打开失败
            if (curBoard==null){
                JOptionPane.showMessageDialog(null, "打开错误");
                return;
            }

            //重画画板
            dl.redrawBoard(curBoard);

            //状态转换
            curShape = new PaintShapeEntity();
            curStroke = new PaintStrokeEntity();

            curState = PaintStateEnum.TO_PAINT;
            paintBtn.setText(curState.getText());

            isEdited = false;
        }
    }

    /**
     * 另存为按钮响应
     * */
    class SaveAsItemActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            curFilePath = getSavePathByFileChooser();
            if (curFilePath==null){return;}
            paintBoardDao.savePaintBoardByPath(curBoard, curFilePath);

            isEdited = false;
        }
    }

    /**
     * 保存按钮响应
     * */
    class SaveItemActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //如果之前没有保存过
            if (curFilePath == null){
                curFilePath = getSavePathByFileChooser();
                if (curFilePath==null){return;}
            }
            paintBoardDao.savePaintBoardByPath(curBoard, curFilePath);

            isEdited = false;
        }
    }

    /**
     * 通过FileChooser储存当前的文件
     * @return 保存是否成功
     * */
    private boolean saveCurFileByFileChooser(){
        if (curFilePath == null) {
            curFilePath = getSavePathByFileChooser();
            if (curFilePath==null){return false;}
        }
        paintBoardDao.savePaintBoardByPath(curBoard, curFilePath);
        return true;
    }

    /**
     * 通过FileChooser得到将要打开的文件路径
     * */
    private String getOpenPathByFileChooser(){
        fileChooser.showOpenDialog(null);
        File f = fileChooser.getSelectedFile();
        if (f == null) {
            return null;
        }
        return f.getPath();
    }

    /**
     * 通过FileChooser得到将要保存的文件路径
     * */
    private String getSavePathByFileChooser(){
        fileChooser.showSaveDialog(null);
        File f = fileChooser.getSelectedFile();
        if (f == null) {
            return null;
        }
        return f.getPath();
    }

    /**
     * 打开新的文件时状态转换
     * */
    private void openNew(){
        curBoard = new PaintBoardEntity();
        curShape = new PaintShapeEntity();
        curStroke = new PaintStrokeEntity();
        curFilePath = null;

        curState = PaintStateEnum.TO_PAINT;
        paintBtn.setText(curState.getText());

        isEdited = false;

        dl.clearBoard();
    }
}
