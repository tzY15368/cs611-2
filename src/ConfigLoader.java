import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;

public class ConfigLoader {
    private String configPath;
    private Map<String, List<List<String>>> data;

    public ConfigLoader(String configPath){

        this.configPath = configPath;
        this.data = new HashMap<>();
        File file = new File(configPath);
        File[] files = file.listFiles();
        boolean validDir = false;
        for(int i=0;i<files.length;i++){
            if(files[i].getName().equals("Armory.txt")){
                validDir = true;
            }
        }
        System.out.println(this.data);
        if(!validDir){
            System.out.println("ConfigLoader: invalid config path");
            System.exit(-1);
        }
//        try {
//            for(File f:files){
//                if(!f.isFile())continue;
//                String fname = f.getName().replaceFirst("[.][^.]+$", "");
//                String allData = Files.readString(f.toPath());
//                String[] lines = allData.split("\n");
//                String[] headers = lines[0].split("/");
//                List<String> hdrs = new ArrayList<>(List.of(headers));
//                System.out.println("------------"+lines[0]);
//                System.out.println("hdrs: "+hdrs);
//                List<List<String>> values = new ArrayList<>();
//                values.add(hdrs);
//                for(int i=1;i<lines.length;i++){
//                    String[] fields = lines[i].split("\\s+");
//                    ArrayList<String> fi = new ArrayList<>(List.of(fields));
//                    fi.remove("All");
//                    if(fi.size()!=headers.length){
//                        System.out.println(headers.length);
//                        System.out.println(fi.size());
//                        System.out.println(Arrays.toString(headers));
//                        System.out.println(fi);
//                        System.out.println(String.format("bad format in file %s", fname));
//                        break;
//                    }
//                    values.add(fi);
//                }
//                System.out.println(fname + values);
//                this.data.put(fname, values);
//            }
//        } catch (IOException ioe){
//            System.out.println("IOEXception:"+ioe);
//            System.exit(-1);
//        }
//        System.out.println("~~~~~~~~~~~~~~~");
//        System.out.println(this.data);
    }
}
