import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String dir1 = "./Casos/casoTeste.txt";
        File file1 = new File(dir1);
        Map mapa1 = new Map(file1);

        String dir2 = "./Casos/caso05.txt";
        File file2 = new File(dir2);
        Map mapa2 = new Map(file2);

        String dir3 = "./Casos/caso06.txt";
        File file3 = new File(dir3);
        Map mapa3 = new Map(file3);

        String dir4 = "./Casos/caso07.txt";
        File file4 = new File(dir4);
        Map mapa4 = new Map(file4);

        String dir5 = "./Casos/caso08.txt";
        File file5 = new File(dir5);
        Map mapa5 = new Map(file5);

        String dir6 = "./Casos/caso09.txt";
        File file6 = new File(dir6);
        Map mapa6 = new Map(file6);

        String dir7 = "./Casos/caso10.txt";
        File file7 = new File(dir7);
        Map mapa7 = new Map(file7);

        // for (Map map : makeMaps()) {
        // System.out.println(map.results());
        // }

        System.out.println(mapa1.results());
    }

    private static ArrayList<File> findFiles() {
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("./Casos/casoTeste.txt"));
        files.add(new File("./Casos/caso05.txt"));
        files.add(new File("./Casos/caso06.txt"));
        files.add(new File("./Casos/caso07.txt"));
        files.add(new File("./Casos/caso08.txt"));
        files.add(new File("./Casos/caso09.txt"));
        files.add(new File("./Casos/caso10.txt"));
        return files;
    }

    private static ArrayList<Map> makeMaps() {
        ArrayList<Map> maps = new ArrayList<>();
        for (File file : findFiles()) {
            maps.add(new Map(file));
        }
        return maps;
    }
}