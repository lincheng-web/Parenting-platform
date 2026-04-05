// 管理员API请求封装

const baseUrl = 'http://localhost:8081/api/admin';

// 请求拦截器
const request = (url, options = {}) => {
  const token = uni.getStorageSync('adminToken');
  options.header = options.header || {};
  if (token) {
    options.header.Authorization = `Bearer ${token}`;
  }
  return new Promise((resolve, reject) => {
    uni.request({
      url: `${baseUrl}${url}`,
      ...options,
      success: (res) => {
        resolve(res);
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
};

// 登录
export const login = async (data) => {
  const res = await request('/login', {
    method: 'POST',
    data
  });
  return res.data;
};

// 退出登录
export const logout = async () => {
  const res = await request('/logout', {
    method: 'POST'
  });
  return res.data;
};

// 用户管理
export const getUserList = async (params) => {
  const res = await request('/sys/admin', {
    method: 'GET',
    data: params
  });
  return res.data;
};

export const createUser = async (data) => {
  const res = await request('/sys/admin', {
    method: 'POST',
    data
  });
  return res.data;
};

export const updateUser = async (data) => {
  const res = await request('/sys/admin', {
    method: 'PUT',
    data
  });
  return res.data;
};

export const deleteUser = async (id) => {
  const res = await request(`/sys/admin/${id}`, {
    method: 'DELETE'
  });
  return res.data;
};

export const resetPassword = async (data) => {
  const res = await request('/sys/admin/reset-password', {
    method: 'PUT',
    data
  });
  return res.data;
};

// 角色管理
export const getRoleList = async (params) => {
  const res = await request('/sys/role', {
    method: 'GET',
    data: params
  });
  return res.data;
};

export const createRole = async (data) => {
  const res = await request('/sys/role', {
    method: 'POST',
    data
  });
  return res.data;
};

export const updateRole = async (data) => {
  const res = await request('/sys/role', {
    method: 'PUT',
    data
  });
  return res.data;
};

export const deleteRole = async (id) => {
  const res = await request(`/sys/role/${id}`, {
    method: 'DELETE'
  });
  return res.data;
};

export const assignPermissions = async (data) => {
  const res = await request('/sys/role/assign-permissions', {
    method: 'POST',
    data
  });
  return res.data;
};

export const assignMenus = async (data) => {
  const res = await request('/sys/role/assign-menus', {
    method: 'POST',
    data
  });
  return res.data;
};

// 权限管理
export const getPermissionList = async (params) => {
  const res = await request('/sys/permission', {
    method: 'GET',
    data: params
  });
  return res.data;
};

export const createPermission = async (data) => {
  const res = await request('/sys/permission', {
    method: 'POST',
    data
  });
  return res.data;
};

export const updatePermission = async (data) => {
  const res = await request('/sys/permission', {
    method: 'PUT',
    data
  });
  return res.data;
};

export const deletePermission = async (id) => {
  const res = await request(`/sys/permission/${id}`, {
    method: 'DELETE'
  });
  return res.data;
};

// 菜单管理
export const getMenuTree = async () => {
  const res = await request('/sys/menu/tree', {
    method: 'GET'
  });
  return res.data;
};

export const createMenu = async (data) => {
  const res = await request('/sys/menu', {
    method: 'POST',
    data
  });
  return res.data;
};

export const updateMenu = async (data) => {
  const res = await request('/sys/menu', {
    method: 'PUT',
    data
  });
  return res.data;
};

export const deleteMenu = async (id) => {
  const res = await request(`/sys/menu/${id}`, {
    method: 'DELETE'
  });
  return res.data;
};

// 系统日志
export const getLogList = async (params) => {
  const res = await request('/sys/log', {
    method: 'GET',
    data: params
  });
  return res.data;
};

export const deleteLogsBefore = async (time) => {
  const res = await request('/sys/log/clean', {
    method: 'DELETE',
    data: { time }
  });
  return res.data;
};

// 系统配置
export const getConfigList = async (params) => {
  const res = await request('/sys/config', {
    method: 'GET',
    data: params
  });
  return res.data;
};

export const createConfig = async (data) => {
  const res = await request('/sys/config', {
    method: 'POST',
    data
  });
  return res.data;
};

export const updateConfig = async (data) => {
  const res = await request('/sys/config', {
    method: 'PUT',
    data
  });
  return res.data;
};

export const deleteConfig = async (id) => {
  const res = await request(`/sys/config/${id}`, {
    method: 'DELETE'
  });
  return res.data;
};
