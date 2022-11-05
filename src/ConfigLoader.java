import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class ConfigLoader {
    private String configPath;
    private Map<String, List<List<String>>> data;

    public Map<String, List<List<String>>> getData() {
        return data;
    }

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
        if(!validDir){
            System.out.println("ConfigLoader: invalid config path");
            System.exit(-1);
        }
        try {
            for(File f:files){
                if(!f.isFile())continue;
                String fname = f.getName().replaceFirst("[.][^.]+$", "");
                ArrayList<List<String>> allData = new ArrayList<>();
                Stream<String> fStream = Files.lines(Path.of(f.getPath()));
                fStream.forEach((String line)->{
                    line = line.replace("All","");
                    String delimiter = allData.size()==0?"/":"\\s+";
                    List<String> fields = new ArrayList<>(List.of(line.split(delimiter)));
                    if(allData.size()!=0 && allData.get(0).size()!= fields.size()){
                        System.out.println("Warning: headers mismatch:\n"+"headers:"+allData.get(0) + "\ngot:"+fields);
                        System.out.println(fname + "--------actual line:"+line);
                        return;
                    }
                    allData.add(fields);
                });
                this.data.put(fname,allData);
            }
        } catch (IOException ioe){
            System.out.println("IOEXception:"+ioe);
            System.exit(-1);
        }
        System.out.println("ConfigLoader: got "+this.data.size()+" types");
        ItemManager.registerItems(this);
    }
}
