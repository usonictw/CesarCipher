package menu;

import Controller.Controller;

import java.io.*;
import java.util.ArrayList;

public class MenuEncryption {

    private ArrayList<MenuEntry> entries = new ArrayList<>();
    boolean isExit = false;
    Controller controller = new Controller();

    public MenuEncryption() {

        entries.add(new MenuEntry("Cesar Encryption") {
            @Override
            public void run() {
                controller.chooseFileForEncrypt();
            }
        });

        entries.add(new MenuEntry("Cesar Decryption") {
            @Override
            public void run() {
                controller.ChooseFileForDecrypt();
            }
        });

        entries.add(new MenuEntry("Brute Force algorithm") {
            @Override
            public void run() {
                controller.bruteForce();
            }
        });

        entries.add(new MenuEntry("Exit") {
            @Override
            public void run() {
                isExit = true;
            }
        });
    }

    public void run() {
        // Бесконечный цикл, пока не выбрали exit
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!isExit) {
            printMenu();
            try {
                String line = reader.readLine();
                int choice = Integer.parseInt(line);
                // Выбираем пункт меню где будет выполняться код
                MenuEntry entry = entries.get(choice - 1);
                entry.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        int itemNumber = 1;
        for (int i = 0; i < entries.size(); i++) {
            System.out.println(itemNumber + ". " + entries.get(i).getTitle());
            itemNumber++;
        }
    }
}
