package util;

import java.io.*;

/**
 * @author waxnkw
 * @version 2018.9.24
 * 单件模式
 * ser文件的处理工具类,包括读写
 * */
public class SerFileUtility {
    private static SerFileUtility instance;

    private SerFileUtility(){}

    public static SerFileUtility getInstance(){
        if (instance==null){instance = new SerFileUtility();}
        return instance;
    }

    /**
     * 根据路径写ser对象
     * @param content 内容
     * @param path 路径
     * */
    public void write(Object content, String path){
        ObjectOutputStream oStream ;
        try {
            oStream = new ObjectOutputStream(new FileOutputStream(path));
            oStream.writeObject(content);
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据路径读ser对象
     * @param path 路径
     * @return 读出的对象
     * */
    public Object read(String path){
        Object obj= null;
        ObjectInputStream oInputStream;
        try {
            oInputStream = new ObjectInputStream(new FileInputStream(path));
            obj = oInputStream.readObject();
            oInputStream.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
}
