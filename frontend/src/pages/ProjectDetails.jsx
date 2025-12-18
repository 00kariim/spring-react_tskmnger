import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { dataService } from '../services/dataService.js';
import ProgressBar from '../components/ProgressBar.jsx';
import TaskItem from '../components/TaskItem.jsx';
import Button from '../components/Button.jsx';
import './ProjectDetails.css';

const ProjectDetails = () => {
  const { projectId } = useParams();
  const navigate = useNavigate();
  const [project, setProject] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [progress, setProgress] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [newTaskTitle, setNewTaskTitle] = useState('');
  const [newTaskDescription, setNewTaskDescription] = useState('');
  const [newTaskDueDate, setNewTaskDueDate] = useState('');
  const [creating, setCreating] = useState(false);

  useEffect(() => {
    loadProjectData();
  }, [projectId]);

  const loadProjectData = async () => {
    try {
      setLoading(true);
      setError('');
      const [projectData, tasksData, progressData] = await Promise.all([
        dataService.getProjectDetails(projectId),
        dataService.getTasks(projectId),
        dataService.getProjectProgress(projectId),
      ]);
      setProject(projectData);
      setTasks(tasksData);
      setProgress(progressData);
    } catch (err) {
      setError('Failed to load project data. Please try again.');
      console.error('Error loading project:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTask = async (e) => {
    e.preventDefault();
    if (!newTaskTitle.trim()) {
      setError('Task title is required');
      return;
    }

    try {
      setCreating(true);
      setError('');
      // Convert datetime-local format to ISO 8601 format
      const dueDateISO = newTaskDueDate
        ? new Date(newTaskDueDate).toISOString()
        : undefined;
      await dataService.createTask(
        projectId,
        newTaskTitle.trim(),
        newTaskDescription.trim(),
        dueDateISO
      );
      setShowCreateForm(false);
      setNewTaskTitle('');
      setNewTaskDescription('');
      setNewTaskDueDate('');
      await loadProjectData();
    } catch (err) {
      setError('Failed to create task. Please try again.');
      console.error('Error creating task:', err);
    } finally {
      setCreating(false);
    }
  };

  const handleCompleteTask = async (projectId, taskId) => {
    try {
      await dataService.completeTask(projectId, taskId);
      await loadProjectData();
    } catch (err) {
      setError('Failed to complete task. Please try again.');
      console.error('Error completing task:', err);
    }
  };

  const handleDeleteTask = async (projectId, taskId) => {
    try {
      await dataService.deleteTask(projectId, taskId);
      await loadProjectData();
    } catch (err) {
      setError('Failed to delete task. Please try again.');
      console.error('Error deleting task:', err);
    }
  };

  if (loading) {
    return (
      <div className="project-details-container">
        <div className="loading">Loading project...</div>
      </div>
    );
  }

  if (!project) {
    return (
      <div className="project-details-container">
        <div className="error-message">Project not found</div>
        <Button onClick={() => navigate('/')}>Back to Dashboard</Button>
      </div>
    );
  }

  return (
    <div className="project-details-container">
      <div className="project-header">
        <Button variant="secondary" onClick={() => navigate('/')}>
          ← Back to Dashboard
        </Button>
        <div className="project-info">
          <h1>{project.title}</h1>
          {project.description && <p className="project-description">{project.description}</p>}
        </div>
      </div>

      {progress && (
        <ProgressBar
          progressPercentage={progress.progressPercentage}
          completedTasks={progress.completedTasks}
          totalTasks={progress.totalTasks}
        />
      )}

      {error && <div className="error-message">{error}</div>}

      <div className="tasks-section">
        <div className="tasks-header">
          <h2>Tasks</h2>
          <Button
            variant="primary"
            onClick={() => setShowCreateForm(!showCreateForm)}
          >
            {showCreateForm ? 'Cancel' : '+ New Task'}
          </Button>
        </div>

        {showCreateForm && (
          <div className="create-task-form">
            <h3>Create New Task</h3>
            <form onSubmit={handleCreateTask}>
              <div className="form-group">
                <label htmlFor="task-title">Title *</label>
                <input
                  id="task-title"
                  type="text"
                  value={newTaskTitle}
                  onChange={(e) => setNewTaskTitle(e.target.value)}
                  placeholder="Enter task title"
                  required
                  disabled={creating}
                />
              </div>
              <div className="form-group">
                <label htmlFor="task-description">Description</label>
                <textarea
                  id="task-description"
                  value={newTaskDescription}
                  onChange={(e) => setNewTaskDescription(e.target.value)}
                  placeholder="Enter task description (optional)"
                  rows="3"
                  disabled={creating}
                />
              </div>
              <div className="form-group">
                <label htmlFor="task-due-date">Due Date</label>
                <input
                  id="task-due-date"
                  type="datetime-local"
                  value={newTaskDueDate}
                  onChange={(e) => setNewTaskDueDate(e.target.value)}
                  disabled={creating}
                />
              </div>
              <Button type="submit" variant="primary" disabled={creating}>
                {creating ? 'Creating...' : 'Create Task'}
              </Button>
            </form>
          </div>
        )}

        {tasks.length === 0 ? (
          <div className="empty-state">
            <p>No tasks yet. Create your first task to get started!</p>
          </div>
        ) : (
          <div className="tasks-list">
            {tasks.map((task) => (
              <TaskItem
                key={task.id}
                task={task}
                projectId={projectId}
                onComplete={handleCompleteTask}
                onDelete={handleDeleteTask}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProjectDetails;

