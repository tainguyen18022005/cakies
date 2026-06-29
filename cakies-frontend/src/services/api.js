import axios from "axios";

const api = axios.create({

    baseURL: "http://localhost:8080/api",

    headers: {

        "Content-Type": "application/json"

    }

});

api.interceptors.request.use(config => {

    const sessionId = localStorage.getItem("sessionId");

    if (sessionId) {

        config.headers["X-Session-Id"] = sessionId;

    }

    const token = localStorage.getItem("token");

    if (token) {

        config.headers.Authorization = `Bearer ${token}`;

    }

    return config;

});

api.interceptors.response.use(

    response => {

        if (
            response.data &&
            response.data.sessionId
        ) {

            localStorage.setItem(
                "sessionId",
                response.data.sessionId
            );

        }

        return response;

    },

    error => {

        if (error.response?.status === 401) {

            localStorage.removeItem("token");

            localStorage.removeItem("user");

        }

        return Promise.reject(error);

    }

);

export default api;
