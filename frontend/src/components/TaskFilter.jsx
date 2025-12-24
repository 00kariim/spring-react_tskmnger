import React, { useState } from 'react';
import './TaskFilter.css';

const TaskFilter = ({ onSearch, initialFilters = {} }) => {
  const [filters, setFilters] = useState({
    q: initialFilters.q || '',
    status: initialFilters.status || '',
    dueDate: initialFilters.dueDate || '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(filters);
  };

  const handleClear = () => {
    const empty = { q: '', status: '', dueDate: '' };
    setFilters(empty);
    onSearch(empty);
  };

  return (
    <form className="task-filter" onSubmit={handleSubmit}>
      <input
        className="filter-input"
        type="text"
        name="q"
        placeholder="Search tasks..."
        value={filters.q}
        onChange={handleChange}
      />

      <select
        className="filter-select"
        name="status"
        value={filters.status}
        onChange={handleChange}
      >
        <option value="">All Statuses</option>
        <option value="PENDING">Pending</option>
        <option value="IN_PROGRESS">In Progress</option>
        <option value="DONE">Done</option>
      </select>

      <input
        className="filter-date"
        type="date"
        name="dueDate"
        value={filters.dueDate}
        onChange={handleChange}
      />

      <div className="filter-actions">
        <button type="submit" className="filter-btn">Search</button>
        <button type="button" className="filter-clear" onClick={handleClear}>Clear</button>
      </div>
    </form>
  );
};

export default TaskFilter;
