<template>
  <view class="log-list-container">
    <view class="search-bar">
      <input 
        type="text" 
        v-model="searchForm.operation" 
        placeholder="搜索操作内容" 
        class="search-input"
      />
      <button type="primary" class="search-btn" @click="handleSearch">搜索</button>
      <button type="default" class="clean-btn" @click="handleClean">清理日志</button>
    </view>
    
    <view class="log-table">
      <view class="table-header">
        <text class="header-item">ID</text>
        <text class="header-item">操作人</text>
        <text class="header-item">操作内容</text>
        <text class="header-item">IP地址</text>
        <text class="header-item">操作时间</text>
        <text class="header-item">操作结果</text>
        <text class="header-item">错误信息</text>
      </view>
      <view 
        v-for="log in logList" 
        :key="log.id"
        class="table-row"
      >
        <text class="row-item">{{ log.id }}</text>
        <text class="row-item">{{ log.adminName }}</text>
        <text class="row-item">{{ log.operation }}</text>
        <text class="row-item">{{ log.ip }}</text>
        <text class="row-item">{{ formatDate(log.createTime) }}</text>
        <text class="row-item" :class="log.result === 1 ? 'result-success' : 'result-error'">
          {{ log.result === 1 ? '成功' : '失败' }}
        </text>
        <text class="row-item error-msg">{{ log.errorMsg || '-' }}</text>
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
import { getLogList, deleteLogsBefore } from '@/api/admin';

export default {
  name: 'LogList',
  setup() {
    const logList = ref([]);
    const total = ref(0);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const totalPages = ref(1);
    const searchForm = ref({
      operation: ''
    });

    const loadLogList = async () => {
      try {
        const res = await getLogList({
          page: currentPage.value,
          pageSize: pageSize.value,
          operation: searchForm.value.operation
        });
        logList.value = res.list;
        total.value = res.total;
        totalPages.value = Math.ceil(total.value / pageSize.value);
      } catch (error) {
        uni.showToast({ title: error.message, icon: 'none' });
      }
    };

    const handleSearch = () => {
      currentPage.value = 1;
      loadLogList();
    };

    const handleClean = () => {
      uni.showModal({
        title: '清理日志',
        content: '确定要清理30天前的日志吗？',
        success: async (res) => {
          if (res.confirm) {
            try {
              const thirtyDaysAgo = new Date();
              thirtyDaysAgo.setDate(thirtyDaysAgo.getDate() - 30);
              await deleteLogsBefore(thirtyDaysAgo);
              uni.showToast({ title: '日志清理成功', icon: 'success' });
              loadLogList();
            } catch (error) {
              uni.showToast({ title: error.message, icon: 'none' });
            }
          }
        }
      });
    };

    const handlePageChange = (page) => {
      currentPage.value = page;
      loadLogList();
    };

    const formatDate = (dateString) => {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleString('zh-CN');
    };

    onMounted(() => {
      loadLogList();
    });

    return {
      logList,
      total,
      currentPage,
      totalPages,
      searchForm,
      handleSearch,
      handleClean,
      handlePageChange,
      formatDate
    };
  }
};
</script>

<style scoped>
.log-list-container {
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

.log-table {
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

.result-success {
  color: #52c41a;
}

.result-error {
  color: #ff4d4f;
}

.error-msg {
  color: #ff4d4f;
  font-size: 24rpx;
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
