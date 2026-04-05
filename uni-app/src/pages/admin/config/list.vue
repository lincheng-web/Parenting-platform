<template>
  <view class="config-list-container">
    <view class="search-bar">
      <input 
        type="text" 
        v-model="searchForm.configKey" 
        placeholder="搜索配置键" 
        class="search-input"
      />
      <button type="primary" class="search-btn" @click="handleSearch">搜索</button>
      <button type="default" class="add-btn" @click="handleAdd">添加配置</button>
    </view>
    
    <view class="config-table">
      <view class="table-header">
        <text class="header-item">ID</text>
        <text class="header-item">配置键</text>
        <text class="header-item">配置值</text>
        <text class="header-item">描述</text>
        <text class="header-item">状态</text>
        <text class="header-item">操作</text>
      </view>
      <view 
        v-for="config in configList" 
        :key="config.id"
        class="table-row"
      >
        <text class="row-item">{{ config.id }}</text>
        <text class="row-item">{{ config.configKey }}</text>
        <text class="row-item config-value">{{ config.configValue }}</text>
        <text class="row-item">{{ config.description }}</text>
        <text class="row-item" :class="config.status === 1 ? 'status-active' : 'status-inactive'">
          {{ config.status === 1 ? '启用' : '禁用' }}
        </text>
        <view class="row-item action-buttons">
          <button type="primary" size="mini" @click="handleEdit(config)">编辑</button>
          <button 
            type="warn" 
            size="mini" 
            @click="handleDelete(config.id)"
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
import { getConfigList, deleteConfig } from '@/api/admin';

export default {
  name: 'ConfigList',
  setup() {
    const configList = ref([]);
    const total = ref(0);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const totalPages = ref(1);
    const searchForm = ref({
      configKey: ''
    });

    const loadConfigList = async () => {
      try {
        const res = await getConfigList({
          page: currentPage.value,
          pageSize: pageSize.value,
          configKey: searchForm.value.configKey
        });
        configList.value = res.list;
        total.value = res.total;
        totalPages.value = Math.ceil(total.value / pageSize.value);
      } catch (error) {
        uni.showToast({ title: error.message, icon: 'none' });
      }
    };

    const handleSearch = () => {
      currentPage.value = 1;
      loadConfigList();
    };

    const handleAdd = () => {
      uni.navigateTo({ url: '/pages/admin/config/add' });
    };

    const handleEdit = (config) => {
      uni.navigateTo({ 
        url: `/pages/admin/config/edit?id=${config.id}` 
      });
    };

    const handleDelete = (configId) => {
      uni.showModal({
        title: '删除配置',
        content: '确定要删除该配置吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              await deleteConfig(configId);
              uni.showToast({ title: '删除成功', icon: 'success' });
              loadConfigList();
            } catch (error) {
              uni.showToast({ title: error.message, icon: 'none' });
            }
          }
        }
      });
    };

    const handlePageChange = (page) => {
      currentPage.value = page;
      loadConfigList();
    };

    onMounted(() => {
      loadConfigList();
    });

    return {
      configList,
      total,
      currentPage,
      totalPages,
      searchForm,
      handleSearch,
      handleAdd,
      handleEdit,
      handleDelete,
      handlePageChange
    };
  }
};
</script>

<style scoped>
.config-list-container {
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

.config-table {
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

.config-value {
  font-size: 24rpx;
  word-break: break-all;
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
