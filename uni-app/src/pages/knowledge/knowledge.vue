<template>
  <view class="container">
    <view class="header">
    <view class="back-btn" @tap="goBack">
      <text class="back-icon">‹</text>
    </view>
    <text class="title">专家知识库</text>
  </view>

  <view class="content">
    <view class="info-card">
      <text class="info-emoji">📚</text>
      <text class="info-title">基于本地育儿书籍的RAG问答</text>
      <text class="info-desc">通过检索增强生成技术，为您提供专业的育儿建议</text>
    </view>

      <view class="chat-container">
        <scroll-view class="message-list" scroll-y :scroll-into-view="scrollToView">
          <view v-for="(msg, index) in messages" :key="index" :id="'msg-' + index" class="message-item" :class="msg.role === 'user' ? 'user-message' : 'ai-message'">
            <view class="message-content">
              <text class="message-text">{{ msg.content }}</text>
              <view v-if="msg.sources && msg.sources.length > 0" class="sources">
                <text class="sources-title">参考来源：</text>
                <text v-for="(source, sIndex) in msg.sources" :key="sIndex" class="source-item">{{ source }}</text>
              </view>
            </view>
          </view>
          <view v-if="loading" class="message-item ai-message">
            <view class="message-content">
              <text class="message-text">正在检索知识库...</text>
            </view>
          </view>
        </scroll-view>

        <view class="input-area">
          <input class="input-box" v-model="inputText" placeholder="请输入育儿问题..." @confirm="sendMessage" />
          <view class="send-btn" @tap="sendMessage">
            <text class="send-icon">📤</text>
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
      messages: [
        {
          role: 'ai',
          content: '您好！我是基于专家知识库的AI助手，可以为您提供专业的育儿建议。请问有什么可以帮助您的吗？'
        }
      ],
      inputText: '',
      loading: false,
      scrollToView: ''
    };
  },
  methods: {
    goBack() {
      uni.navigateBack();
    },
    async sendMessage() {
      if (!this.inputText.trim()) {
        uni.showToast({
          title: '请输入内容',
          icon: 'none'
        });
        return;
      }

      const userMessage = this.inputText;
      this.messages.push({
        role: 'user',
        content: userMessage
      });
      this.inputText = '';
      this.loading = true;

      this.scrollToBottom();

      try {
        const response = await uni.request({
          url: 'http://localhost:8888/api/rag/chat',
          method: 'POST',
          data: {
            question: userMessage,
            useRag: true
          },
          header: {
            'Content-Type': 'application/json'
          }
        });

        this.loading = false;

        if (response[1].data && response[1].data.answer) {
          this.messages.push({
            role: 'ai',
            content: response[1].data.answer,
            sources: response[1].data.sources || []
          });
        } else {
          this.messages.push({
            role: 'ai',
            content: '抱歉，知识库中暂时没有相关内容。'
          });
        }
      } catch (error) {
        this.loading = false;
        this.messages.push({
          role: 'ai',
          content: '网络错误，请稍后重试。'
        });
      }

      this.scrollToBottom();
    },
    scrollToBottom() {
      this.$nextTick(() => {
        this.scrollToView = 'msg-' + (this.messages.length - 1);
      });
    }
  }
};
</script>

<style scoped>
.container {
  height: 100vh;
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
}

.header {
  background: white;
  padding: 20rpx;
  display: flex;
  align-items: center;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.back-btn {
  width: 60rpx;
  height: 60rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.back-icon {
  font-size: 48rpx;
  color: #333;
}

.title {
  flex: 1;
  text-align: center;
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.content {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20rpx;
}

.info-card {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.info-emoji {
  font-size: 64rpx;
}

.info-title {
  font-size: 28rpx;
  font-weight: bold;
  color: #333;
  margin-top: 20rpx;
  margin-bottom: 10rpx;
}

.info-desc {
  font-size: 24rpx;
  color: #666;
  text-align: center;
  line-height: 1.5;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.message-list {
  flex: 1;
  padding: 20rpx;
}

.message-item {
  margin-bottom: 20rpx;
  display: flex;
}

.user-message {
  justify-content: flex-end;
}

.ai-message {
  justify-content: flex-start;
}

.message-content {
  max-width: 70%;
  padding: 20rpx 30rpx;
  border-radius: 20rpx;
}

.user-message .message-content {
  background-color: #faad14;
  color: white;
}

.ai-message .message-content {
  background-color: white;
  color: #333;
}

.message-text {
  font-size: 28rpx;
  line-height: 1.5;
}

.sources {
  margin-top: 20rpx;
  padding-top: 20rpx;
  border-top: 1rpx solid rgba(0, 0, 0, 0.1);
}

.sources-title {
  font-size: 24rpx;
  color: #666;
  font-weight: bold;
  display: block;
  margin-bottom: 10rpx;
}

.source-item {
  font-size: 22rpx;
  color: #999;
  display: block;
  line-height: 1.5;
}

.input-area {
  background: white;
  padding: 20rpx;
  display: flex;
  align-items: center;
  border-top: 1rpx solid #f0f0f0;
}

.input-box {
  flex: 1;
  height: 80rpx;
  background-color: #f5f5f5;
  border-radius: 40rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.send-btn {
  width: 80rpx;
  height: 80rpx;
  background-color: #faad14;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 20rpx;
}
</style>
