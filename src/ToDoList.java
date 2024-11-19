import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class ToDoList {
    public List<Task> tasks;
    public final String fileName = "tasks.txt";

    // Task Class
    public class Task {
        public String name;
        public String description;
        public String dueDate;
        public String priority;
        public boolean isCompleted;
        public boolean hasReminder;

        public Task(String name, String description, String dueDate, String priority) {
            this.name = name;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.isCompleted = false;
            this.hasReminder = false;  // Default, no reminder
        }

        // Getters and Setters
        public String getName()
        {
            return name;
        }
        public void setName(String name)
        {
            this.name = name;
        }
        public String getDescription()
        {
            return description;
        }
        public void setDescription(String description)
        {
            this.description = description;
        }
        public String getDueDate()
        {
            return dueDate;
        }
        public void setDueDate(String dueDate)
        {
            this.dueDate = dueDate;
        }
        public String getPriority()
        {
            return priority;
        }
        public void setPriority(String priority)
        {
            this.priority = priority;
        }
        public boolean isCompleted()
        {
            return isCompleted;
        }
        public void setCompleted(boolean completed)
        {
            isCompleted = completed;
        }
        public boolean hasReminder()
        {
            return hasReminder;
        }
        public void setReminder(boolean reminder)
        {
            this.hasReminder = reminder;
        }

        @Override

        public String toString() {
            return "\t   Task Name: " + name +
                    "\n\t   Description: " + description +
                    "\n\t   Due Date: " + dueDate +
                    "\n\t   Priority: " + priority +
                    "\n\t   Status: " + (isCompleted ? "Completed" : "Incomplete") +
                    "\n\t   Reminder: " + (hasReminder ? "Yes" : "No") +
                    "\n\t   ------------------------------";
        }

    }

    // Constructor
    public ToDoList() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    // Add Task
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Edit Task
    public void editTask(int index, String name, String description, String dueDate, String priority) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setName(name);
            task.setDescription(description);
            task.setDueDate(dueDate);
            task.setPriority(priority);
          System.out.println("Task updated successfully.");
        } else {
            System.out.println("Invalid task index!");
        }
    }

    // Delete Task
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Invalid task index!");
        }
    }

    // Display Tasks
    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        }
        else
        {
            for (int i = 0; i < tasks.size(); i++)
            {
                System.out.println((i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

    // Sort Tasks by Priority
    public void sortTasksByPriority() {
        Stack<Task> stack = new Stack<>();
        for (Task task : tasks) {
            if (task.getPriority().equals("Low")) {
                stack.push(task);
            }
        }
        for (Task task : tasks) {
            if (task.getPriority().equals("Medium")) {
                stack.push(task);
            }
        }
        for (Task task : tasks) {
            if (task.getPriority().equals("High")) {
                stack.push(task);
            }
        }
        tasks.clear();
        while (!stack.isEmpty()) {
            tasks.add(stack.pop());
        }
    }

    // Sort Tasks by Due Date


    public void sortTasksByDueDate() {
        // Define a formatter to parse dates in "dd-MM-yyyy" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Bubble Sort implementation
        for (int i = 0; i < tasks.size() - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < tasks.size() - i - 1; j++) {
                // Parse the due dates of the two tasks being compared
                LocalDate date1 = LocalDate.parse(tasks.get(j).getDueDate(), formatter);
                LocalDate date2 = LocalDate.parse(tasks.get(j + 1).getDueDate(), formatter);

                // Compare parsed dates and swap if out of order
                if (date1.isAfter(date2)) {
                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                    swapped = true;
                }
            }

            // Break early if no swaps were made during this pass
            if (!swapped) {
                break;
            }
        }
    }



//        tasks.clear();
//        tasks.addAll(queue);
//    }

    // Filter Tasks by Completion Status
    public void filterTasksByCompletion(boolean completed) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.isCompleted() == completed) {
                System.out.println(task.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with the specified completion status.");
        }
    }

    // Save Tasks
    public void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(task.getName() + "," + task.getDescription() + "," + task.getDueDate() + "," +
                        task.getPriority() + "," + task.isCompleted() + "," + task.hasReminder() + "\n");
            }  System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Load Tasks
    public void loadTasks() {
        if (!tasks.isEmpty()) {
            System.out.println("Tasks are already loaded into memory. Clear the list to reload.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskDetails = line.split(",");
                if (taskDetails.length == 6) {
                    Task task = new Task(taskDetails[0], taskDetails[1], taskDetails[2], taskDetails[3]);
                    task.setCompleted(Boolean.parseBoolean(taskDetails[4]));
                    task.setReminder(Boolean.parseBoolean(taskDetails[5]));
                    tasks.add(task);
                }
            }
            System.out.println("Tasks loaded successfully.");
        } catch (IOException e) {
            System.out.println("No previous task file found, starting fresh.");
        }
    }

}