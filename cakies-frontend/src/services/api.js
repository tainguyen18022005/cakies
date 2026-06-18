import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Request interceptor to add X-Session-Id
api.interceptors.request.use(config => {
    const sessionId = localStorage.getItem('sessionId');
    if (sessionId) {
        config.headers['X-Session-Id'] = sessionId;
    }
    return config;
});

// Response interceptor to save new X-Session-Id
api.interceptors.response.use(response => {
    // If backend returns session ID in body or we extract from cart response
    if (response.data && response.data.sessionId) {
        localStorage.setItem('sessionId', response.data.sessionId);
    }
    return response;
});

export default api;
