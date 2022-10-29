import java.util.List;

public record TablesDifferences(List<String> removed, List<String> updated,
                      List<String> added) {
}