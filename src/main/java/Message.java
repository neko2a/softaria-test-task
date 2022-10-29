public class Message {

    private static final String MESSAGE_TEXT = """
            Здравствуйте, %s!

            За последние сутки во вверенных Вам сайтах произошли следующие изменения:

            Исчезли следующие страницы: %s.
            Появились следующие новые страницы: %s.
            Изменились следующие страницы: %s.

            С уважением,
            %s.""";

    public String generateMessage(String receiver, String sender, TablesDifferences differences) {
        return String.format(MESSAGE_TEXT, receiver, differences.removed(), differences.added(), differences.updated(), sender);
    }

}
