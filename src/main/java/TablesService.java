import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TablesService {
    public static TablesDifferences findDifferencesParallel(Hashtable<String, String> oldTable,
                                                            Hashtable<String, String> newTable) {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<List<String>> updatedFuture = service.submit(() -> findUpdated(oldTable, newTable));
        Future<List<String>> removedFuture = service.submit(() -> findRemoved(oldTable, newTable));
        Future<List<String>> addedFuture = service.submit(() -> findAdded(oldTable, newTable));

        try {
            return new TablesDifferences(removedFuture.get(), updatedFuture.get(), addedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            service.shutdown();
        }
    }

    public static TablesDifferences findDifferences(Hashtable<String, String> oldTable,
                                                    Hashtable<String, String> newTable) {
        List<String> updated = new ArrayList<>();
        List<String> added = new ArrayList<>();
        List<String> removed = new ArrayList<>();

        newTable.keySet().forEach(url -> {
            if (!oldTable.containsKey(url)) {
                added.add(url);
            } else if (!oldTable.get(url).equals(newTable.get(url))) {
                updated.add(url);
            }
        });

        oldTable.keySet().forEach(url -> {
            if (!newTable.containsKey(url)) {
                removed.add(url);
            }
        });

        return new TablesDifferences(removed, updated, added);
    }


    private static List<String> findUpdated(Hashtable<String, String> oldTable,
                                            Hashtable<String, String> newTable) {
        return oldTable.keySet().stream().filter(k ->
                newTable.get(k) != null && !oldTable.get(k).equals(newTable.get(k))).toList();
    }

    private static List<String> findRemoved(Hashtable<String, String> oldTable,
                                            Hashtable<String, String> newTable) {
        return oldTable.keySet().stream().filter(k -> !newTable.containsKey(k)).toList();
    }

    private static List<String> findAdded(Hashtable<String, String> oldTable,
                                          Hashtable<String, String> newTable) {
        return newTable.keySet().stream().filter(k -> !oldTable.containsKey(k)).toList();
    }

}
