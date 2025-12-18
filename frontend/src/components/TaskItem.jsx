import { useState } from 'react';
import Button from './Button.jsx';
import './TaskItem.css';

const TaskItem = ({ task, onComplete, onDelete, projectId }) => {
  const [isDeleting, setIsDeleting] = useState(false);

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const isOverdue = () => {
    if (task.completed || !task.dueDate) return false;
    return new Date(task.dueDate) < new Date();
  };

  const handleComplete = async () => {
    if (!task.completed && onComplete) {
      await onComplete(projectId, task.id);
    }
  };

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this task?')) {
      setIsDeleting(true);
      if (onDelete) {
        await onDelete(projectId, task.id);
      }
      setIsDeleting(false);
    }
  };

  return (
    <div className={`task-item ${task.completed ? 'task-completed' : ''} ${isOverdue() ? 'task-overdue' : ''}`}>
      <div className="task-item-content">
        <div className="task-item-header">
          <h4 className="task-item-title">{task.title}</h4>
          <div className="task-item-actions">
            {!task.completed && (
              <Button
                variant="success"
                onClick={handleComplete}
                className="btn-small"
              >
                Complete
              </Button>
            )}
            <Button
              variant="danger"
              onClick={handleDelete}
              disabled={isDeleting}
              className="btn-small"
            >
              {isDeleting ? 'Deleting...' : 'Delete'}
            </Button>
          </div>
        </div>
        {task.description && (
          <p className="task-item-description">{task.description}</p>
        )}
        <div className="task-item-footer">
          {task.dueDate && (
            <span className={`task-item-due ${isOverdue() ? 'task-overdue-text' : ''}`}>
              Due: {formatDate(task.dueDate)}
            </span>
          )}
          {task.completed && (
            <span className="task-item-status">✓ Completed</span>
          )}
        </div>
      </div>
    </div>
  );
};

export default TaskItem;

