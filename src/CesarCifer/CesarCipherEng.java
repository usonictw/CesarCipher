package CesarCifer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CesarCipherEng {

    private static final List<Character> separatingMarks = Arrays.asList(
            '.', ',', '"', ':', ';', '!', '?', ' ', '-', ')', '(', '%', '#', '~', '<', '>', '^', '@');

    public static void encryptionEng(String path, int shift) throws IOException {

        StringBuilder encryptionString = new StringBuilder();
        String[] str = path.split("\\.");

        System.out.println(path);
        String pathAfterEncrypt = str[0] + "Encrypt" + "." + str[1];
        System.out.println(pathAfterEncrypt);

        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
        fileChannel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            char ch = (char) buffer.get();
            if ((ch >= 'A' && ch <= 'Z')) {
                if ((ch + (shift % 26)) > 90) {
                    encryptionString.append((char) ((ch + shift % 26) - 26));
                } else {
                    encryptionString.append((char) (ch + shift % 26));
                }
            } else if ((ch >= 'a' && ch <= 'z')) {
                if ((ch + (shift % 26) > 122)) {
                    encryptionString.append((char) ((ch + shift % 26) - 26));
                } else {
                    encryptionString.append((char) (ch + shift % 26));
                }
            } else {
                boolean found = false;
                int index = 0;
                for (Character c : separatingMarks
                ) {
                    if (ch == c) {
                        found = true;
                        break;
                    }
                    index++;
                }
                if (found) {
                    encryptionString.append((char) (separatingMarks.get(((shift + index) %
                            separatingMarks.size()))));
                } else {
                    encryptionString.append(ch);
                }
            }
        }


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

    public static void decryptionEng(String path, int key) throws IOException {

        String[] str = path.split("\\.");

        System.out.println(path);
        String fileNameAfterDecrypt = str[0] + "Decrypt" + "." + str[1];
        System.out.println(fileNameAfterDecrypt);
        int k = - key;
        if (Files.exists(Paths.get(path))) {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            StringBuilder encryptionString = new StringBuilder();
            while (buffer.hasRemaining()) {
                char ch = (char) buffer.get();
                if ((ch >= 'A' && ch <= 'Z')) {
                    if ((ch + (k % 26)) < 65) {
                        encryptionString.append((char) ((ch + k % 26) + 26));
                    } else {
                        encryptionString.append((char) (ch + k % 26));
                    }
                } else if ((ch >= 'a' && ch <= 'z')) {
                    if ((ch + (k % 26)) < 97) {
                        encryptionString.append((char) ((ch + k % 26) + 26));
                    } else {
                        encryptionString.append((char) (ch + k % 26));
                    }
                } else {
                    int index = 0;
                    boolean found = false;
                    for (Character c : separatingMarks) {
                        if (ch == c) {
                            found = true;
                            break;
                        }
                        index++;
                    }
                    if (found) {
                        if ((index + (k % separatingMarks.size())) >= 0) {
                            encryptionString.append(separatingMarks.get((index + (k % separatingMarks.size())) % separatingMarks.size()));
                        } else {
                            encryptionString.append(separatingMarks.get((separatingMarks.size() + (index + (k % separatingMarks.size())))));
                        }
                    } else {
                        encryptionString.append(ch);
                    }
                }
            }
            Files.createFile(Paths.get(fileNameAfterDecrypt));
            RandomAccessFile fileForWrite = new RandomAccessFile(fileNameAfterDecrypt, "rw");
            FileChannel forWrite = fileForWrite.getChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(String.valueOf(encryptionString).getBytes().length);
            buffer1.clear();
            buffer1.put(String.valueOf(encryptionString).getBytes());
            buffer1.flip();
            forWrite.write(buffer1);
            forWrite.close();
        }
    }

    public static void writeToFile(String pathAfterEncrypt, StringBuilder encryptionString) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathAfterEncrypt, false), 524)) {
            bw.write(String.valueOf(encryptionString));
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public static void bruteForce(String path) throws IOException {


        String[] str = path.split("\\.");
        System.out.println(path);
        String fileNameBF = str[0] + "BruteForce" + "." + str[1];
        System.out.println(fileNameBF);

        for (int i = 1; i < 26; i++) {
            StringBuilder encryptionString = new StringBuilder();
            FileReader fr = new FileReader(path);
            BufferedReader buffer = new BufferedReader(fr, 524);
            while (buffer.ready()) {
                char ch = (char) buffer.read();
                if ((ch >= 'A' && ch <= 'Z')) {
                    if ((ch - i % 26) < 65) {
                        encryptionString.append((char) ((ch - i % 26) + 26));
                    } else {
                        encryptionString.append((char) (ch - i % 26));
                    }
                } else if ((ch >= 'a' && ch <= 'z')) {
                    if ((ch - i % 26) < 97) {
                        encryptionString.append((char) ((ch - i % 26) + 26));
                    } else {
                        encryptionString.append((char) (ch - i % 26));
                    }
                } else {
                    int index = 0;
                    boolean found = false;
                    for (Character c : separatingMarks) {
                        if (ch == c) {
                            found = true;
                            break;
                        }
                        index++;
                    }
                    if (found) {
                        if ((index - i) >= 0) {
                            encryptionString.append(separatingMarks.get((index - i) % separatingMarks.size()));
                        } else {
                            encryptionString.append(separatingMarks.get(separatingMarks.size() + (index - i)));
                        }
                    } else {
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
                if (last.contains("}") || last.contains("{") || last.contains("/") ||
                        last.contains("'") || last.contains("`")) {
                    continue;
                } else if ((last.contains(" ") && (last.contains(", ") && last.contains(". "))) || (last.contains(" ") &&
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

