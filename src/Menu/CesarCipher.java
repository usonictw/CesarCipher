import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CesarCipher {

    public static void encryption(String path, int shift) throws IOException {

        if (Files.exists(Paths.get(path))) {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            FileChannel fileChannel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            StringBuilder encryptionString = new StringBuilder();
            while (buffer.hasRemaining()) {
                char ch = (char) buffer.get();
                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                    encryptionString.append((char) (ch + shift % 26));
                }
            }
            Files.createFile(Paths.get("D:\\file1encrypt.txt"));
            RandomAccessFile fileForWrite = new RandomAccessFile("D:\\file1encrypt.txt", "rw");
            FileChannel forWrite = fileForWrite.getChannel();

            ByteBuffer buffer1 = ByteBuffer.allocate(String.valueOf(encryptionString).getBytes().length);
            buffer1.clear();
            buffer1.put(String.valueOf(encryptionString).getBytes());
            buffer1.flip();
            forWrite.write(buffer1);
            forWrite.close();

        }
    }

    public static void decryption(String path, int key) throws IOException {
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
                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                    encryptionString.append((char) (ch + k % 26));
                }
            }
            Files.createFile(Paths.get("D:\\file1Decrypt.txt"));
            RandomAccessFile fileForWrite = new RandomAccessFile("D:\\fileDecrypt.txt", "rw");
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
