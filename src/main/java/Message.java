import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Message {

    /**
     * @param oldTable old urls table
     * @param newTable new urls table
     * @return Information about changes between two tables
     */
    private static Answer findDifferences(Hashtable<String, String> oldTable,
                                          Hashtable<String, String> newTable) {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<List<String>> updatedFuture = service.submit(() ->
                oldTable.keySet().stream().filter(k ->
                        newTable.get(k) != null && !oldTable.get(k).equals(newTable.get(k))).toList());
        Future<List<String>> removedFuture = service.submit(() ->
                oldTable.keySet().stream().filter(k -> !newTable.containsKey(k)).toList());
        Future<List<String>> addedFuture = service.submit(() ->
                newTable.keySet().stream().filter(k -> !oldTable.containsKey(k)).toList());

        try {
            return new Answer(removedFuture.get(), updatedFuture.get(), addedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            service.shutdown();
        }
    }

    public String generateMessage(Hashtable<String, String> oldTable,
                                  Hashtable<String, String> newTable,
                                  String receiver,
                                  String sender) {
        Answer differences = findDifferences(oldTable, newTable);
        return String.format("""
                        Здравствуйте, %s!

                        За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                        Исчезли следующие страницы: %s.
                        Появились следующие новые страницы: %s.
                        Изменились следующие страницы: %s.

                        С уважением,
                        %s.""",
                receiver,
                differences.removed,
                differences.added,
                differences.updated,
                sender);
    }

    private record Answer(List<String> removed, List<String> updated,
                          List<String> added) {
    }
}
