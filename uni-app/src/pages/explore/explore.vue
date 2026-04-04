<template>
  <view class="container">
    <view class="header">
    <view class="back-btn" @tap="goBack">
      <text class="back-icon">‹</text>
    </view>
    <text class="title">周边探索</text>
  </view>

  <view class="content">
    <view class="location-info">
      <text class="location-emoji">📍</text>
      <text class="location-text">当前位置：{{ currentLocation }}</text>
    </view>

      <view class="category-tabs">
        <view 
          v-for="(category, index) in categories" 
          :key="index" 
          class="tab-item" 
          :class="{ active: currentCategory === category.value }"
          @tap="selectCategory(category.value)"
        >
          <text class="tab-emoji">{{ category.emoji }}</text>
          <text class="tab-text">{{ category.name }}</text>
        </view>
      </view>

      <scroll-view class="poi-list" scroll-y>
        <view v-if="loading" class="loading">
          <text>正在搜索周边...</text>
        </view>
        <view v-else-if="poiList.length === 0" class="empty">
          <text>暂无相关地点</text>
        </view>
        <view v-else>
          <view v-for="(poi, index) in poiList" :key="index" class="poi-item" @tap="selectPOI(poi)">
            <view class="poi-icon">
              <text class="poi-emoji">📍</text>
            </view>
            <view class="poi-info">
              <text class="poi-name">{{ poi.name }}</text>
              <text class="poi-address">{{ poi.address }}</text>
              <text class="poi-distance">距离：{{ poi.distance }}米</text>
            </view>
            <view class="poi-action">
              <text class="poi-arrow">›</text>
            </view>
          </view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      currentLocation: '获取中...',
      currentCategory: 'park',
      categories: [
        { name: '公园', value: 'park', emoji: '🌳' },
        { name: '学校', value: 'school', emoji: '🏫' },
        { name: '医院', value: 'hospital', emoji: '🏥' },
        { name: '商场', value: 'mall', emoji: '🏬' }
      ],
      poiList: [],
      loading: false
    };
  },
  onLoad() {
    this.getCurrentLocation();
    this.searchNearbyPOI();
  },
  methods: {
    goBack() {
      uni.navigateBack();
    },
    getCurrentLocation() {
      uni.getLocation({
        type: 'gcj02',
        success: (res) => {
          const { latitude, longitude } = res;
          this.currentLocation = `${latitude.toFixed(4)}, ${longitude.toFixed(4)}`;
          this.searchNearbyPOI(latitude, longitude);
        },
        fail: () => {
          this.currentLocation = '无法获取位置';
          uni.showToast({
            title: '获取位置失败',
            icon: 'none'
          });
        }
      });
    },
    selectCategory(category) {
      this.currentCategory = category;
      this.searchNearbyPOI();
    },
    async searchNearbyPOI(lat, lng) {
      this.loading = true;
      this.poiList = [];

      try {
        const response = await uni.request({
          url: 'http://localhost:8888/api/explore/nearby',
          method: 'POST',
          data: {
            latitude: lat || 39.9042,
            longitude: lng || 116.4074,
            category: this.currentCategory,
            radius: 1000
          },
          header: {
            'Content-Type': 'application/json'
          }
        });

        this.loading = false;

        if (response[1].data && response[1].data.pois) {
          this.poiList = response[1].data.pois;
        } else {
          this.poiList = this.getMockPOI();
        }
      } catch (error) {
        this.loading = false;
        this.poiList = this.getMockPOI();
      }
    },
    getMockPOI() {
      const mockData = {
        park: [
          { name: '朝阳公园', address: '北京市朝阳区朝阳公园南路1号', distance: 500 },
          { name: '奥林匹克森林公园', address: '北京市朝阳区北五环林萃路', distance: 1200 }
        ],
        school: [
          { name: '北京市第一中学', address: '北京市东城区鼓楼东大街', distance: 800 },
          { name: '朝阳区实验小学', address: '北京市朝阳区建国路', distance: 300 }
        ],
        hospital: [
          { name: '北京儿童医院', address: '北京市西城区南礼士路56号', distance: 2000 },
          { name: '朝阳医院', address: '北京市朝阳区工人体育场南路8号', distance: 1500 }
        ],
        mall: [
          { name: '朝阳大悦城', address: '北京市朝阳区朝阳北路101号', distance: 600 },
          { name: '国贸商城', address: '北京市朝阳区建国门外大街1号', distance: 1800 }
        ]
      };
      return mockData[this.currentCategory] || [];
    },
    selectPOI(poi) {
      uni.showModal({
        title: poi.name,
        content: `地址：${poi.address}\n距离：${poi.distance}米`,
        showCancel: false
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

.location-info {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.location-emoji {
  font-size: 40rpx;
}

.location-text {
  flex: 1;
  font-size: 28rpx;
  color: #333;
  margin-left: 20rpx;
}

.category-tabs {
  background: white;
  border-radius: 20rpx;
  padding: 20rpx;
  display: flex;
  justify-content: space-around;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.tab-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20rpx;
  border-radius: 10rpx;
}

.tab-emoji {
  font-size: 36rpx;
}

.tab-item.active {
  background-color: #e6f7ff;
}

.tab-text {
  font-size: 24rpx;
  color: #666;
  margin-top: 10rpx;
}

.tab-item.active .tab-text {
  color: #1890ff;
  font-weight: bold;
}

.poi-list {
  flex: 1;
  background: white;
  border-radius: 20rpx;
  padding: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.loading,
.empty {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
  font-size: 28rpx;
}

.poi-item {
  display: flex;
  align-items: center;
  padding: 30rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}

.poi-item:last-child {
  border-bottom: none;
}

.poi-icon {
  width: 80rpx;
  height: 80rpx;
  background-color: #e6f7ff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20rpx;
}

.poi-emoji {
  font-size: 32rpx;
}

.poi-info {
  flex: 1;
}

.poi-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 10rpx;
  display: block;
}

.poi-address {
  font-size: 24rpx;
  color: #666;
  margin-bottom: 8rpx;
  display: block;
}

.poi-distance {
  font-size: 22rpx;
  color: #1890ff;
  display: block;
}

.poi-action {
  margin-left: 20rpx;
}

.poi-arrow {
  font-size: 40rpx;
  color: #999;
}
</style>
