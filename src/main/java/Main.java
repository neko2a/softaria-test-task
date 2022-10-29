import java.util.Hashtable;

public class Main {
    public static void main(String[] args) {
        Hashtable<String, String> yesterdayTable = new Hashtable<>();
        Hashtable<String, String> todayTable = new Hashtable<>();
        Message message = new Message();

        yesterdayTable.put("https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html", "old_Hashtable.html");
        yesterdayTable.put("https://translate.google.com/", "old_translate");
        yesterdayTable.put("https://www.geeksforgeeks.org/how-to-iterate-through-hashtable-in-java/", "old_iter");

        todayTable.put("https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html", "new_Hashtable.html");
        todayTable.put("https://translate.google.com/", "old_translate");
        todayTable.put("https://vk.com/", "new_vk");

        System.out.println(message.generateMessage("Наталья Ивановна", "автоматизированная система мониторинга",
                TablesService.findDifferencesParallel(yesterdayTable, todayTable)));

        System.out.println(message.generateMessage("Наталья Ивановна", "автоматизированная система мониторинга",
                TablesService.findDifferences(yesterdayTable, todayTable)));
    }


}
