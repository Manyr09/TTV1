import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static TaskManager manager = Manager.getDefault();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            System.out.println("Введите ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createTaskMenu();
                    break;
                case 2:
                    updateTaskMenu();
                    break;
                case 3:
                    deleteTaskMenu();
                    break;
                case 4:
                    viewTasksMenu();
                    break;
                case 5:
                    viewHistory();
                    break;
                case 0:
                    System.out.println("Выходим из программы.");
                    return;
                default:
                    System.out.println("Ошибка. Пожалуйста, введите число от 0 до 5.");
            }
        }
    }


    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Создать задачу");
        System.out.println("2. Обновить задачу");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Просмотр задач");
        System.out.println("5. История просмотров");
        System.out.println("0. Выйти");
    }


    private static void createTaskMenu() {
        System.out.println("\nСоздать:");
        System.out.println("1. Задачу");
        System.out.println("2. Эпик");
        System.out.println("3. Подзадачу");
        System.out.println("0. Назад");

        System.out.print("Введите ваш выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                createTask();
                break;
            case 2:
                createEpic();
                break;
            case 3:
                createSubtask();
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }
    }


    private static void updateTaskMenu() {
        System.out.println("\nОбновить:");
        System.out.println("1. Задачу");
        System.out.println("2. Эпик");
        System.out.println("3. Подзадачу");
        System.out.println("0. Назад");

        System.out.print("Введите ваш выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();


        switch (choice) {
            case 1:
                updateTask();
                break;
            case 2:
                updateEpic();
                break;
            case 3:
                updateSubtask();
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }

    }


    private static void deleteTaskMenu() {
        System.out.println("\nУдалить:");
        System.out.println("1. Задачу");
        System.out.println("2. Эпик");
        System.out.println("3. Подзадачу");
        System.out.println("4. Все задачи");
        System.out.println("5. Все эпики");
        System.out.println("6. Все подзадачи");
        System.out.println("0. Назад");

        System.out.print("Введите ваш выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                deleteTask();
                break;
            case 2:
                deleteEpic();
                break;
            case 3:
                deleteSubtask();
                break;
            case 4:
                manager.deleteAllTasks();
                System.out.println("Все задачи удалены.");
                break;
            case 5:
                manager.deleteAllEpics();
                System.out.println("Все эпики и подзадачи удалены.");
                break;
            case 6:
                manager.deleteAllSubtasks();
                System.out.println("Все подзадачи удалены.");
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }

    }


    private static void viewTasksMenu() {
        System.out.println("\nПросмотр:");
        System.out.println("1. Все задачи");
        System.out.println("2. Все эпики");
        System.out.println("3. Все подзадачи");
        System.out.println("4. Подзадачи эпика");
        System.out.println("5. Задача по ID");
        System.out.println("6. Эпик по ID");
        System.out.println("7. Подзадача по ID");
        System.out.println("0. Назад");

        System.out.print("Введите ваш выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAllTasks();
                break;
            case 2:
                viewAllEpics();
                break;
            case 3:
                viewAllSubtasks();
                break;
            case 4:
                viewSubtasksOfEpic();
                break;
            case 5:
                viewTaskById();
                break;
            case 6:
                viewEpicById();
                break;
            case 7:
                viewSubtaskById();
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void createTask() {
        System.out.print("Название задачи: ");
        String name = scanner.nextLine();
        System.out.print("Описание задачи: ");
        String description = scanner.nextLine();

        manager.createTask(new Task(name, description, TaskStatus.NEW));
        System.out.println("Задача создана.");
    }


    private static void updateTask() {
        System.out.print("Введите ID задачи для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Task existingTask = manager.getTaskById(id);

        if (existingTask != null) {
            System.out.print("Новое название задачи (оставьте пустым, чтобы не менять): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                existingTask.setName(name);
            }

            System.out.print("Новое описание задачи (оставьте пустым, чтобы не менять): ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) {
                existingTask.setDescription(description);
            }

            updateTaskStatus(existingTask);
            manager.updateTask(existingTask);
            System.out.println("Задача обновлена.");
        } else {
            System.out.println("Задача с таким ID не найдена.");
        }
    }


    private static void deleteTask() {
        System.out.print("Введите ID задачи для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        manager.deleteTaskById(id);
        System.out.println("Задача удалена.");
    }


    private static void viewAllTasks() {
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }


    private static void viewTaskById() {
        System.out.print("Введите ID задачи для просмотра: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Task task = manager.getTaskById(id);
        if (task != null) {
            System.out.println(task);
        } else {
            System.out.println("Задача с таким ID не найдена.");
        }
    }


    private static void createEpic() {
        System.out.print("Название эпика: ");
        String name = scanner.nextLine();
        System.out.print("Описание эпика: ");
        String description = scanner.nextLine();
        manager.createEpic(new Epic(name, description, TaskStatus.NEW));
        System.out.println("Эпик создан.");
    }


    private static void updateEpic() {
        System.out.print("Введите ID эпика для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Epic existingEpic = manager.getEpicById(id);

        if (existingEpic != null) {
            System.out.print("Новое название эпика (оставьте пустым, чтобы не менять): ");
            String name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                existingEpic.setName(name);
            }
            System.out.print("Новое описание эпика (оставьте пустым, чтобы не менять): ");
            String description = scanner.nextLine();

            if (!description.trim().isEmpty()) {
                existingEpic.setDescription(description);
            }
            manager.updateEpic(existingEpic);
            System.out.println("Эпик обновлен.");
        } else {
            System.out.println("Эпик с таким ID не найден.");
        }
    }


    private static void deleteEpic() {
        System.out.print("Введите ID эпика для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        manager.deleteEpicById(id);
        System.out.println("Эпик удален.");
    }


    private static void viewAllEpics() {
        List<Epic> epics = manager.getAllEpics();

        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        } else {
            for (Epic epic : epics) {
                System.out.println(epic);
            }
        }
    }


    private static void viewEpicById() {
        System.out.print("Введите ID эпика для просмотра: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Epic epic = manager.getEpicById(id);

        if (epic != null) {
            System.out.println(epic);
        } else {
            System.out.println("Эпик с таким ID не найден.");
        }
    }

    private static void viewSubtasksOfEpic() {
        System.out.print("Введите ID эпика для просмотра подзадач: ");
        int epicId = scanner.nextInt();
        scanner.nextLine();
        List<Subtask> subtasks = manager.getEpicSubtasks(epicId);

        if (subtasks.isEmpty()) {
            System.out.println("У этого эпика нет подзадач.");
        } else {
            for (Subtask subtask : subtasks) {
                System.out.println(subtask);
            }
        }
    }

    private static void createSubtask() {
        System.out.print("Введите ID эпика для новой подзадачи: ");
        int epicId = scanner.nextInt();
        scanner.nextLine();
        Epic epic = manager.getEpicById(epicId);

        if (epic == null) {
            System.out.println("Эпик с таким ID не найден.");
            return;
        }
        System.out.print("Название подзадачи: ");
        String name = scanner.nextLine();

        System.out.print("Описание подзадачи: ");
        String description = scanner.nextLine();
        manager.createSubtask(new Subtask(name, description, TaskStatus.NEW, epicId));
        System.out.println("Подзадача создана.");
    }


    private static void updateSubtask() {
        System.out.print("Введите ID подзадачи для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Subtask existingSubtask = manager.getSubtaskById(id);


        if (existingSubtask != null) {
            System.out.print("Новое название подзадачи (оставьте пустым, чтобы не менять): ");
            String name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                existingSubtask.setName(name);
            }

            System.out.print("Новое описание подзадачи (оставьте пустым, чтобы не менять): ");
            String description = scanner.nextLine();

            if (!description.trim().isEmpty()) {
                existingSubtask.setDescription(description);
            }
            updateTaskStatus(existingSubtask);
            manager.updateSubtask(existingSubtask);
            System.out.println("Подзадача обновлена.");
        } else {
            System.out.println("Подзадача с таким ID не найдена.");
        }
    }


    private static void deleteSubtask() {
        System.out.print("Введите ID подзадачи для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        manager.deleteSubtaskById(id);
        System.out.println("Подзадача удалена.");
    }


    private static void viewAllSubtasks() {
        List<Subtask> subtasks = manager.getAllSubtasks();

        if (subtasks.isEmpty()) {
            System.out.println("Список подзадач пуст.");
        } else {
            for (Subtask subtask : subtasks) {
                System.out.println(subtask);
            }
        }
    }


    private static void viewSubtaskById() {
        System.out.print("Введите ID подзадачи для просмотра: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Subtask subtask = manager.getSubtaskById(id);

        if (subtask != null) {
            System.out.println(subtask);
        } else {
            System.out.println("Подзадача с таким ID не найдена.");
        }
    }

    private static void updateTaskStatus(Task task) {
        System.out.println("Текущий статус: " + task.getStatus());
        System.out.println("Выберите новый статус:");
        System.out.println("1. NEW");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. DONE");

        System.out.print("Введите ваш выбор (или 0, чтобы не менять): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                task.setStatus(TaskStatus.NEW);
                break;
            case 2:
                task.setStatus(TaskStatus.IN_PROGRESS);
                break;
            case 3:
                task.setStatus(TaskStatus.DONE);
                break;
            case 0:
                break;
            default:
                System.out.println("Неверный выбор статуса. Статус остался прежним.");
        }
    }


    private static void viewHistory() {
        List<Task> history = manager.getHistory();
        if (history.isEmpty()) {
            System.out.println("История просмотров пуста.");
        } else {
            System.out.println("История просмотров:");
            for (Task task : history) {
                System.out.println(task);
            }
        }
    }
}