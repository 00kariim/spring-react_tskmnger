import './ProgressBar.css';

const ProgressBar = ({ progressPercentage, completedTasks, totalTasks }) => {
  return (
    <div className="progress-container">
      <div className="progress-header">
        <span className="progress-label">Progress</span>
        <span className="progress-percentage">{Math.round(progressPercentage)}%</span>
      </div>
      <div className="progress-bar-wrapper">
        <div
          className="progress-bar-fill"
          style={{ width: `${progressPercentage}%` }}
        />
      </div>
      <div className="progress-stats">
        <span>{completedTasks} of {totalTasks} tasks completed</span>
      </div>
    </div>
  );
};

export default ProgressBar;

