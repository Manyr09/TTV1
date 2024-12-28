import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString(){
        return String.format("Эпик %s: %s - %s. Статус - %s. Подзадачи по Id: %s", getId(), getName(), getDescription(), getStatus(), getSubtaskIds());
    }
}