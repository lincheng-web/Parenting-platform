<template>
  <view class="container">
    <view class="header">
    <view class="back-btn" @tap="goBack">
      <text class="back-icon">‹</text>
    </view>
    <text class="title">AI速问</text>
  </view>

    <view class="chat-container">
      <scroll-view class="message-list" scroll-y :scroll-into-view="scrollToView">
        <view v-for="(msg, index) in messages" :key="index" :id="'msg-' + index" class="message-item" :class="msg.role === 'user' ? 'user-message' : 'ai-message'">
          <view class="message-content">
            <text class="message-text">{{ msg.content }}</text>
          </view>
        </view>
        <view v-if="loading" class="message-item ai-message">
          <view class="message-content">
            <text class="message-text">正在思考中...</text>
          </view>
        </view>
      </scroll-view>

      <view class="input-area">
        <input class="input-box" v-model="inputText" placeholder="请输入您的问题..." @confirm="sendMessage" />
        <view class="send-btn" @tap="sendMessage">
          <text class="send-icon">✈️</text>
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
          content: '您好！我是AI育儿助手，有什么可以帮助您的吗？'
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
        uni.request({
          url: 'http://localhost:8888/api/rag/chat',
          method: 'POST',
          data: {
            question: userMessage,
            useRag: false
          },
          header: {
            'Content-Type': 'application/json'
          },
          success: (res) => {
            this.loading = false;
            console.log('API响应:', res);
            if (res.statusCode === 200 && res.data) {
              this.messages.push({
                role: 'ai',
                content: res.data.answer || '抱歉，我暂时无法回答这个问题。'
              });
            } else {
              this.messages.push({
                role: 'ai',
                content: '抱歉，服务暂时不可用。'
              });
            }
            this.scrollToBottom();
          },
          fail: (err) => {
            this.loading = false;
            console.error('网络错误:', err);
            this.messages.push({
              role: 'ai',
              content: '网络错误，请稍后重试。'
            });
            this.scrollToBottom();
          }
        });
      } catch (error) {
        this.loading = false;
        console.error('异常:', error);
        this.messages.push({
          role: 'ai',
          content: '网络错误，请稍后重试。'
        });
        this.scrollToBottom();
      }
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
  background-color: #52c41a;
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
  background-color: #52c41a;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 20rpx;
}
</style>
