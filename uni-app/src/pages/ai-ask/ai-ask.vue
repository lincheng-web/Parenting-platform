<template>
  <view class="container">
    <view class="header">
      <view class="back-btn" @tap="goBack">
        <text class="back-icon">‹</text>
      </view>
      <text class="title">问问老师</text>
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
            <text class="message-text">老师正在思考呢～</text>
          </view>
        </view>
      </scroll-view>

      <view class="input-area">
        <input class="input-box" v-model="inputText" placeholder="想问什么就写下来吧" @confirm="sendMessage" />
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
          content: '您好！我是育儿老师，有什么可以帮助您的吗？'
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
    sendMessage() {
      if (!this.inputText.trim()) {
        uni.showToast({
          title: '请先写点内容哦',
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
          url: 'http://localhost:8081/api/rag/chat',
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
                content: res.data.answer || '抱歉，老师暂时不知道怎么回答这个问题。'
              });
            } else {
              this.messages.push({
                role: 'ai',
                content: '抱歉，老师现在有点忙，请稍后再问。'
              });
            }
            this.scrollToBottom();
          },
          fail: (err) => {
            this.loading = false;
            console.error('网络错误:', err);
            this.messages.push({
              role: 'ai',
              content: '网络好像不太顺畅，请稍后再试～'
            });
            this.scrollToBottom();
          }
        });
      } catch (error) {
        this.loading = false;
        console.error('异常:', error);
        this.messages.push({
          role: 'ai',
          content: '网络好像不太顺畅，请稍后再试～'
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

.back-btn {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.back-icon {
  font-size: 28px;
  color: #5A4B38;
}

.title {
  flex: 1;
  text-align: center;
  font-size: 18px;
  font-weight: 600;
  color: #5A4B38;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.message-list {
  flex: 1;
  padding: 20px;
}

.message-item {
  margin-bottom: 16px;
  display: flex;
}

.user-message {
  justify-content: flex-end;
}

.ai-message {
  justify-content: flex-start;
}

.message-content {
  max-width: 75%;
  padding: 12px 16px;
  border-radius: 12px;
}

.user-message .message-content {
  background-color: #7BAE7F;
  color: #FFFFFF;
}

.ai-message .message-content {
  background-color: #FFFFFF;
  color: #5A4B38;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.message-text {
  font-size: 15px;
  line-height: 1.6;
}

.input-area {
  background-color: #FFFFFF;
  padding: 12px 20px;
  display: flex;
  align-items: center;
  border-top: 1px solid #E8E5E0;
}

.input-box {
  flex: 1;
  height: 44px;
  background-color: #F5F3ED;
  border: 1px solid #E8E5E0;
  border-radius: 8px;
  padding: 0 16px;
  font-size: 15px;
  color: #5A4B38;
  transition: border-color 0.2s;
}

.input-box:focus {
  border-color: #7BAE7F;
}

.send-btn {
  width: 44px;
  height: 44px;
  background-color: #7BAE7F;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 12px;
  transition: background-color 0.2s;
}

.send-btn:active {
  background-color: #6BA36E;
}

.send-icon {
  font-size: 20px;
  color: #FFFFFF;
}
</style>
