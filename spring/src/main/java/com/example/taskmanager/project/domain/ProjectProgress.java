package com.example.taskmanager.project.domain;

public class ProjectProgress {

    private final long totalTasks;
    private final long completedTasks;

    public ProjectProgress(long totalTasks, long completedTasks) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public double getProgressPercentage() {
        if (totalTasks == 0) {
            return 0.0;
        }
        return (completedTasks * 100.0) / totalTasks;
    }
}


