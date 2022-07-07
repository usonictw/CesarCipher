import java.util.ArrayList;
import java.util.List;

public class Menu {

    private ArrayList<MenuEntry> entries = new ArrayList<>();

 entries.add(new MenuEntry("BankSystem") {
        @Override
        public void run() {
            menuBank.run();
        }
    });

}
