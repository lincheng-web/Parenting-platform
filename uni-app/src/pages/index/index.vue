<template>
  <view class="container">
    <!-- 顶部宝宝状态 -->
    <view class="baby-status">
      <view class="baby-avatar">
        <text class="avatar-emoji">👶</text>
      </view>
      <view class="baby-info">
        <text class="baby-name">小明</text>
        <text class="baby-location">📍 学校附近</text>
      </view>
      <view class="device-info">
        <text class="battery-text">🔋 电量充足</text>
      </view>
    </view>

    <!-- 功能入口 -->
    <view class="function-section">
      <text class="section-title">今天想做什么呢？</text>
      
      <view class="function-grid">
        <view class="function-card" @tap="goToAIAsk">
          <view class="card-icon-box">
            <text class="card-emoji">💬</text>
          </view>
          <text class="card-name">问问老师</text>
          <text class="card-hint">育儿问题随时问</text>
        </view>

        <view class="function-card" @tap="goToKnowledge">
          <view class="card-icon-box">
            <text class="card-emoji">📚</text>
          </view>
          <text class="card-name">育儿知识</text>
          <text class="card-hint">专家经验学起来</text>
        </view>

        <view class="function-card" @tap="goToExplore">
          <view class="card-icon-box">
            <text class="card-emoji">🌳</text>
          </view>
          <text class="card-name">去哪玩</text>
          <text class="card-hint">周边好地方找找</text>
        </view>
      </view>
    </view>

    <!-- 最近提醒 -->
    <view class="alert-section">
      <view class="section-header">
        <text class="section-title">最近提醒</text>
        <text class="view-all" @tap="goToAlerts">看全部</text>
      </view>
      
      <view class="alert-list">
        <view v-for="(alert, index) in alerts" :key="index" class="alert-card">
          <view class="alert-icon">
            <text class="alert-emoji">⚠️</text>
          </view>
          <view class="alert-info">
            <text class="alert-text">{{ alert.title }}</text>
            <text class="alert-time">{{ alert.time }}</text>
          </view>
        </view>
        
        <view v-if="alerts.length === 0" class="empty-state">
          <text class="empty-emoji">🎉</text>
          <text class="empty-text">宝宝今天表现很好哦</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      alerts: [
        {
          title: '小明离开学校范围',
          time: '今天 15:30'
        },
        {
          title: '电量低于20%',
          time: '今天 08:15'
        }
      ]
    };
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
      uni.showToast({
        title: '这个功能还在准备中',
        icon: 'none'
      });
    }
  }
};
</script>

<style scoped>
.container {
  min-height: 100vh;
  background-color: #F9F7F3;
  padding: 20px;
}

/* 宝宝状态栏 */
.baby-status {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
  margin-bottom: 24px;
}

.baby-avatar {
  width: 56px;
  height: 56px;
  background-color: #7BAE7F;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
}

.avatar-emoji {
  font-size: 32px;
}

.baby-info {
  flex: 1;
}

.baby-name {
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.baby-location {
  font-size: 14px;
  color: #8B7355;
}

.device-info {
  text-align: right;
}

.battery-text {
  font-size: 14px;
  color: #7BAE7F;
}

/* 功能区域 */
.function-section {
  margin-bottom: 24px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 16px;
}

.function-grid {
  display: flex;
  gap: 12px;
}

.function-card {
  flex: 1;
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 20px 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
  transition: background-color 0.2s;
}

.function-card:active {
  background-color: #F5F3ED;
}

.card-icon-box {
  width: 48px;
  height: 48px;
  background-color: #F9F7F3;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
}

.card-emoji {
  font-size: 28px;
}

.card-name {
  font-size: 16px;
  font-weight: 500;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.card-hint {
  font-size: 12px;
  color: #8B7355;
}

/* 提醒区域 */
.alert-section {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #E8E5E0;
}

.section-header .section-title {
  margin-bottom: 0;
}

.view-all {
  font-size: 14px;
  color: #7BAE7F;
}

.alert-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.alert-card {
  display: flex;
  align-items: center;
  padding: 12px;
  background-color: #F9F7F3;
  border-radius: 8px;
}

.alert-icon {
  margin-right: 12px;
}

.alert-emoji {
  font-size: 24px;
}

.alert-info {
  flex: 1;
}

.alert-text {
  font-size: 15px;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.alert-time {
  font-size: 12px;
  color: #8B7355;
}

.empty-state {
  text-align: center;
  padding: 32px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.empty-emoji {
  font-size: 48px;
  margin-bottom: 12px;
}

.empty-text {
  font-size: 15px;
  color: #8B7355;
}
</style>
