import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        remove(task.getId());
        history.add(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }


    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }


    @Override
    public void remove(int id) {
        history.removeIf(task -> task.getId() == id);
    }

    @Override
    public void remove(Collection<Integer> ids) {
        for (Integer id : ids) {
            remove(id);
        }
    }
}
