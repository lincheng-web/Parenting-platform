<template>
  <view class="admin-container">
    <!-- 侧边栏 -->
    <view class="sidebar">
      <view class="sidebar-header">
        <text class="sidebar-title">管理后台</text>
      </view>
      <view class="sidebar-menu">
        <view 
          v-for="menu in menuList" 
          :key="menu.id"
          class="menu-item"
          @click="navigateTo(menu.path)"
        >
          <text class="menu-icon">{{ menu.icon }}</text>
          <text class="menu-text">{{ menu.title }}</text>
        </view>
      </view>
      <view class="sidebar-footer">
        <view class="user-info">
          <text class="user-name">{{ adminInfo?.realName || '管理员' }}</text>
        </view>
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </view>
    </view>
    
    <!-- 主内容区 -->
    <view class="main-content">
      <view class="content-header">
        <text class="header-title">{{ pageTitle }}</text>
      </view>
      <view class="content-body">
        <view class="welcome-card">
          <text class="welcome-text">欢迎回来，{{ adminInfo?.realName || '管理员' }}</text>
          <text class="welcome-desc">今天是 {{ formatDate(new Date()) }}</text>
        </view>
        <view class="stats-container">
          <view class="stat-card">
            <text class="stat-number">128</text>
            <text class="stat-label">用户数</text>
          </view>
          <view class="stat-card">
            <text class="stat-number">16</text>
            <text class="stat-label">角色数</text>
          </view>
          <view class="stat-card">
            <text class="stat-number">256</text>
            <text class="stat-label">权限数</text>
          </view>
          <view class="stat-card">
            <text class="stat-number">48</text>
            <text class="stat-label">菜单数</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { ref, onMounted } from 'vue';

export default {
  name: 'AdminIndex',
  setup() {
    const adminInfo = ref(JSON.parse(uni.getStorageSync('adminInfo') || '{}'));
    const pageTitle = ref('首页');
    const menuList = ref([
      { id: 1, title: '首页', path: '/pages/admin/index', icon: '🏠' },
      { id: 2, title: '用户管理', path: '/pages/admin/user/list', icon: '👥' },
      { id: 3, title: '角色管理', path: '/pages/admin/role/list', icon: '🎭' },
      { id: 4, title: '权限管理', path: '/pages/admin/permission/list', icon: '🔒' },
      { id: 5, title: '菜单管理', path: '/pages/admin/menu/list', icon: '📋' },
      { id: 6, title: '系统日志', path: '/pages/admin/log/list', icon: '📝' },
      { id: 7, title: '系统配置', path: '/pages/admin/config/list', icon: '⚙️' }
    ]);

    const navigateTo = (path) => {
      uni.navigateTo({ url: path });
    };

    const handleLogout = () => {
      uni.removeStorageSync('adminToken');
      uni.removeStorageSync('adminInfo');
      uni.navigateTo({ url: '/pages/admin/login' });
    };

    const formatDate = (date) => {
      return date.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        weekday: 'long'
      });
    };

    onMounted(() => {
      if (!uni.getStorageSync('adminToken')) {
        uni.navigateTo({ url: '/pages/admin/login' });
      }
    });

    return {
      adminInfo,
      pageTitle,
      menuList,
      navigateTo,
      handleLogout,
      formatDate
    };
  }
};
</script>

<style scoped>
.admin-container {
  display: flex;
  height: 100vh;
  background-color: #f0f2f5;
}

.sidebar {
  width: 250rpx;
  background-color: #001529;
  color: #fff;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 30rpx;
  border-bottom: 1rpx solid #1f2f3f;
}

.sidebar-title {
  font-size: 32rpx;
  font-weight: bold;
}

.sidebar-menu {
  flex: 1;
  padding: 20rpx 0;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 20rpx 30rpx;
  cursor: pointer;
  transition: background-color 0.3s;
}

.menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.menu-icon {
  margin-right: 20rpx;
  font-size: 32rpx;
}

.menu-text {
  font-size: 28rpx;
}

.sidebar-footer {
  padding: 20rpx;
  border-top: 1rpx solid #1f2f3f;
}

.user-info {
  margin-bottom: 20rpx;
}

.user-name {
  font-size: 28rpx;
}

.logout-btn {
  width: 100%;
  padding: 15rpx;
  background-color: #1890ff;
  color: #fff;
  border: none;
  border-radius: 5rpx;
  font-size: 26rpx;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.content-header {
  padding: 30rpx;
  background-color: #fff;
  border-bottom: 1rpx solid #e8e8e8;
}

.header-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.content-body {
  flex: 1;
  padding: 30rpx;
  overflow-y: auto;
}

.welcome-card {
  background-color: #fff;
  padding: 40rpx;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  margin-bottom: 30rpx;
}

.welcome-text {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 10rpx;
}

.welcome-desc {
  font-size: 28rpx;
  color: #666;
}

.stats-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20rpx;
}

.stat-card {
  background-color: #fff;
  padding: 30rpx;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  text-align: center;
}

.stat-number {
  font-size: 48rpx;
  font-weight: bold;
  color: #1890ff;
  display: block;
  margin-bottom: 10rpx;
}

.stat-label {
  font-size: 24rpx;
  color: #666;
}
</style>
