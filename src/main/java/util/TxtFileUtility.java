package util;

import java.io.*;

public class TxtFileUtility {
    private static TxtFileUtility instance;

    private TxtFileUtility(){}

    public TxtFileUtility getInstance(){
        if (instance==null){instance = new TxtFileUtility();}
        return instance;
    }

    public void write(String content, String path){
        File file;
        file = new File(path);
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(String path){
        File file = new File(path);
        String content = "";
        try{
            InputStreamReader read=new InputStreamReader(new FileInputStream(file));
            BufferedReader bf=new BufferedReader(read);
            String line=null;
            while((line=bf.readLine())!=null){
                content+=line+System.lineSeparator();
            }
            bf.close();
            return content;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
