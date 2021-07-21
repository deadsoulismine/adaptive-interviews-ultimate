import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import initialState from "ant-design-vue/lib/vc-slick/src/initial-state";

Vue.use(Vuex);

const state = {
    jwtToken: localStorage.getItem('user-jwtToken') || '',
    role: localStorage.getItem('user-role') || '',
    username: localStorage.getItem('user-name') || '',
    authorities: localStorage.getItem('authorities') || '',
};

const getters = {
    isAuthenticated: state => {
        return state.jwtToken != null && state.jwtToken !== '';
    },
    isAdmin: state => {
        return state.role === 'admin';
    },
    getUsername: state => {
        return state.name;
    },
    getAuthorities: state => {
        return state.authorities;
    },
    getToken: state => {
        return state.jwtToken;
    }
};

const mutations = {
    auth_login: (state, user) => {
        localStorage.setItem('user-jwtToken', user.jwtToken);
        localStorage.setItem('user-name', user.name);
        localStorage.setItem('user-authorities', user.roles);
        state.jwtToken = user.jwtToken;
        state.name = user.name;
        state.authorities = user.roles;
        let isUser = false;
        let isAdmin = false;
        for (let i = 0; i < user.roles.length; i++) {
            if (user.roles[i].authority === 'ROLE_USER') {
                isUser = true;
            } else if (user.roles[i].authority === 'ROLE_ADMIN') {
                isAdmin = true;
            }
        }
        if (isUser) {
            localStorage.setItem('user-role', 'user');
            state.role = 'user';
        }
        if (isAdmin) {
            localStorage.setItem('user-role', 'admin');
            state.role = 'admin';
        }
    },
    auth_logout: (state) => {
        state.jwtToken = '';
        state.role = '';
        state.name = '';
        state.authorities = [];
        localStorage.removeItem('user-jwtToken');
        localStorage.removeItem('user-role');
        localStorage.removeItem('user-name');
        localStorage.removeItem('user-authorities');
    },
    reset(state) {
        Object.keys(state).forEach(key => {
            Object.assign(state[key], initialState[key])
        })
    }
};

const actions = {
    login: (context, user) => {
        context.commit('auth_login', user)
    },
    logout: (context) => {
        context.commit('auth_logout');
    }
};

export const store = new Vuex.Store({
    plugins: [createPersistedState()],
    state,
    getters,
    mutations,
    actions
});
