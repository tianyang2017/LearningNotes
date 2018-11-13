import java.io.*;
import java.util.ArrayList;

/**
 * @author : heibai
 * @description : 转换图片地址为github地址工具类
 * @date :create in 2018/11/8
 */
public class ChangeImageUrl {

    private static ArrayList<String> filesList=new ArrayList<>();

    public static void main(String[] args) throws Exception {
        if (args.length<1){
            System.out.println("请传递路径");
            return;
        }
        String dir=args[0];
        String preUrl="https://github.com/heibaiying/LearningNotes/blob/master/picture/";
        String regex="(!\\[(\\S*)]\\(D:\\\\学习笔记\\\\picture\\\\(\\S*)\\)[^(</br>)]*?)";
        changeImageUrl(dir,preUrl,regex);
    }


    private static void changeImageUrl(String dir,String preUrl,String oldImageUrlRegex) throws IOException {
        getAllFile(dir);
        for (String filePath:filesList){
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            StringBuilder stringBuilder=new StringBuilder();
            byte[] bytes=new byte[10240];
            int read=0;
            while ((read=inputStream.read(bytes))!=-1){
                stringBuilder.append(new String(bytes,0,read));
            }
            inputStream.close();
            String content =stringBuilder.toString();
            String newContent = content.replaceAll(oldImageUrlRegex, String.format("$1</br>![$2](%s$3)</br>",preUrl));
            System.out.println(newContent);
            String newFilePath = filePath.replaceAll("(\\S*)\\.(\\S*)","$1-(changed).$2");
            FileWriter fileWriter=new FileWriter(new File(filePath));
            fileWriter.write(newContent);
            fileWriter.flush();
        }
    }

    private static void getAllFile(String dir){
        File file = new File(dir);
        //如果是文件 则不遍历
        if (file.isFile()&&file.getName().endsWith(".md")){
            filesList.add(file.getAbsolutePath());
            return;
        }
        //如果是文件夹 则遍历下面的所有文件
        File[] files = file.listFiles();
        if (files != null) {
            for (File f:files){
                if (f.isDirectory()&&!f.getName().startsWith(".")){
                    getAllFile(f.getAbsolutePath());
                }else if (f.getName().endsWith(".md")){
                    filesList.add(f.getAbsolutePath());
                }
            }
        }
    }

}
