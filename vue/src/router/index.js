import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";

Vue.use(VueRouter)

const routes = [

  //  ===   登录   ===
  { path: '/login', name: 'Login', component: () => import('@/views/login/Login.vue') },

  //  ===   注册   ===
  { path: '/register', name: 'Register', component: () => import('@/views/register/register.vue') },

  //  ===   一级路由   ===
  { path: '/', name: 'Layout', component: Layout, redirect: '/home',

    // 二级路由
    children: [

      //  ===   主页   ===
      { path: 'home', name: 'Home', component: () => import('@/views/home/HomeView.vue') },

      //  ===   Admin  ===
      { path: 'adminList', name: 'Admin', component: () => import('@/views/admin/AdminList.vue') },
      { path: 'addAdmin', name: 'AddAdmin', component: () => import('@/views/admin/AddAdmin.vue') },
      { path: 'editAdmin', name: 'EditAdmin', component: () => import('@/views/admin/EditAdmin.vue') },

      //  ===   User  ===
      { path: 'userList', name: 'User', component: () => import('@/views/user/UserList.vue') },
      { path: 'addUser', name: 'AddUser', component: () => import('@/views/user/AddUser.vue') },
      { path: 'editUser', name: 'EditUser', component: () => import('@/views/user/EditUser.vue') },

      // ===  Category  ===
      { path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/CategoryList.vue') },
      { path:  'addCategory', name: 'AddCategory', component: () => import('@/views/category/AddCategory.vue') },
      { path:  'editCategory', name: 'EditCategory', component: () => import('@/views/category/EditCategory.vue')},

      // ===  Book  ===
      { path: 'bookList', name:  'BookList', component: () => import('@/views/book/BookList.vue') },
      { path: 'addBook', name: 'AddBook', component: () => import('@/views/book/AddBook.vue') },
      { path: 'editBook', name: 'EditBook', component: () => import('@/views/book/EditBook.vue') },

      // ===  Borrow  ===
      { path: 'borrowList', name:  'BorrowList', component: () => import('@/views/borrow/BorrowList.vue') },
      { path: 'addBorrow', name: 'AddBorrow', component: () => import('@/views/borrow/AddBorrow.vue') },
      { path: 'editBorrow', name: 'EditBorrow', component: () => import('@/views/borrow/EditBorrow.vue') },

      // ===  Return  ===
      { path: 'returnList', name:  'ReturnList', component: () => import('@/views/return/ReturnList.vue') },
    ]
  },
  { path: "*", component: () => import('@/views/404.vue') }
]

const router = new VueRouter({ mode: 'history', base: process.env.BASE_URL, routes })

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  }
  const admin = Cookies.get("admin")
  if (!admin && to.path !== '/login' && to.path !== '/register') {
    return next('/login')
  }
  next()
})

// 针对ElementUI导航栏中重复导航报错问题
// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err)
// }

export default router
