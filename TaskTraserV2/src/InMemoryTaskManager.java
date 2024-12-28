import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
public class InMemoryTaskManager implements TaskManager{
    private int nextId = 1;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Manager.getDefaultHistory();

    private int generateId() {
        return nextId++;
    }


    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task; // Return the original task, not a new one
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic; // Return the original epic, not a copy
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
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }


    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> subtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask originalSubtask = this.subtasks.get(subtaskId);
                if (originalSubtask != null) {
                    subtasks.add(getSubtaskById(subtaskId));
                }
            }
        }
        return subtasks;
    }

    @Override
    public void createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }



    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }


    @Override
    public void deleteTaskById(int id) {
        Task removedTask = tasks.remove(id);
        if (removedTask != null) {
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        historyManager.remove(tasks.keySet());

    }


    @Override
    public void createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            historyManager.remove(id);
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
        }
    }


    @Override
    public void deleteAllEpics() {
        epics.clear();
        historyManager.remove(epics.keySet());

        subtasks.clear();
        historyManager.remove(subtasks.keySet());
    }




    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtaskIds().add(subtask.getId());
            updateEpicStatus(epic);
        }
    }


    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            historyManager.remove(id);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(id));
                updateEpicStatus(epic);
            }
        }
    }


    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        historyManager.remove(subtasks.keySet());

        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        }
    }

    private void updateEpicStatus(Epic epic) {
        List<Subtask> subtasksOfEpic = getEpicSubtasks(epic.getId());
        if (subtasksOfEpic.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            boolean allNew = true;
            boolean allDone = true;
            for (Subtask subtask : subtasksOfEpic) {
                if (subtask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                }
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
            }


            if (allDone) {
                epic.setStatus(TaskStatus.DONE);
            } else if (allNew) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
