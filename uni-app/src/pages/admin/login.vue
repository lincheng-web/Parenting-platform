<template>
  <view class="login-container">
    <view class="login-form">
      <view class="logo">
        <text class="logo-text">管理后台</text>
      </view>
      <form @submit="handleLogin">
        <view class="form-item">
          <text class="label">用户名</text>
          <input 
            type="text" 
            v-model="form.username" 
            placeholder="请输入用户名" 
            class="input"
          />
        </view>
        <view class="form-item">
          <text class="label">密码</text>
          <input 
            type="password" 
            v-model="form.password" 
            placeholder="请输入密码" 
            class="input"
          />
        </view>
        <view class="form-item checkbox">
          <checkbox v-model="form.remember" />
          <text>记住密码</text>
        </view>
        <button type="primary" form-type="submit" class="login-btn">登录</button>
      </form>
    </view>
  </view>
</template>

<script>
import { ref } from 'vue';
import { login } from '@/api/admin';

export default {
  name: 'AdminLogin',
  setup() {
    const form = ref({
      username: '',
      password: '',
      remember: false
    });

    const handleLogin = async (e) => {
      // 在uni-app中，不需要调用e.preventDefault()
      try {
        const res = await login(form.value);
        uni.setStorageSync('adminToken', res.token);
        uni.setStorageSync('adminInfo', res.admin);
        uni.navigateTo({ url: '/pages/admin/index' });
      } catch (error) {
        uni.showToast({ title: error.message, icon: 'none' });
      }
    };

    return {
      form,
      handleLogin
    };
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.login-form {
  width: 90%;
  max-width: 400px;
  background-color: #fff;
  padding: 30rpx;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);
}

.logo {
  text-align: center;
  margin-bottom: 40rpx;
}

.logo-text {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
}

.form-item {
  margin-bottom: 20rpx;
}

.label {
  display: block;
  margin-bottom: 10rpx;
  font-size: 28rpx;
  color: #666;
}

.input {
  width: 100%;
  padding: 20rpx;
  border: 1rpx solid #ddd;
  border-radius: 5rpx;
  font-size: 28rpx;
}

.checkbox {
  display: flex;
  align-items: center;
  margin-bottom: 30rpx;
}

.checkbox text {
  margin-left: 10rpx;
  font-size: 26rpx;
  color: #666;
}

.login-btn {
  width: 100%;
  padding: 20rpx;
  font-size: 30rpx;
  background-color: #409eff;
  color: #fff;
  border: none;
  border-radius: 5rpx;
}
</style>
