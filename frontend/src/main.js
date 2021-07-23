import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from './router'
import Antd from 'ant-design-vue';
import SortedTablePlugin from "vue-sorted-table";
import BootstrapVue from 'bootstrap-vue';
import VueTableDynamic from 'vue-table-dynamic';
import {store} from './store';
import 'ant-design-vue/dist/antd.css'
import _ from "lodash";

Vue.use(VueRouter);
Vue.use(SortedTablePlugin);
Vue.use(Antd);
Vue.use(BootstrapVue);
Vue.use(VueTableDynamic)
Vue.prototype.$_ = _;

new Vue({
    router: routes,
    store,
    render: h => h(App),
    el: '#app',
    template: '<App/>'
})
