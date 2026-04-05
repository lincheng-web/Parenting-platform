<template>
  <view class="container">
    <view class="header">
      <text class="title">成长记录</text>
      <view class="add-btn" @tap="addRecord">
        <text class="add-icon">+</text>
      </view>
    </view>

    <view class="content">
      <view class="baby-summary">
        <view class="summary-card">
          <text class="summary-emoji">👶</text>
          <view class="summary-info">
            <text class="baby-name">小明</text>
            <text class="baby-age">3岁2个月</text>
          </view>
        </view>
      </view>

      <view class="record-section">
        <text class="section-title">最近记录</text>
        
        <view class="record-list">
          <view v-for="(record, index) in records" :key="index" class="record-card">
            <view class="record-icon-box">
              <text class="record-emoji">{{ record.emoji }}</text>
            </view>
            <view class="record-info">
              <text class="record-title">{{ record.title }}</text>
              <text class="record-date">{{ record.date }}</text>
              <text class="record-desc">{{ record.desc }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      records: [
        {
          emoji: '🍼',
          title: '喝了200ml奶',
          date: '今天 09:30',
          desc: '宝宝胃口很好，喝得干干净净'
        },
        {
          emoji: '😴',
          title: '午睡2小时',
          date: '今天 14:00',
          desc: '睡得很香，醒来心情不错'
        },
        {
          emoji: '🎨',
          title: '画了一幅画',
          date: '昨天 16:30',
          desc: '画了太阳和小花，颜色搭配得很好'
        },
        {
          emoji: '📖',
          title: '读绘本《好饿的毛毛虫》',
          date: '昨天 20:00',
          desc: '能跟着说出故事里的动物名字'
        },
        {
          emoji: '🚶',
          title: '学会了新单词',
          date: '3天前',
          desc: '会说"谢谢"和"对不起"了'
        }
      ]
    };
  },
  methods: {
    addRecord() {
      uni.showModal({
        title: '添加记录',
        content: '选择要记录的内容',
        confirmText: '知道了',
        showCancel: false
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
  justify-content: space-between;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
}

.add-btn {
  width: 40px;
  height: 40px;
  background-color: #7BAE7F;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.add-icon {
  font-size: 24px;
  color: #FFFFFF;
  font-weight: bold;
}

.content {
  flex: 1;
  padding: 20px;
}

.baby-summary {
  margin-bottom: 20px;
}

.summary-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.summary-emoji {
  font-size: 48px;
  margin-right: 16px;
}

.summary-info {
  flex: 1;
}

.baby-name {
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.baby-age {
  font-size: 14px;
  color: #8B7355;
}

.record-section {
  flex: 1;
}

.section-title {
  font-size: 16px;
  font-weight: 500;
  color: #5A4B38;
  display: block;
  margin-bottom: 16px;
}

.record-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.record-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: flex-start;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.record-icon-box {
  width: 48px;
  height: 48px;
  background-color: #F9F7F3;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  flex-shrink: 0;
}

.record-emoji {
  font-size: 24px;
}

.record-info {
  flex: 1;
}

.record-title {
  font-size: 16px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.record-date {
  font-size: 13px;
  color: #F5A65B;
  display: block;
  margin-bottom: 8px;
}

.record-desc {
  font-size: 14px;
  color: #8B7355;
  line-height: 1.6;
}
</style>
