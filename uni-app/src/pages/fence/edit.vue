<template>
  <view class="container">
    <!-- 顶部导航栏 -->
    <view class="nav-bar">
      <text class="nav-title">围栏设置</text>
      <view class="nav-buttons">
        <text class="cancel-btn" @tap="cancel">取消</text>
        <text class="save-btn" @tap="save">保存</text>
      </view>
    </view>

    <!-- 地图选点区域 -->
    <view class="map-container">
      <map
        id="map"
        ref="map"
        class="map"
        :latitude="centerLat"
        :longitude="centerLng"
        :scale="16"
        @longpress="handleMapLongPress"
      >
        <!-- 围栏多边形 -->
        <cover-view v-if="fencePoints.length > 0">
          <cover-polygon
            :points="fencePoints"
            stroke-width="2"
            stroke-color="#52c41a"
            fill-color="rgba(82, 196, 26, 0.2)"
          ></cover-polygon>
        </cover-view>
        <!-- 围栏顶点 -->
        <cover-view v-for="(point, index) in fencePoints" :key="index">
          <cover-view
            class="fence-point"
            :style="{
              left: point.left + 'px',
              top: point.top + 'px'
            }"
            @tap="removePoint(index)"
          >
            <cover-view class="point-inner"></cover-view>
          </cover-view>
        </cover-view>
      </map>
      <view class="map-tip">
        <text>长按地图添加围栏顶点，点击顶点删除</text>
      </view>
    </view>

    <!-- 表单区域 -->
    <view class="form-container">
      <!-- 围栏名称 -->
      <view class="form-item">
        <text class="label">围栏名称</text>
        <u-input
          v-model="fenceForm.name"
          placeholder="请输入围栏名称，如：学校"
          :border="false"
        ></u-input>
      </view>

      <!-- 时间设置 -->
      <view class="form-item">
        <text class="label">时间范围</text>
        <view class="time-range">
          <u-picker
            mode="time"
            v-model="fenceForm.startTime"
            @change="handleStartTimeChange"
            :border="false"
          >
            <view class="time-picker">
              <text>{{ fenceForm.startTime }}</text>
              <uni-icons type="arrowdown" size="20" color="#999"></uni-icons>
            </view>
          </u-picker>
          <text class="time-separator">至</text>
          <u-picker
            mode="time"
            v-model="fenceForm.endTime"
            @change="handleEndTimeChange"
            :border="false"
          >
            <view class="time-picker">
              <text>{{ fenceForm.endTime }}</text>
              <uni-icons type="arrowdown" size="20" color="#999"></uni-icons>
            </view>
          </u-picker>
        </view>
      </view>

      <!-- 重复星期 -->
      <view class="form-item">
        <text class="label">重复星期</text>
        <view class="week-selector">
          <view
            v-for="(week, index) in weekDays"
            :key="index"
            class="week-item"
            :class="{ active: fenceForm.weekDays.includes(week.value) }"
            @tap="toggleWeek(week.value)"
          >
            <text class="week-text">{{ week.label }}</text>
          </view>
        </view>
      </view>

      <!-- 启用开关 -->
      <view class="form-item">
        <text class="label">启用围栏</text>
        <u-switch v-model="fenceForm.enabled" active-color="#52c41a"></u-switch>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      centerLat: 39.9042,
      centerLng: 116.4074,
      fencePoints: [],
      fenceForm: {
        name: '',
        startTime: '08:00',
        endTime: '17:00',
        weekDays: ['1', '2', '3', '4', '5'],
        enabled: true
      },
      weekDays: [
        { label: '周一', value: '1' },
        { label: '周二', value: '2' },
        { label: '周三', value: '3' },
        { label: '周四', value: '4' },
        { label: '周五', value: '5' },
        { label: '周六', value: '6' },
        { label: '周日', value: '7' }
      ]
    };
  },
  methods: {
    // 处理地图长按事件，添加围栏顶点
    handleMapLongPress(e) {
      const mapContext = uni.createMapContext('map');
      mapContext.getRegion({  // 先获取地图区域信息
        success: (res) => {
          // 计算屏幕坐标
          const windowWidth = uni.getSystemInfoSync().windowWidth;
          const left = (e.detail.x / windowWidth) * 100;
          const top = (e.detail.y / uni.getSystemInfoSync().windowHeight) * 100;

          // 添加顶点
          this.fencePoints.push({
            latitude: e.detail.latitude,
            longitude: e.detail.longitude,
            left: e.detail.x,
            top: e.detail.y
          });
        }
      });
    },

    // 移除顶点
    removePoint(index) {
      this.fencePoints.splice(index, 1);
    },

    // 切换星期选择
    toggleWeek(value) {
      const index = this.fenceForm.weekDays.indexOf(value);
      if (index > -1) {
        this.fenceForm.weekDays.splice(index, 1);
      } else {
        this.fenceForm.weekDays.push(value);
      }
    },

    // 处理开始时间变化
    handleStartTimeChange(e) {
      this.fenceForm.startTime = e.value;
    },

    // 处理结束时间变化
    handleEndTimeChange(e) {
      this.fenceForm.endTime = e.value;
    },

    // 保存围栏
    save() {
      if (!this.fenceForm.name) {
        uni.showToast({ title: '请输入围栏名称', icon: 'none' });
        return;
      }

      if (this.fencePoints.length < 3) {
        uni.showToast({ title: '请至少添加3个围栏顶点', icon: 'none' });
        return;
      }

      // 构建围栏坐标字符串
      const fencePointsStr = this.fencePoints.map(point => 
        `${point.longitude},${point.latitude}`
      ).join(';');

      // 构建请求参数
      const params = {
        ...this.fenceForm,
        fencePoints: fencePointsStr,
        weekDays: this.fenceForm.weekDays.join(',')
      };

      // 调用保存接口
      uni.request({
        url: 'http://localhost:8080/api/fence/save',
        method: 'POST',
        data: params,
        success: (res) => {
          if (res.statusCode === 200) {
            uni.showToast({ title: '保存成功', icon: 'success' });
            setTimeout(() => {
              uni.navigateBack();
            }, 1000);
          } else {
            uni.showToast({ title: '保存失败', icon: 'none' });
          }
        },
        fail: () => {
          uni.showToast({ title: '网络错误', icon: 'none' });
        }
      });
    },

    // 取消
    cancel() {
      uni.navigateBack();
    }
  },
  onLoad() {
    // 获取当前位置
    uni.getLocation({
      type: 'wgs84',
      success: (res) => {
        this.centerLat = res.latitude;
        this.centerLng = res.longitude;
      }
    });
  }
};
</script>

<style scoped>
.container {
  flex: 1;
  background-color: #f5f5f5;
}

.nav-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20rpx;
  background-color: white;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
  position: sticky;
  top: 0;
  z-index: 10;
}

.nav-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.nav-buttons {
  display: flex;
  align-items: center;
}

.cancel-btn {
  font-size: 28rpx;
  color: #999;
  margin-right: 30rpx;
}

.save-btn {
  font-size: 28rpx;
  color: #52c41a;
  font-weight: bold;
}

.map-container {
  position: relative;
  height: 500rpx;
  background-color: white;
  margin: 20rpx;
  border-radius: 20rpx;
  overflow: hidden;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.map {
  width: 100%;
  height: 100%;
}

.map-tip {
  position: absolute;
  bottom: 20rpx;
  left: 0;
  right: 0;
  text-align: center;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 10rpx;
  font-size: 24rpx;
  color: #666;
}

.fence-point {
  position: absolute;
  width: 30rpx;
  height: 30rpx;
  margin-left: -15rpx;
  margin-top: -15rpx;
  z-index: 100;
}

.point-inner {
  width: 100%;
  height: 100%;
  background-color: #52c41a;
  border-radius: 50%;
  border: 2rpx solid white;
  box-shadow: 0 2rpx 5rpx rgba(0, 0, 0, 0.2);
}

.form-container {
  background-color: white;
  margin: 20rpx;
  border-radius: 20rpx;
  padding: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.form-item {
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}

.form-item:last-child {
  border-bottom: none;
}

.label {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 15rpx;
  display: block;
}

.time-range {
  display: flex;
  align-items: center;
}

.time-picker {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15rpx;
  background-color: #f5f5f5;
  border-radius: 10rpx;
}

.time-separator {
  margin: 0 20rpx;
  color: #999;
}

.week-selector {
  display: flex;
  flex-wrap: wrap;
}

.week-item {
  width: 80rpx;
  height: 60rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 10rpx;
  background-color: #f5f5f5;
  border-radius: 10rpx;
  font-size: 24rpx;
  color: #666;
}

.week-item.active {
  background-color: #52c41a;
  color: white;
}
</style>