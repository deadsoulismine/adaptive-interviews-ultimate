import Vue from "vue";
import Router from 'vue-router'
import HelloWorld from "@/components/HelloWorld";
import Login from "@/components/Login";
import Employee from '@/components/Employee'
import Interview from '@/components/Interview'
import Department from '@/components/Department'
import Users from '@/components/Users'
import Interviews from '@/components/Interviews'
import Departments from '@/components/Departments'
import Employees from "@/components/Employees";
import EditUser from "@/components/EditUser";
import CreateInterview from "@/components/CreateInterview";
import CreateUser from "@/components/CreateUser";
import CreateEmployee from "@/components/CreateEmployee";
import EditEmployee from "@/components/EditEmployee";
import CreateDepartment from "@/components/CreateDepartment";

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/users',
            name: 'Users',
            component: Users
        },
        {
            path: '/',
            name: 'HelloWorld',
            component: HelloWorld
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/employees/:id',
            name: 'Employee',
            component: Employee
        },
        {
            path: '/interviews/:id',
            name: 'Interview',
            component: Interview
        },
        {
            path: '/departments/:id',
            name: 'Department',
            component: Department
        },
        {
            path: '/departments',
            name: 'Departments',
            component: Departments
        },
        {
            path: '/employees',
            name: 'Employees',
            component: Employees
        },
        {
            path: '/interviews',
            name: 'Interviews',
            component: Interviews
        },
        {
            path: '/users/edit/:id',
            name: 'EditUser',
            component: EditUser
        },
        {
            path: '/interviews/add',
            name: 'CreateInterview',
            component: CreateInterview
        },
        {
            path: '/users/add',
            name: 'CreateUser',
            component: CreateUser
        },
        {
            path: '/employees/add',
            name: 'CreateEmployee',
            component: CreateEmployee
        },
        {
            path: '/employees/edit/:id',
            name: 'EditEmployee',
            component: EditEmployee
        },

        {
            path: '/departments/add',
            name: 'CreateDepartment',
            component: CreateDepartment
        },
    ]
})


