import java.io.File;

public class Main {

    public static void main(String[] args) {
        String dirCasos = "casos-cohen";
        // String dirCasos = "casos";

        String dir1 = "./casoTeste.txt";
        File file1 = new File(dir1);
        Map mapaTeste = new Map(file1);

        String dir2 = dirCasos + "/caso05.txt";
        File file2 = new File(dir2);
        Map mapa5 = new Map(file2);

        String dir3 = dirCasos + "/caso06.txt";
        File file3 = new File(dir3);
        Map mapa6 = new Map(file3);

        String dir4 = dirCasos + "/caso07.txt";
        File file4 = new File(dir4);
        Map mapa7 = new Map(file4);

        String dir5 = dirCasos + "/caso08.txt";
        File file5 = new File(dir5);
        Map mapa8 = new Map(file5);

        String dir6 = dirCasos + "/caso09.txt";
        File file6 = new File(dir6);
        Map mapa9 = new Map(file6);

        String dir7 = dirCasos + "/caso10.txt";
        File file7 = new File(dir7);
        Map mapa10 = new Map(file7);

        for (Map map : new Map[]{mapaTeste, mapa5, mapa6, mapa7, mapa8, mapa9, mapa10}) {
            System.out.println(map.results());
        }

        // System.out.println(mapa1.results());
    }
}