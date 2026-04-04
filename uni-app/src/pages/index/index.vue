<template>
  <view class="container">
    <!-- 顶部状态栏 -->
    <view class="status-bar">
      <view class="child-status">
        <image class="child-avatar" :src="child.avatarUrl || '/static/avatar.png'"></image>
        <view class="child-info">
          <text class="child-name">{{ child.name }}</text>
          <text class="child-location">{{ child.location || '位置未知' }}</text>
        </view>
        <view class="device-status">
          <text class="battery">电量: {{ device.battery }}%</text>
        </view>
      </view>
    </view>

    <!-- 中部功能卡片 -->
    <view class="function-cards">
      <!-- AI速问 -->
      <view class="card" @tap="goToAIAsk">
        <view class="card-icon ai-icon">
          <text class="icon-text">💬</text>
        </view>
        <view class="card-content">
          <text class="card-title">AI速问</text>
          <text class="card-desc">基础层：DeepSeek API 直接问答</text>
        </view>
        <text class="arrow">›</text>
      </view>

      <!-- 专家知识库 -->
      <view class="card" @tap="goToKnowledge">
        <view class="card-icon knowledge-icon">
          <text class="icon-text">📚</text>
        </view>
        <view class="card-content">
          <text class="card-title">专家知识库</text>
          <text class="card-desc">增强层：基于本地 PDF 育儿书籍的 RAG 问答</text>
        </view>
        <text class="arrow">›</text>
      </view>

      <!-- 周边探索 -->
      <view class="card" @tap="goToExplore">
        <view class="card-icon explore-icon">
          <text class="icon-text">📍</text>
        </view>
        <view class="card-content">
          <text class="card-title">周边探索</text>
          <text class="card-desc">硬件层：百度地图 POI 推荐活动</text>
        </view>
        <text class="arrow">›</text>
      </view>
    </view>

    <!-- 底部告警记录 -->
    <view class="alert-section">
      <view class="section-title">
        <text>最近告警</text>
        <text class="more" @tap="goToAlerts">查看全部</text>
      </view>
      <view class="alert-list">
        <view v-for="(alert, index) in alerts" :key="index" class="alert-item">
          <view class="alert-icon">
            <text class="alert-emoji">⚠️</text>
          </view>
          <view class="alert-content">
            <text class="alert-title">{{ alert.title }}</text>
            <text class="alert-time">{{ alert.time }}</text>
          </view>
        </view>
        <view v-if="alerts.length === 0" class="empty-alert">
          <text>暂无告警记录</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      child: {
        name: '小明',
        avatarUrl: '',
        location: '学校附近'
      },
      device: {
        battery: 85
      },
      alerts: [
        {
          title: '小明在规定时间不在学校',
          time: '2026-04-03 15:30'
        },
        {
          title: '小明离开家范围',
          time: '2026-04-03 08:15'
        }
      ]
    };
  },
  onLoad() {
    // 初始化WebSocket连接
    this.initWebSocket();
  },
  methods: {
    goToAIAsk() {
      uni.navigateTo({
        url: '/pages/ai-ask/ai-ask'
      });
    },
    goToKnowledge() {
      uni.navigateTo({
        url: '/pages/knowledge/knowledge'
      });
    },
    goToExplore() {
      uni.navigateTo({
        url: '/pages/explore/explore'
      });
    },
    goToAlerts() {
      uni.navigateTo({
        url: '/pages/alerts/alerts'
      });
    },
    initWebSocket() {
      // WebSocket连接逻辑
      const token = uni.getStorageSync('token');
      if (token) {
        // 实际项目中应该使用真实的WebSocket地址
        const wsUrl = `ws://localhost:8080/ws/connect/${token}`;
        // 这里应该实现WebSocket连接逻辑
      }
    }
  }
};
</script>

<style scoped>
.container {
  flex: 1;
  background-color: #f5f5f5;
}

.status-bar {
  background: linear-gradient(135deg, #52c41a, #73d13d);
  padding: 20rpx;
  border-radius: 0 0 20rpx 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);
}

.child-status {
  display: flex;
  align-items: center;
  color: white;
}

.child-avatar {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  margin-right: 20rpx;
  border: 2rpx solid white;
}

.child-info {
  flex: 1;
}

.child-name {
  font-size: 32rpx;
  font-weight: bold;
  margin-bottom: 8rpx;
  display: block;
}

.child-location {
  font-size: 24rpx;
  opacity: 0.9;
}

.device-status {
  text-align: right;
}

.battery {
  font-size: 24rpx;
  opacity: 0.9;
}

.function-cards {
  padding: 20rpx;
  margin-top: 20rpx;
}

.card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
  display: flex;
  align-items: center;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
  transition: transform 0.3s;
}

.card:active {
  transform: scale(0.98);
}

.card-icon {
  width: 80rpx;
  height: 80rpx;
  border-radius: 20rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20rpx;
}

.icon-text {
  font-size: 40rpx;
}

.arrow {
  font-size: 40rpx;
  color: #999;
}

.ai-icon {
  background-color: #e6f7ee;
}

.knowledge-icon {
  background-color: #fff7e6;
}

.explore-icon {
  background-color: #e6f7ff;
}

.card-content {
  flex: 1;
}

.card-title {
  font-size: 32rpx;
  font-weight: bold;
  margin-bottom: 8rpx;
  display: block;
}

.card-desc {
  font-size: 24rpx;
  color: #666;
  line-height: 1.4;
}

.alert-section {
  padding: 20rpx;
  margin-top: 20rpx;
}

.section-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.section-title text:first-child {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.more {
  font-size: 24rpx;
  color: #52c41a;
}

.alert-list {
  background: white;
  border-radius: 20rpx;
  padding: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.alert-item {
  display: flex;
  align-items: center;
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}

.alert-item:last-child {
  border-bottom: none;
}

.alert-icon {
  margin-right: 20rpx;
}

.alert-emoji {
  font-size: 32rpx;
}

.alert-content {
  flex: 1;
}

.alert-title {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 8rpx;
  display: block;
}

.alert-time {
  font-size: 22rpx;
  color: #999;
}

.empty-alert {
  text-align: center;
  padding: 40rpx 0;
  color: #999;
  font-size: 24rpx;
}
</style>