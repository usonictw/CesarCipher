package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MenuChooseLngs {

    public static String titleLng;
    private ArrayList<MenuEntry> menuList = new ArrayList<>();
    private boolean isExit = false;


    public MenuChooseLngs() {

        menuList.add(new MenuEntry("English") {
            @Override
            public void run() {
                titleLng = "English";
                MenuEncryption menuEncryption = new MenuEncryption();
                menuEncryption.run();
            }
        });

        menuList.add(new MenuEntry("Cyrillic") {
            @Override
            public void run() {
                titleLng = "Cyrillic";
                MenuEncryption menuEncryption = new MenuEncryption();
                menuEncryption.run();
            }
        });

        menuList.add(new MenuEntry("Exit") {
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
                MenuEntry entry = menuList.get(choice - 1);
                entry.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        int itemNumber = 1;
        for (int i = 0; i < menuList.size(); i++) {
            System.out.println(itemNumber + ". " + menuList.get(i).getTitle());
            itemNumber++;
        }
    }
}

