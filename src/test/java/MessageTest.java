import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

public class MessageTest {
    @Test
    public void allTablesAreEmptyTest() {
        Message message = new Message();
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        String receiver = "Receiver";
        String sender = "Sender";
        String expected = String.format("""
                Здравствуйте, %s!

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы: %s.
                Появились следующие новые страницы: %s.
                Изменились следующие страницы: %s.

                С уважением,
                %s.""", receiver, "[]", "[]", "[]", sender);

        Assertions.assertEquals(expected, message.generateMessage(oldTable,
                newTable, receiver, sender));
    }

    @Test
    public void oldTableEmptyTest() {
        Message message = new Message();
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        String receiver = "Receiver";
        String sender = "Sender";

        for (int i = 0; i < 3; i++) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        String expected = String.format("""
                Здравствуйте, %s!

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы: %s.
                Появились следующие новые страницы: %s.
                Изменились следующие страницы: %s.

                С уважением,
                %s.""", receiver, "[]", newTable.keySet(), "[]", sender);
        Assertions.assertEquals(expected, message.generateMessage(oldTable,
                newTable, receiver, sender));
    }

    @Test
    public void newTableEmptyTest() {
        Message message = new Message();
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        String receiver = "Receiver";
        String sender = "Sender";

        for (int i = 0; i < 3; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        String expected = String.format("""
                Здравствуйте, %s!

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы: %s.
                Появились следующие новые страницы: %s.
                Изменились следующие страницы: %s.

                С уважением,
                %s.""", receiver, oldTable.keySet(), "[]", "[]", sender);
        Assertions.assertEquals(expected, message.generateMessage(oldTable,
                newTable, receiver, sender));
    }

    @Test
    public void changedTest() {
        Message message = new Message();
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> changedTable = new Hashtable<>();
        String receiver = "Receiver";
        String sender = "Sender";

        for (int i = 0; i < 20; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 0; i < 20; i += 2) {
            newTable.put("www." + i + ".com", "New important code " + i + ".html");
            changedTable.put("www." + i + ".com", "New important code " + i + ".html");
        }

        String resultString = message.generateMessage(oldTable,
                newTable, receiver, sender).split("\n")[6];
        for (String v : changedTable.keySet()) {
            Assertions.assertTrue(resultString.contains(v));
        }
        Assertions.assertEquals(changedTable.size(), resultString.split(", ").length);
    }

    @Test
    public void removedTest() {
        Message message = new Message();
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> removedTable = new Hashtable<>();
        String receiver = "Receiver";
        String sender = "Sender";

        for (int i = 0; i < 20; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 0; i < 20; i += 2) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 1; i < 20; i += 2) {
            removedTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        String resultString = message.generateMessage(oldTable,
                newTable, receiver, sender).split("\n")[4];
        for (String v : removedTable.keySet()) {
            Assertions.assertTrue(resultString.contains(v));
        }
        Assertions.assertEquals(removedTable.size(), resultString.split(", ").length);
    }

    @Test
    public void addedTest() {
        Message message = new Message();
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> addedTable = new Hashtable<>();
        String receiver = "Receiver";
        String sender = "Sender";

        for (int i = 0; i < 10; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 15; i < 29; i += 2) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
            addedTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        String resultString = message.generateMessage(oldTable,
                newTable, receiver, sender).split("\n")[5];
        for (String v : addedTable.keySet()) {
            Assertions.assertTrue(resultString.contains(v));
        }
        Assertions.assertEquals(addedTable.size(), resultString.split(", ").length);
    }
}
