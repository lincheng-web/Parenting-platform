<template>
  <view class="menu-list-container">
    <view class="search-bar">
      <input 
        type="text" 
        v-model="searchForm.menuName" 
        placeholder="搜索菜单名称" 
        class="search-input"
      />
      <button type="primary" class="search-btn" @click="handleSearch">搜索</button>
      <button type="default" class="add-btn" @click="handleAdd">添加菜单</button>
    </view>
    
    <view class="menu-tree">
      <view 
        v-for="menu in menuTree" 
        :key="menu.id"
        class="menu-item"
      >
        <view class="menu-header" @click="toggleMenu(menu)">
          <text class="menu-icon">{{ menu.icon || '📁' }}</text>
          <text class="menu-name">{{ menu.menuName }}</text>
          <text class="menu-arrow">{{ menu.expanded ? '▼' : '▶' }}</text>
          <view class="menu-actions">
            <button type="primary" size="mini" @click.stop="handleEdit(menu)">编辑</button>
            <button type="warn" size="mini" @click.stop="handleDelete(menu.id)">删除</button>
          </view>
        </view>
        <view v-if="menu.children && menu.children.length > 0 && menu.expanded" class="menu-children">
          <view 
            v-for="child in menu.children" 
            :key="child.id"
            class="menu-child-item"
          >
            <text class="child-icon">{{ child.icon || '📄' }}</text>
            <text class="child-name">{{ child.menuName }}</text>
            <text class="child-path">{{ child.path }}</text>
            <view class="child-actions">
              <button type="primary" size="mini" @click="handleEdit(child)">编辑</button>
              <button type="warn" size="mini" @click="handleDelete(child.id)">删除</button>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { ref, onMounted } from 'vue';
import { getMenuTree, deleteMenu } from '@/api/admin';

export default {
  name: 'MenuList',
  setup() {
    const menuTree = ref([]);
    const searchForm = ref({
      menuName: ''
    });

    const loadMenuTree = async () => {
      try {
        const res = await getMenuTree();
        menuTree.value = res;
        // 初始化所有菜单为展开状态
        menuTree.value.forEach(menu => {
          menu.expanded = true;
          if (menu.children) {
            menu.children.forEach(child => {
              child.expanded = true;
            });
          }
        });
      } catch (error) {
        uni.showToast({ title: error.message, icon: 'none' });
      }
    };

    const handleSearch = () => {
      // 这里可以实现搜索功能
      loadMenuTree();
    };

    const handleAdd = () => {
      uni.navigateTo({ url: '/pages/admin/menu/add' });
    };

    const handleEdit = (menu) => {
      uni.navigateTo({ 
        url: `/pages/admin/menu/edit?id=${menu.id}` 
      });
    };

    const handleDelete = (menuId) => {
      uni.showModal({
        title: '删除菜单',
        content: '确定要删除该菜单吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await deleteMenu(menuId);
              uni.showToast({ title: '删除成功', icon: 'success' });
              loadMenuTree();
            } catch (error) {
              uni.showToast({ title: error.message, icon: 'none' });
            }
          }
        }
      });
    };

    const toggleMenu = (menu) => {
      menu.expanded = !menu.expanded;
    };

    onMounted(() => {
      loadMenuTree();
    });

    return {
      menuTree,
      searchForm,
      handleSearch,
      handleAdd,
      handleEdit,
      handleDelete,
      toggleMenu
    };
  }
};
</script>

<style scoped>
.menu-list-container {
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

.menu-tree {
  background-color: #fff;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
  padding: 20rpx;
}

.menu-item {
  margin-bottom: 10rpx;
}

.menu-header {
  display: flex;
  align-items: center;
  padding: 20rpx;
  background-color: #fafafa;
  border-radius: 5rpx;
  cursor: pointer;
  transition: background-color 0.3s;
}

.menu-header:hover {
  background-color: #f0f0f0;
}

.menu-icon {
  margin-right: 15rpx;
  font-size: 32rpx;
}

.menu-name {
  flex: 1;
  font-size: 28rpx;
  font-weight: bold;
  color: #333;
}

.menu-arrow {
  margin-right: 20rpx;
  font-size: 24rpx;
  color: #666;
}

.menu-actions {
  display: flex;
  gap: 10rpx;
}

.menu-children {
  margin-left: 40rpx;
  margin-top: 10rpx;
}

.menu-child-item {
  display: flex;
  align-items: center;
  padding: 15rpx;
  background-color: #f9f9f9;
  border-radius: 5rpx;
  margin-bottom: 5rpx;
}

.child-icon {
  margin-right: 15rpx;
  font-size: 28rpx;
}

.child-name {
  flex: 1;
  font-size: 26rpx;
  color: #333;
}

.child-path {
  flex: 2;
  font-size: 24rpx;
  color: #666;
  margin-right: 20rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.child-actions {
  display: flex;
  gap: 10rpx;
}
</style>
