import { createRouter, createWebHistory } from "vue-router";

import HomeView from "../views/HomeView.vue";

import Login from "../views/Login.vue";

const router = createRouter({

    history: createWebHistory(import.meta.env.BASE_URL),

    routes: [

        {

            path: "/login",

            name: "login",

            component: Login

        },

        {

            path: "/",

            name: "home",

            component: HomeView,

            meta: {

                requiresAuth: true

            }

        },

        {

            path: "/product/:id",

            name: "productDetail",

            component: () =>
                import("../views/ProductDetailView.vue"),

            meta: {

                requiresAuth: true

            }

        },

        {

            path: "/cart",

            name: "cart",

            component: () =>
                import("../views/CartView.vue"),

            meta: {

                requiresAuth: true

            }

        },

        {

            path: "/checkout",

            name: "checkout",

            component: () =>
                import("../views/CheckoutView.vue"),

            meta: {

                requiresAuth: true

            }

        },

        {

            path: "/success/:orderId",

            name: "success",

            component: () =>
                import("../views/SuccessView.vue"),

            meta: {

                requiresAuth: true

            }

        }

    ]

});

router.beforeEach((to, from, next) => {

    const token = localStorage.getItem("token");

    if (

        to.meta.requiresAuth &&

        !token

    ) {

        next("/login");

        return;

    }

    if (

        to.path === "/login" &&

        token

    ) {

        next("/");

        return;

    }

    next();

});

export default router;
