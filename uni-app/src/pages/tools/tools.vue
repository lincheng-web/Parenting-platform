<template>
  <view class="container">
    <view class="header">
      <text class="title">亲子工具</text>
    </view>

    <view class="content">
      <view class="tools-section">
        <text class="section-title">常用工具</text>
        
        <view class="tools-grid">
          <view class="tool-card" @tap="openTool('vaccine')">
            <view class="tool-icon-box">
              <text class="tool-emoji">💉</text>
            </view>
            <text class="tool-name">疫苗提醒</text>
            <text class="tool-hint">别错过接种时间</text>
          </view>

          <view class="tool-card" @tap="openTool('food')">
            <view class="tool-icon-box">
              <text class="tool-emoji">🥣</text>
            </view>
            <text class="tool-name">辅食食谱</text>
            <text class="tool-hint">营养又好吃</text>
          </view>

          <view class="tool-card" @tap="openTool('sleep')">
            <view class="tool-icon-box">
              <text class="tool-emoji">😴</text>
            </view>
            <text class="tool-name">睡眠记录</text>
            <text class="tool-hint">了解宝宝作息</text>
          </view>

          <view class="tool-card" @tap="openTool('growth')">
            <view class="tool-icon-box">
              <text class="tool-emoji">📏</text>
            </view>
            <text class="tool-name">生长曲线</text>
            <text class="tool-hint">看看长高没</text>
          </view>

          <view class="tool-card" @tap="openTool('fence')">
            <view class="tool-icon-box">
              <text class="tool-emoji">📍</text>
            </view>
            <text class="tool-name">安全围栏</text>
            <text class="tool-hint">守护宝宝安全</text>
          </view>

          <view class="tool-card" @tap="openTool('diaper')">
            <view class="tool-icon-box">
              <text class="tool-emoji">👶</text>
            </view>
            <text class="tool-name">换尿布</text>
            <text class="tool-hint">记录换洗时间</text>
          </view>

          <view class="tool-card" @tap="openTool('temperature')">
            <view class="tool-icon-box">
              <text class="tool-emoji">🌡️</text>
            </view>
            <text class="tool-name">体温记录</text>
            <text class="tool-hint">健康有保障</text>
          </view>

          <view class="tool-card" @tap="openTool('medicine')">
            <view class="tool-icon-box">
              <text class="tool-emoji">💊</text>
            </view>
            <text class="tool-name">用药提醒</text>
            <text class="tool-hint">按时吃药</text>
          </view>
        </view>
      </view>

      <view class="tips-section">
        <text class="section-title">贴心小贴士</text>
        <view class="tip-card">
          <text class="tip-emoji">💡</text>
          <text class="tip-text">记得定期给宝宝测量身高体重，记录生长情况哦～</text>
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
    openTool(tool) {
      switch(tool) {
        case 'vaccine':
          uni.showToast({
            title: '疫苗提醒功能还在准备中',
            icon: 'none'
          });
          break;
        case 'food':
          uni.navigateTo({
            url: '/pages/knowledge/knowledge'
          });
          break;
        case 'sleep':
          uni.showToast({
            title: '睡眠记录功能还在准备中',
            icon: 'none'
          });
          break;
        case 'growth':
          uni.navigateTo({
            url: '/pages/growth/growth'
          });
          break;
        case 'fence':
          uni.navigateTo({
            url: '/pages/fence/edit'
          });
          break;
        case 'diaper':
          uni.showToast({
            title: '换尿布记录功能还在准备中',
            icon: 'none'
          });
          break;
        case 'temperature':
          uni.showToast({
            title: '体温记录功能还在准备中',
            icon: 'none'
          });
          break;
        case 'medicine':
          uni.showToast({
            title: '用药提醒功能还在准备中',
            icon: 'none'
          });
          break;
      }
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

.tools-section {
  margin-bottom: 24px;
}

.section-title {
  font-size: 16px;
  font-weight: 500;
  color: #5A4B38;
  display: block;
  margin-bottom: 16px;
}

.tools-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.tool-card {
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

.tool-card:active {
  background-color: #F5F3ED;
}

.tool-icon-box {
  width: 56px;
  height: 56px;
  background-color: #F9F7F3;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
}

.tool-emoji {
  font-size: 28px;
}

.tool-name {
  font-size: 15px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.tool-hint {
  font-size: 12px;
  color: #8B7355;
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
