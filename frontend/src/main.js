import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from './router'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css'
import SortedTablePlugin from "vue-sorted-table";
import BootstrapVue from 'bootstrap-vue';
import {store} from './store';
import _ from "lodash";
import VueTableDynamic from 'vue-table-dynamic';

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
