package CesarCifer;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CesarCipherCyr {

    private static final List<Character> cyrillicUpperCase = Arrays.asList('А', 'Б', 'В', 'Г', 'Д', 'Е',
            'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц',
            'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', '.', ',', '"', ':', ';', '!', '?', ' ', '-', ')', '(');

    private static final List<Character> cyrillicLowerCase = Arrays.asList('а', 'б', 'в', 'г', 'д', 'е',
            'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'c', 'т', 'у', 'ф', 'х', 'ц',
            'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я');

    public static void encryptionCyrillic(String path, int shift) throws IOException {

        StringBuilder encryptionString = new StringBuilder();
        String[] str = path.split("\\.");
        System.out.println(path);
        String pathAfterEncrypt = str[0] + "EncryptCyr" + "." + str[1];
        System.out.println(pathAfterEncrypt);


        FileReader fr = new FileReader(path);
        BufferedReader buffer = new BufferedReader(fr, 524);
        int i;
        while ((i = buffer.read()) != -1) {
            char ch = (char) i;
            boolean found = false;
            int index = 0;
            for (Character c : cyrillicUpperCase
            ) {
                if (ch == c) {
                    found = true;
                    break;
                }
                index++;
            }
            if (found) {
                encryptionString.append((char) (cyrillicUpperCase.get((shift + index) %
                        cyrillicUpperCase.size())));
            }else {
                index = 0;
                for (Character c : cyrillicLowerCase
                ) {
                    if (ch == c) {
                        found = true;
                        break;
                    }
                    index++;
                }
                if (found) {
                    encryptionString.append((char) (cyrillicLowerCase.get((shift + index) %
                            cyrillicLowerCase.size())));
                }else {
                    encryptionString.append(ch);
                }
            }
        }
        fr.close();

        if (!Files.exists(Paths.get(pathAfterEncrypt))) {
            writeToFile(pathAfterEncrypt, encryptionString);
        } else {
            System.out.println("File already exist. Enter add to file name");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            pathAfterEncrypt = str[0] + "Encrypt" + s + "." + str[1];
            writeToFile(pathAfterEncrypt, encryptionString);
        }
    }

    public static void decryptionCyrillic(String path, int key) throws IOException {

        StringBuilder encryptionString = new StringBuilder();
        String[] str = path.split("\\.");
        System.out.println(path);
        String fileNameAfterDecrypt = str[0] + "DecryptCyr" + "." + str[1];
        System.out.println(fileNameAfterDecrypt);

        int keyUpperCase = - key % cyrillicUpperCase.size();
        int keyLowerCase = - key % cyrillicLowerCase.size();
        FileReader fr = new FileReader(path);
        BufferedReader buffer = new BufferedReader(fr, 524);
        int i;
        while ((i = buffer.read()) != -1) {
            char ch = (char) i;
            boolean found = false;
            int index = 0;
            for (Character c : cyrillicUpperCase
            ) {
                if (ch == c) {
                    found = true;
                    break;
                }
                index++;
            }
            if (found) {
                if ((index + keyUpperCase) >= 0) {
                    encryptionString.append(cyrillicUpperCase.get((index + keyUpperCase) % cyrillicUpperCase.size()));
                } else {
                    encryptionString.append(cyrillicUpperCase.get(cyrillicUpperCase.size() + (index + keyUpperCase)));
                }
            } else {
                index = 0;
                for (Character c : cyrillicLowerCase
                ) {
                    if (ch == c) {
                        found = true;
                        break;
                    }
                    index++;
                }
                if (found) {
                    if ((index + keyLowerCase) >= 0) {
                        encryptionString.append(cyrillicLowerCase.get((index + keyLowerCase) % cyrillicLowerCase.size()));
                    } else {
                        encryptionString.append(cyrillicLowerCase.get(cyrillicLowerCase.size() + (index + keyLowerCase)));
                    }
                }else {
                    encryptionString.append(ch);
                }
            }
            writeToFile(fileNameAfterDecrypt, encryptionString);
        }
    }

    public static void writeToFile(String pathAfterEncrypt, StringBuilder encryptionString) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathAfterEncrypt, false), 524)) {
            bw.write(String.valueOf(encryptionString));
            bw.flush();
        }catch (IOException e){
            throw new RuntimeException();
        }

    }

    public static void bruteForce(String path) throws IOException {

        String[] str = path.split("\\.");
        System.out.println(path);
        String fileNameBF = str[0] + "BruteForce" + "." + str[1];
        System.out.println(fileNameBF);

        for (int i = 1; i < cyrillicUpperCase.size(); i++) {
            StringBuilder encryptionString = new StringBuilder();
            FileReader fr = new FileReader(path);
            BufferedReader buffer = new BufferedReader(fr, 524);
            int j;
            while ((j = buffer.read()) != -1) {
                char ch = (char) j;
                boolean found = false;
                int index = 0;
                for (Character c : cyrillicUpperCase
                ) {
                    if (ch == c) {
                        found = true;
                        break;
                    }
                    index++;
                }
                if (found) {
                    if ((index - i) >= 0) {
                        encryptionString.append(cyrillicUpperCase.get((index - i) % cyrillicUpperCase.size()));
                    } else {
                        encryptionString.append(cyrillicUpperCase.get(cyrillicUpperCase.size() + (index - i)));
                    }
                } else {
                    for (Character c : cyrillicLowerCase
                    ) {
                        if (ch == c) {
                            found = true;
                            break;
                        }
                        index++;
                    }
                    if (found) {
                        if ((index - i) >= 0) {
                            encryptionString.append(cyrillicLowerCase.get((index - i) % cyrillicLowerCase.size()));
                        } else {
                            encryptionString.append(cyrillicLowerCase.get(cyrillicLowerCase.size() + (index - i)));
                        }
                    }else {
                        encryptionString.append(ch);
                    }
                }
            }
            writeToFile(fileNameBF, encryptionString);
            String line, last = null;
            FileReader fr1 = new FileReader(fileNameBF);
            BufferedReader buffer1 = new BufferedReader(fr1, 524);
            while ((line = buffer1.readLine()) != null) {
                last = line;
            }
            if (last != null) {
                if ((last.contains(" ") && (last.contains(", ") || last.contains(". "))) || (last.contains(" ") &&
                        (last.contains("!") || last.contains("?") || last.contains(".")))) {
                    System.out.printf("The key %d is correct \n", i);
                    return;
                }
            } else {
                throw new NullPointerException();
            }
            fr1.close();
            fr.close();
            File file = new File(fileNameBF);
            file.delete();
        }
    }
}
