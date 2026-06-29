import { defineStore } from "pinia";
import api from "../services/api";

export const useAuthStore = defineStore("auth", {

    state: () => ({

        user: JSON.parse(localStorage.getItem("user")) || null,

        token: localStorage.getItem("token") || null,

        loading: false

    }),

    getters: {

        isAuthenticated: (state) => !!state.token,

        username: (state) => state.user?.username || ""

    },

    actions: {

        async login(credentials) {

            this.loading = true;

            try {

                const response = await api.post("/auth/login", credentials);

                this.token = response.data.token;

                this.user = response.data.user;

                localStorage.setItem("token", this.token);

                localStorage.setItem(
                    "user",
                    JSON.stringify(this.user)
                );

                return response.data;

            } finally {

                this.loading = false;

            }

        },

        logout() {

            this.user = null;

            this.token = null;

            localStorage.removeItem("token");

            localStorage.removeItem("user");

        }

    }

});