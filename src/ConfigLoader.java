import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    private String configPath;
    private Map<String, String[][]> data;

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
        for(File f:files){
            if(!f.isFile())continue;
            String fname = f.getName().replaceFirst("[.][^.]+$", "");
            
        }
    }
}
