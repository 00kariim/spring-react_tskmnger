import api from './api.js';

export const dataService = {
  // Projects
  async getProjects() {
    const response = await api.get('/projects');
    return response.data;
  },

  async getProjectDetails(projectId) {
    const response = await api.get(`/projects/${projectId}`);
    return response.data;
  },

  async createProject(title, description = '') {
    const response = await api.post('/projects', { title, description });
    return response.data;
  },

  async getProjectProgress(projectId) {
    const response = await api.get(`/projects/${projectId}/progress`);
    return response.data;
  },

  // Tasks
  async getTasks(projectId) {
    const response = await api.get(`/projects/${projectId}/tasks`);
    return response.data;
  },

  async createTask(projectId, title, description = '', dueDate) {
    const response = await api.post(`/projects/${projectId}/tasks`, {
      title,
      description,
      dueDate,
    });
    return response.data;
  },

  async completeTask(projectId, taskId) {
    const response = await api.post(`/projects/${projectId}/tasks/${taskId}/complete`);
    return response.data;
  },

  async deleteTask(projectId, taskId) {
    const response = await api.delete(`/projects/${projectId}/tasks/${taskId}`);
    return response.data;
  },
};

