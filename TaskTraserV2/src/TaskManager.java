import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

interface TaskManager {
    Task getTaskById(int id);
    Epic getEpicById(int id);
    Subtask getSubtaskById(int id);
    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();
    List<Subtask> getEpicSubtasks(int epicId);
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTaskById(int id);
    void deleteAllTasks();
    void createEpic(Epic epic);
    void updateEpic(Epic epic);
    void deleteEpicById(int id);
    void deleteAllEpics();
    void createSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    void deleteSubtaskById(int id);
    void deleteAllSubtasks();
    List<Task> getHistory();
}