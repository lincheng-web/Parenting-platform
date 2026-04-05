<template>
  <view class="container">
    <view class="header">
      <text class="title">我的</text>
    </view>

    <view class="content">
      <view class="user-section">
        <view class="user-card">
          <view class="user-avatar">
            <text class="avatar-emoji">👨‍👩‍👧</text>
          </view>
          <view class="user-info">
            <text class="user-name">幸福的一家</text>
            <text class="user-desc">陪伴宝宝快乐成长</text>
          </view>
          <view class="user-arrow">
            <text class="arrow-icon">›</text>
          </view>
        </view>
      </view>

      <view class="menu-section">
        <view class="menu-card">
          <view class="menu-item" @tap="goToPage('baby')">
            <view class="menu-icon-box">
              <text class="menu-emoji">👶</text>
            </view>
            <text class="menu-name">宝宝信息</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>

          <view class="menu-item" @tap="goToPage('family')">
            <view class="menu-icon-box">
              <text class="menu-emoji">👨‍👩‍👧</text>
            </view>
            <text class="menu-name">家庭成员</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>

          <view class="menu-item" @tap="goToPage('reminder')">
            <view class="menu-icon-box">
              <text class="menu-emoji">🔔</text>
            </view>
            <text class="menu-name">提醒设置</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>

          <view class="menu-item" @tap="goToPage('data')">
            <view class="menu-icon-box">
              <text class="menu-emoji">📊</text>
            </view>
            <text class="menu-name">数据统计</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>
        </view>

        <view class="menu-card">
          <view class="menu-item" @tap="goToPage('help')">
            <view class="menu-icon-box">
              <text class="menu-emoji">❓</text>
            </view>
            <text class="menu-name">帮助中心</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>

          <view class="menu-item" @tap="goToPage('feedback')">
            <view class="menu-icon-box">
              <text class="menu-emoji">💬</text>
            </view>
            <text class="menu-name">意见反馈</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>

          <view class="menu-item" @tap="goToPage('about')">
            <view class="menu-icon-box">
              <text class="menu-emoji">ℹ️</text>
            </view>
            <text class="menu-name">关于我们</text>
            <view class="menu-arrow">
              <text class="arrow-icon">›</text>
            </view>
          </view>
        </view>
      </view>

      <view class="tips-section">
        <view class="tip-card">
          <text class="tip-emoji">💡</text>
          <text class="tip-text">记录宝宝的每一个成长瞬间，都是珍贵的回忆哦～</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    goToPage(page) {
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
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #FFFFFF;
  padding: 16px 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
}

.content {
  flex: 1;
  padding: 20px;
}

.user-section {
  margin-bottom: 24px;
}

.user-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.user-avatar {
  width: 64px;
  height: 64px;
  background-color: #F9F7F3;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
}

.avatar-emoji {
  font-size: 32px;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.user-desc {
  font-size: 14px;
  color: #8B7355;
}

.user-arrow {
  margin-left: 12px;
}

.arrow-icon {
  font-size: 24px;
  color: #E8E5E0;
}

.menu-section {
  margin-bottom: 24px;
}

.menu-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
  margin-bottom: 12px;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #F9F7F3;
  transition: background-color 0.2s;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-item:active {
  background-color: #F5F3ED;
}

.menu-icon-box {
  width: 40px;
  height: 40px;
  background-color: #F9F7F3;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
}

.menu-emoji {
  font-size: 20px;
}

.menu-name {
  flex: 1;
  font-size: 15px;
  color: #5A4B38;
}

.menu-arrow {
  margin-left: 12px;
}

.tips-section {
  margin-top: 20px;
}

.tip-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: flex-start;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.tip-emoji {
  font-size: 20px;
  margin-right: 12px;
  flex-shrink: 0;
}

.tip-text {
  flex: 1;
  font-size: 14px;
  color: #8B7355;
  line-height: 1.6;
}
</style>
