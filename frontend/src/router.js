import Vue from "vue";
import Router from 'vue-router'
import Index from "@/components/Index";
import Login from "@/components/Login";
import Employee from '@/components/Employee'
import EditInterview from '@/components/EditInterview'
import EditDepartment from '@/components/EditDepartment'
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
import Test from "@/components/Test";

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
            name: 'Index',
            component: Index
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
            name: 'EditInterview',
            component: EditInterview
        },
        {
            path: '/departments/:id',
            name: 'EditDepartment',
            component: EditDepartment
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
            path: '/users/:id',
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
            path: '/employees/:id',
            name: 'EditEmployee',
            component: EditEmployee
        },
        {
            path: '/departments/add',
            name: 'CreateDepartment',
            component: CreateDepartment
        },
        {
            path: '/test',
            name: 'Test',
            component: Test
        }
    ]
})


