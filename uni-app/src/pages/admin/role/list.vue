<template>
  <view class="role-list-container">
    <view class="search-bar">
      <input 
        type="text" 
        v-model="searchForm.roleName" 
        placeholder="搜索角色名称" 
        class="search-input"
      />
      <button type="primary" class="search-btn" @click="handleSearch">搜索</button>
      <button type="default" class="add-btn" @click="handleAdd">添加角色</button>
    </view>
    
    <view class="role-table">
      <view class="table-header">
        <text class="header-item">ID</text>
        <text class="header-item">角色名称</text>
        <text class="header-item">角色编码</text>
        <text class="header-item">描述</text>
        <text class="header-item">状态</text>
        <text class="header-item">创建时间</text>
        <text class="header-item">操作</text>
      </view>
      <view 
        v-for="role in roleList" 
        :key="role.id"
        class="table-row"
      >
        <text class="row-item">{{ role.id }}</text>
        <text class="row-item">{{ role.roleName }}</text>
        <text class="row-item">{{ role.roleCode }}</text>
        <text class="row-item">{{ role.description }}</text>
        <text class="row-item" :class="role.status === 1 ? 'status-active' : 'status-inactive'">
          {{ role.status === 1 ? '启用' : '禁用' }}
        </text>
        <text class="row-item">{{ formatDate(role.createTime) }}</text>
        <view class="row-item action-buttons">
          <button type="primary" size="mini" @click="handleEdit(role)">编辑</button>
          <button type="default" size="mini" @click="handleAssignPermissions(role)">权限配置</button>
          <button type="default" size="mini" @click="handleAssignMenus(role)">菜单配置</button>
          <button 
            type="warn" 
            size="mini" 
            @click="handleDelete(role.id)"
          >
            删除
          </button>
        </view>
      </view>
    </view>
    
    <view class="pagination">
      <text class="page-info">共 {{ total }} 条记录</text>
      <view class="page-controls">
        <button 
          type="default" 
          size="mini" 
          @click="handlePageChange(1)"
          :disabled="currentPage === 1"
        >
          首页
        </button>
        <button 
          type="default" 
          size="mini" 
          @click="handlePageChange(currentPage - 1)"
          :disabled="currentPage === 1"
        >
          上一页
        </button>
        <text class="page-number">{{ currentPage }}</text>
        <button 
          type="default" 
          size="mini" 
          @click="handlePageChange(currentPage + 1)"
          :disabled="currentPage >= totalPages"
        >
          下一页
        </button>
        <button 
          type="default" 
          size="mini" 
          @click="handlePageChange(totalPages)"
          :disabled="currentPage >= totalPages"
        >
          末页
        </button>
      </view>
    </view>
  </view>
</template>

<script>
import { ref, onMounted } from 'vue';
import { getRoleList, deleteRole } from '@/api/admin';

export default {
  name: 'RoleList',
  setup() {
    const roleList = ref([]);
    const total = ref(0);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const totalPages = ref(1);
    const searchForm = ref({
      roleName: ''
    });

    const loadRoleList = async () => {
      try {
        const res = await getRoleList({
          page: currentPage.value,
          pageSize: pageSize.value,
          roleName: searchForm.value.roleName
        });
        roleList.value = res.list;
        total.value = res.total;
        totalPages.value = Math.ceil(total.value / pageSize.value);
      } catch (error) {
        uni.showToast({ title: error.message, icon: 'none' });
      }
    };

    const handleSearch = () => {
      currentPage.value = 1;
      loadRoleList();
    };

    const handleAdd = () => {
      uni.navigateTo({ url: '/pages/admin/role/add' });
    };

    const handleEdit = (role) => {
      uni.navigateTo({ 
        url: `/pages/admin/role/edit?id=${role.id}` 
      });
    };

    const handleAssignPermissions = (role) => {
      uni.navigateTo({ 
        url: `/pages/admin/role/assign-permissions?id=${role.id}&name=${role.roleName}` 
      });
    };

    const handleAssignMenus = (role) => {
      uni.navigateTo({ 
        url: `/pages/admin/role/assign-menus?id=${role.id}&name=${role.roleName}` 
      });
    };

    const handleDelete = (roleId) => {
      uni.showModal({
        title: '删除角色',
        content: '确定要删除该角色吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await deleteRole(roleId);
              uni.showToast({ title: '删除成功', icon: 'success' });
              loadRoleList();
            } catch (error) {
              uni.showToast({ title: error.message, icon: 'none' });
            }
          }
        }
      });
    };

    const handlePageChange = (page) => {
      currentPage.value = page;
      loadRoleList();
    };

    const formatDate = (dateString) => {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleString('zh-CN');
    };

    onMounted(() => {
      loadRoleList();
    });

    return {
      roleList,
      total,
      currentPage,
      totalPages,
      searchForm,
      handleSearch,
      handleAdd,
      handleEdit,
      handleAssignPermissions,
      handleAssignMenus,
      handleDelete,
      handlePageChange,
      formatDate
    };
  }
};
</script>

<style scoped>
.role-list-container {
  padding: 20rpx;
  background-color: #f0f2f5;
  min-height: 100vh;
}

.search-bar {
  display: flex;
  margin-bottom: 20rpx;
  align-items: center;
}

.search-input {
  flex: 1;
  padding: 20rpx;
  border: 1rpx solid #ddd;
  border-radius: 5rpx;
  margin-right: 10rpx;
}

.search-btn {
  margin-right: 10rpx;
}

.role-table {
  background-color: #fff;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.table-header {
  display: flex;
  background-color: #fafafa;
  padding: 20rpx;
  border-bottom: 1rpx solid #e8e8e8;
}

.header-item {
  flex: 1;
  font-weight: bold;
  color: #333;
  font-size: 26rpx;
}

.table-row {
  display: flex;
  padding: 20rpx;
  border-bottom: 1rpx solid #e8e8e8;
}

.row-item {
  flex: 1;
  font-size: 26rpx;
  color: #666;
}

.status-active {
  color: #52c41a;
}

.status-inactive {
  color: #ff4d4f;
}

.action-buttons {
  display: flex;
  gap: 10rpx;
  flex-wrap: wrap;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20rpx;
  padding: 20rpx;
  background-color: #fff;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
}

.page-info {
  font-size: 26rpx;
  color: #666;
}

.page-controls {
  display: flex;
  align-items: center;
  gap: 10rpx;
}

.page-number {
  font-size: 26rpx;
  color: #1890ff;
  font-weight: bold;
}
</style>
