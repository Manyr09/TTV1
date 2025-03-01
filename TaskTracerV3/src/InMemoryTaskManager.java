import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Manager.getDefaultHistory();
    private int nextId = 1;

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }
    @Override
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>(tasks.values());
        for (Task task : allTasks) {
            historyManager.add(task);
        }
        return allTasks;
    }

    @Override
    public List<Epic> getAllEpics() {
        List<Epic> allEpics = new ArrayList<>(epics.values());
        for (Epic epic : allEpics) {
            historyManager.add(epic);
        }
        return allEpics;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> allSubtasks = new ArrayList<>(subtasks.values());
        for (Subtask subtask : allSubtasks) {
            historyManager.add(subtask);
        }
        return allSubtasks;
    }

    @Override
    public void createTask(Task task) {
        task.setId(generateUniqueId());
        tasks.put(task.getId(), task);
    }
    @Override
    public void createEpic(Epic epic) {
        epic.setId(generateUniqueId());
        epics.put(epic.getId(), epic);
    }
    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(generateUniqueId());
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpic());

        if (epic != null) {
            epic.addSubtask(subtask);
            epic.updateStatus();
        }
    }
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        epic.updateStatus();
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpic());
        if (epic != null) {
            epic.updateStatus();
        }
    }
    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }
    @Override
    public void deleteEpicById(int id) {
        epics.remove(id);
        List<Subtask> subtasksToRemove = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpic().getId() == id) {
                subtasksToRemove.add(subtask);
            }
        }
        for (Subtask subtask : subtasksToRemove) {
            subtasks.remove(subtask.getId());
        }

    }
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }
    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }
    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            epic.updateStatus();
        }
    }
    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            subtasks.remove(id);
            Epic epic = epics.get(subtask.getEpic());
            if (epic != null) {
                epic.removeSubtaskById(id);
                epic.updateStatus();
            }
        }
    }
    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpic().getId() == epicId) {
                epicSubtasks.add(subtask);
                historyManager.add(subtask);
            }
        }
        return epicSubtasks;
    }

    private int generateUniqueId() {
        return nextId++;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}