<template>
  <view class="container">
    <view class="header">
      <view class="back-btn" @tap="goBack">
        <text class="back-icon">‹</text>
      </view>
      <text class="title">安全围栏</text>
      <view class="save-btn" @tap="save">
        <text class="save-text">保存</text>
      </view>
    </view>

    <view class="content">
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
          <cover-view v-if="fencePoints.length > 0">
            <cover-polygon
              :points="fencePoints"
              stroke-width="2"
              stroke-color="#7BAE7F"
              fill-color="rgba(123, 174, 127, 0.2)"
            ></cover-polygon>
          </cover-view>
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
          <text class="tip-emoji">👆</text>
          <text class="tip-text">长按地图添加围栏点，点击圆点可以删除</text>
        </view>
      </view>

      <view class="form-container">
        <view class="form-item">
          <text class="label">围栏名称</text>
          <input
            class="input-box"
            v-model="fenceForm.name"
            placeholder="比如：学校、幼儿园"
          />
        </view>

        <view class="form-item">
          <text class="label">时间范围</text>
          <view class="time-range">
            <view class="time-picker" @tap="showTimePicker('start')">
              <text class="time-text">{{ fenceForm.startTime }}</text>
              <text class="picker-icon">▼</text>
            </view>
            <text class="time-separator">至</text>
            <view class="time-picker" @tap="showTimePicker('end')">
              <text class="time-text">{{ fenceForm.endTime }}</text>
              <text class="picker-icon">▼</text>
            </view>
          </view>
        </view>

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

        <view class="form-item">
          <text class="label">启用围栏</text>
          <view class="switch-box" :class="{ active: fenceForm.enabled }" @tap="toggleSwitch">
            <view class="switch-dot"></view>
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
    goBack() {
      uni.navigateBack();
    },
    handleMapLongPress(e) {
      const mapContext = uni.createMapContext('map');
      mapContext.getRegion({
        success: (res) => {
          const windowWidth = uni.getSystemInfoSync().windowWidth;
          const left = (e.detail.x / windowWidth) * 100;
          const top = (e.detail.y / uni.getSystemInfoSync().windowHeight) * 100;

          this.fencePoints.push({
            latitude: e.detail.latitude,
            longitude: e.detail.longitude,
            left: e.detail.x,
            top: e.detail.y
          });
        }
      });
    },
    removePoint(index) {
      this.fencePoints.splice(index, 1);
    },
    toggleWeek(value) {
      const index = this.fenceForm.weekDays.indexOf(value);
      if (index > -1) {
        this.fenceForm.weekDays.splice(index, 1);
      } else {
        this.fenceForm.weekDays.push(value);
      }
    },
    showTimePicker(type) {
      uni.showToast({
        title: '时间选择功能还在准备中',
        icon: 'none'
      });
    },
    toggleSwitch() {
      this.fenceForm.enabled = !this.fenceForm.enabled;
    },
    save() {
      if (!this.fenceForm.name) {
        uni.showToast({
          title: '请输入围栏名称',
          icon: 'none'
        });
        return;
      }

      if (this.fencePoints.length < 3) {
        uni.showToast({
          title: '请至少添加3个围栏点',
          icon: 'none'
        });
        return;
      }

      const fencePointsStr = this.fencePoints.map(point => 
        `${point.longitude},${point.latitude}`
      ).join(';');

      const params = {
        ...this.fenceForm,
        fencePoints: fencePointsStr,
        weekDays: this.fenceForm.weekDays.join(',')
      };

      uni.request({
        url: 'http://localhost:8080/api/fence/save',
        method: 'POST',
        data: params,
        success: (res) => {
          if (res.statusCode === 200) {
            uni.showToast({
              title: '保存成功啦',
              icon: 'none'
            });
            setTimeout(() => {
              uni.navigateBack();
            }, 1000);
          } else {
            uni.showToast({
              title: '保存失败，请重试',
              icon: 'none'
            });
          }
        },
        fail: () => {
          uni.showToast({
            title: '网络好像不太顺畅',
            icon: 'none'
          });
        }
      });
    }
  },
  onLoad() {
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

.save-btn {
  padding: 8px 16px;
  background-color: #7BAE7F;
  border-radius: 8px;
}

.save-text {
  font-size: 14px;
  color: #FFFFFF;
  font-weight: 600;
}

.content {
  flex: 1;
  padding: 20px;
}

.map-container {
  position: relative;
  height: 300px;
  background-color: #FFFFFF;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
  margin-bottom: 20px;
}

.map {
  width: 100%;
  height: 100%;
}

.map-tip {
  position: absolute;
  bottom: 16px;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 12px 16px;
  border-radius: 8px;
  margin: 0 16px;
}

.tip-emoji {
  font-size: 20px;
  margin-right: 8px;
}

.tip-text {
  font-size: 13px;
  color: #5A4B38;
}

.fence-point {
  position: absolute;
  width: 30px;
  height: 30px;
  margin-left: -15px;
  margin-top: -15px;
  z-index: 100;
}

.point-inner {
  width: 100%;
  height: 100%;
  background-color: #7BAE7F;
  border-radius: 50%;
  border: 2px solid #FFFFFF;
  box-shadow: 0 2px 5px rgba(90, 75, 56, 0.2);
}

.form-container {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
}

.form-item {
  padding: 16px 0;
  border-bottom: 1px solid #F9F7F3;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.form-item:last-child {
  border-bottom: none;
}

.label {
  font-size: 15px;
  color: #5A4B38;
  flex-shrink: 0;
  width: 80px;
}

.input-box {
  flex: 1;
  font-size: 15px;
  color: #5A4B38;
  text-align: right;
}

.time-range {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.time-picker {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  background-color: #F9F7F3;
  border-radius: 8px;
}

.time-text {
  font-size: 14px;
  color: #5A4B38;
  margin-right: 8px;
}

.picker-icon {
  font-size: 12px;
  color: #8B7355;
}

.time-separator {
  margin: 0 12px;
  color: #8B7355;
  font-size: 14px;
}

.week-selector {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.week-item {
  width: 40px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #F9F7F3;
  border-radius: 8px;
  font-size: 13px;
  color: #8B7355;
}

.week-item.active {
  background-color: #7BAE7F;
  color: #FFFFFF;
}

.switch-box {
  width: 48px;
  height: 28px;
  background-color: #E8E5E0;
  border-radius: 14px;
  position: relative;
  transition: background-color 0.3s;
}

.switch-box.active {
  background-color: #7BAE7F;
}

.switch-dot {
  width: 24px;
  height: 24px;
  background-color: #FFFFFF;
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 2px;
  transition: left 0.3s;
  box-shadow: 0 2px 4px rgba(90, 75, 56, 0.15);
}

.switch-box.active .switch-dot {
  left: 22px;
}
</style>