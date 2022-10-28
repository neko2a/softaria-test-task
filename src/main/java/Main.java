import java.util.Hashtable;

public class Main {
    public static void main(String[] args) {
        Hashtable<String, String> yesterdayTable = new Hashtable<>();
        Hashtable<String, String> todayTable = new Hashtable<>();

        yesterdayTable.put("https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html", "old_Hashtable.html");
        yesterdayTable.put("https://translate.google.com/", "old_translate");
        yesterdayTable.put("https://www.geeksforgeeks.org/how-to-iterate-through-hashtable-in-java/", "old_iter");

        todayTable.put("https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html", "new_Hashtable.html");
        todayTable.put("https://translate.google.com/", "old_translate");
        todayTable.put("https://vk.com/", "new_vk");

        Message message = new Message();
        System.out.println(message.generateMessage(yesterdayTable, todayTable,
                "Наталья Ивановна",
                "автоматизированная система мониторинга"));
    }


}
