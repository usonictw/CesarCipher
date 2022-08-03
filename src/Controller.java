import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {

        MenuGeneral menuGeneral = new MenuGeneral();
        menuGeneral.run();

    }

    public void CesarEncryption(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose file for encryption");
        File[] logicalDrivers = File.listRoots();
        while (true) {
            for (int i = 1; i <= logicalDrivers.length; i++) {
                System.out.println(i + " " + logicalDrivers[i - 1]);
            }
            break;
        }
        System.out.println("Choose item (logical drive).");
        int index = scanner.nextInt();
        openDrive(logicalDrivers[index - 1]);
        System.out.println("Choose file \".txt\" or directory");
        int index2 = scanner.nextInt();
        File[] files = logicalDrivers[index - 1].listFiles();
        for (int i = 0; i < files.length; i++) {
            if ((index2 - 1) == i) {
                String s = files[i].toString();
                if (files[i].isFile()) {
                    System.out.println("Enter shift for performing encryption. Note: Number 13 is symmetric Cesar Encryption  ");
                    try {
                        CesarCipher.encryption(s, scanner.nextInt());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    openDrive(files[i]);
                }
            }
        }
    }

    public void CesarDecryption(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose file for encryption");
        File[] logicalDrivers = File.listRoots();
        while (true) {
            for (int i = 1; i <= logicalDrivers.length; i++) {
                System.out.println(i + " " + logicalDrivers[i - 1]);
            }
            break;
        }
        System.out.println("Choose item (logical drive).");
        int index = scanner.nextInt();
        ArrayList<String> files = searchEncryptFile(logicalDrivers[index - 1]);
        int i = 1;
        for (String s : files
        ) {
            System.out.println(i + ". " + s);
            i++;
        }

        System.out.println("Choose file for decryption");
        int index2 = scanner.nextInt();
        System.out.println("Enter key (shift) for decryption");
        int shift = scanner.nextInt();
        try {
            CesarCipher.decryption(files.get(index2 - 1), shift);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private ArrayList<String> searchEncryptFile(File file) {
        ArrayList<String> encryptFiles = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    String s1 = files[i].getName();
                    if (s1.contains("Encrypt")) {
                        encryptFiles.add(files[i].getPath());
                    }
                }
            }

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    searchEncryptFile(files[i]);
                }
            }
        }
        return encryptFiles;
    }

    private void openDrive(File directory) {
        if (directory.isDirectory()) {
            int index = 1;
            for (File file :
                    directory.listFiles()) {
                System.out.println(index + ". " + file);
                index++;
            }
            System.out.println();
        }
    }

}
