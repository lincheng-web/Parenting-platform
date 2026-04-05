<template>
  <view class="user-list-container">
    <view class="search-bar">
      <input 
        type="text" 
        v-model="searchForm.username" 
        placeholder="搜索用户名" 
        class="search-input"
      />
      <button type="primary" class="search-btn" @click="handleSearch">搜索</button>
      <button type="default" class="add-btn" @click="handleAdd">添加用户</button>
    </view>
    
    <view class="user-table">
      <view class="table-header">
        <text class="header-item">ID</text>
        <text class="header-item">用户名</text>
        <text class="header-item">真实姓名</text>
        <text class="header-item">手机号</text>
        <text class="header-item">邮箱</text>
        <text class="header-item">状态</text>
        <text class="header-item">创建时间</text>
        <text class="header-item">操作</text>
      </view>
      <view 
        v-for="user in userList" 
        :key="user.id"
        class="table-row"
      >
        <text class="row-item">{{ user.id }}</text>
        <text class="row-item">{{ user.username }}</text>
        <text class="row-item">{{ user.realName }}</text>
        <text class="row-item">{{ user.phone }}</text>
        <text class="row-item">{{ user.email }}</text>
        <text class="row-item" :class="user.status === 1 ? 'status-active' : 'status-inactive'">
          {{ user.status === 1 ? '启用' : '禁用' }}
        </text>
        <text class="row-item">{{ formatDate(user.createTime) }}</text>
        <view class="row-item action-buttons">
          <button type="primary" size="mini" @click="handleEdit(user)">编辑</button>
          <button type="default" size="mini" @click="handleResetPassword(user)">重置密码</button>
          <button 
            type="warn" 
            size="mini" 
            @click="handleDelete(user.id)"
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
import { getUserList, deleteUser, updateUserStatus } from '@/api/admin';

export default {
  name: 'UserList',
  setup() {
    const userList = ref([]);
    const total = ref(0);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const totalPages = ref(1);
    const searchForm = ref({
      username: ''
    });

    const loadUserList = async () => {
      try {
        const res = await getUserList({
          page: currentPage.value,
          pageSize: pageSize.value,
          username: searchForm.value.username
        });
        userList.value = res.list;
        total.value = res.total;
        totalPages.value = Math.ceil(total.value / pageSize.value);
      } catch (error) {
        uni.showToast({ title: error.message, icon: 'none' });
      }
    };

    const handleSearch = () => {
      currentPage.value = 1;
      loadUserList();
    };

    const handleAdd = () => {
      uni.navigateTo({ url: '/pages/admin/user/add' });
    };

    const handleEdit = (user) => {
      uni.navigateTo({ 
        url: `/pages/admin/user/edit?id=${user.id}` 
      });
    };

    const handleResetPassword = (user) => {
      uni.showModal({
        title: '重置密码',
        content: `确定要重置 ${user.username} 的密码吗？`,
        success: async (res) => {
          if (res.confirm) {
            // 调用重置密码接口
            uni.showToast({ title: '密码已重置', icon: 'success' });
          }
        }
      });
    };

    const handleDelete = (userId) => {
      uni.showModal({
        title: '删除用户',
        content: '确定要删除该用户吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await deleteUser(userId);
              uni.showToast({ title: '删除成功', icon: 'success' });
              loadUserList();
            } catch (error) {
              uni.showToast({ title: error.message, icon: 'none' });
            }
          }
        }
      });
    };

    const handlePageChange = (page) => {
      currentPage.value = page;
      loadUserList();
    };

    const formatDate = (dateString) => {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleString('zh-CN');
    };

    onMounted(() => {
      loadUserList();
    });

    return {
      userList,
      total,
      currentPage,
      totalPages,
      searchForm,
      handleSearch,
      handleAdd,
      handleEdit,
      handleResetPassword,
      handleDelete,
      handlePageChange,
      formatDate
    };
  }
};
</script>

<style scoped>
.user-list-container {
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

.user-table {
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
