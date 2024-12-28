import java.util.List;
import java.util.Collection;
public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
    void remove(int id);
    void remove(Collection<Integer> ids);
}
