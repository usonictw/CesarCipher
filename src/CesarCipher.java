import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CesarCipher {


    public static void encryption(String path, int shift) throws IOException {

        StringBuilder encryptionString = new StringBuilder();
        String[] str = path.split("\\.");

        System.out.println(path);
        String pathAfterEncrypt = str[0] + "Encrypt" + "." + str[1];
        System.out.println(pathAfterEncrypt);

        if (Files.exists(Paths.get(path))) {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                char ch = (char) buffer.get();
                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                    encryptionString.append((char)(ch + shift % 26));
                } else if(ch == ' ' || ch == '.' || ch == '!' || ch == '?' || ch == ',' || ch == '\n' || ch == '\r'){
                    encryptionString.append(ch);
                }
            }
        }
        if (!Files.isRegularFile(Paths.get(pathAfterEncrypt))) {
            Files.createFile(Paths.get(pathAfterEncrypt));
            RandomAccessFile fileForWrite = new RandomAccessFile(pathAfterEncrypt, "rw");
            FileChannel forWrite = fileForWrite.getChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(String.valueOf(encryptionString).getBytes().length);
            buffer1.clear();
            buffer1.put(String.valueOf(encryptionString).getBytes());
            buffer1.flip();
            forWrite.write(buffer1);
            forWrite.close();
        } else {
            System.out.println("File already exist. Enter add to file name");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            pathAfterEncrypt = str[0] + "Encrypt" + s + "." + str[1];

            encryption(pathAfterEncrypt, shift);
        }

    }

    public static void decryption(String path, int key) throws IOException {

        String[] str = path.split("\\.");

        System.out.println(path);
        String fileNameAfterDecrypt = str[0] + "Decrypt" + "." + str[1];
        System.out.println(fileNameAfterDecrypt);
        int k = -key;
        if (Files.exists(Paths.get(path))) {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            StringBuilder encryptionString = new StringBuilder();
            while (buffer.hasRemaining()) {
                char ch = (char) buffer.get();
                if(ch == ' ' || ch == '.' || ch == '!' || ch == '?' || ch == ',' || ch == '\n' || ch == '\r'){
                    encryptionString.append(ch);
                }else {
                    encryptionString.append((char) (ch + k % 26));
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
}
