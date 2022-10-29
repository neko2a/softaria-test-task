import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

public class TablesDifferentTest {
    @Test
    public void allTablesAreEmptyTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);
        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        Assertions.assertTrue(resultParallel.added().isEmpty());
        Assertions.assertTrue(resultParallel.removed().isEmpty());
        Assertions.assertTrue(resultParallel.updated().isEmpty());
        Assertions.assertTrue(result.added().isEmpty());
        Assertions.assertTrue(result.removed().isEmpty());
        Assertions.assertTrue(result.updated().isEmpty());
    }

    @Test
    public void oldTableEmptyTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();

        for (int i = 0; i < 3; i++) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);
        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        for (String v : newTable.keySet()) {
            Assertions.assertTrue(resultParallel.added().contains(v));
            Assertions.assertTrue(result.added().contains(v));
        }

        Assertions.assertTrue(resultParallel.removed().isEmpty());
        Assertions.assertTrue(resultParallel.updated().isEmpty());
        Assertions.assertTrue(result.removed().isEmpty());
        Assertions.assertTrue(result.updated().isEmpty());
    }

    @Test
    public void newTableEmptyTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();


        for (int i = 0; i < 3; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);
        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        for (String v : oldTable.keySet()) {
            Assertions.assertTrue(resultParallel.removed().contains(v));
            Assertions.assertTrue(result.removed().contains(v));
        }

        Assertions.assertTrue(resultParallel.added().isEmpty());
        Assertions.assertTrue(resultParallel.updated().isEmpty());
        Assertions.assertTrue(result.added().isEmpty());
        Assertions.assertTrue(result.updated().isEmpty());
    }

    @Test
    public void updatedTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> updatedTable = new Hashtable<>();

        for (int i = 0; i < 20; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 0; i < 20; i += 2) {
            newTable.put("www." + i + ".com", "New important code " + i + ".html");
            updatedTable.put("www." + i + ".com", "New important code " + i + ".html");
        }

        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);
        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        for (String v : updatedTable.keySet()) {
            Assertions.assertTrue(resultParallel.updated().contains(v));
            Assertions.assertTrue(result.updated().contains(v));
        }

        Assertions.assertTrue(resultParallel.added().isEmpty());
        Assertions.assertTrue(resultParallel.removed().isEmpty());
        Assertions.assertTrue(result.added().isEmpty());
        Assertions.assertTrue(result.removed().isEmpty());
    }

    @Test
    public void removedTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> removedTable = new Hashtable<>();

        for (int i = 0; i < 20; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
            removedTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 0; i < 20; i += 2) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
            removedTable.remove("www." + i + ".com");
        }

        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);
        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        for (String v : removedTable.keySet()) {
            Assertions.assertTrue(resultParallel.removed().contains(v));
            Assertions.assertTrue(result.removed().contains(v));
        }

        Assertions.assertTrue(resultParallel.added().isEmpty());
        Assertions.assertTrue(resultParallel.updated().isEmpty());
        Assertions.assertTrue(result.added().isEmpty());
        Assertions.assertTrue(result.updated().isEmpty());
    }

    @Test
    public void addedTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> addedTable = new Hashtable<>();

        for (int i = 0; i < 10; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }
        for (int i = 15; i < 29; i += 2) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
            addedTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);
        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        for (String v : addedTable.keySet()) {
            Assertions.assertTrue(resultParallel.added().contains(v));
            Assertions.assertTrue(result.added().contains(v));
        }

        Assertions.assertTrue(resultParallel.removed().isEmpty());
        Assertions.assertTrue(resultParallel.updated().isEmpty());
        Assertions.assertTrue(result.removed().isEmpty());
        Assertions.assertTrue(result.updated().isEmpty());
    }

    @Test
    public void generalTest() {
        Hashtable<String, String> oldTable = new Hashtable<>();
        Hashtable<String, String> newTable = new Hashtable<>();
        Hashtable<String, String> addedTable = new Hashtable<>();
        Hashtable<String, String> removedTable = new Hashtable<>();
        Hashtable<String, String> updatedTable = new Hashtable<>();

        for (int i = 0; i < 20; i++) {
            oldTable.put("www." + i + ".com", "Important code " + i + ".html");
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        for (int i = 0; i < 10; i += 2) {
            newTable.remove("www." + i + ".com");
            removedTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        for (int i = 10; i < 20; i += 2) {
            newTable.put("www." + i + ".com", "New important code " + i + ".html");
            updatedTable.put("www." + i + ".com", "New important code " + i + ".html");
        }

        for (int i = 20; i < 30; i++) {
            newTable.put("www." + i + ".com", "Important code " + i + ".html");
            addedTable.put("www." + i + ".com", "Important code " + i + ".html");
        }

        TablesDifferences resultParallel = TablesService.findDifferencesParallel(oldTable, newTable);

        for (String v : addedTable.keySet()) {
            Assertions.assertTrue(resultParallel.added().contains(v));
        }
        for (String v : removedTable.keySet()) {
            Assertions.assertTrue(resultParallel.removed().contains(v));
        }
        for (String v : updatedTable.keySet()) {
            Assertions.assertTrue(resultParallel.updated().contains(v));
        }

        TablesDifferences result = TablesService.findDifferences(oldTable, newTable);

        for (String v : addedTable.keySet()) {
            Assertions.assertTrue(result.added().contains(v));
        }
        for (String v : removedTable.keySet()) {
            Assertions.assertTrue(result.removed().contains(v));
        }
        for (String v : updatedTable.keySet()) {
            Assertions.assertTrue(result.updated().contains(v));
        }
    }
}
