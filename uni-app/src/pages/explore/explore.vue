<template>
  <view class="container">
    <view class="header">
      <view class="back-btn" @tap="goBack">
        <text class="back-icon">‹</text>
      </view>
      <text class="title">去哪玩</text>
    </view>

    <view class="content">
      <view class="location-card">
        <text class="location-emoji">📍</text>
        <text class="location-text">当前位置：{{ currentLocation }}</text>
      </view>

      <view class="category-section">
        <text class="section-title">想找什么呢？</text>
        <view class="category-grid">
          <view 
            v-for="(category, index) in categories" 
            :key="index" 
            class="category-card" 
            :class="{ active: currentCategory === category.value }"
            @tap="selectCategory(category.value)"
          >
            <text class="category-emoji">{{ category.emoji }}</text>
            <text class="category-name">{{ category.name }}</text>
          </view>
        </view>
      </view>

      <view class="poi-section">
        <view v-if="loading" class="loading-state">
          <text class="loading-emoji">🔍</text>
          <text class="loading-text">正在帮您找呢～</text>
        </view>
        <view v-else-if="poiList.length === 0" class="empty-state">
          <text class="empty-emoji">🤔</text>
          <text class="empty-text">附近好像没有找到这个地方</text>
        </view>
        <view v-else class="poi-list">
          <view v-for="(poi, index) in poiList" :key="index" class="poi-card" @tap="selectPOI(poi)">
            <view class="poi-icon-box">
              <text class="poi-emoji">📍</text>
            </view>
            <view class="poi-info">
              <text class="poi-name">{{ poi.name }}</text>
              <text class="poi-address">{{ poi.address }}</text>
              <view class="poi-meta">
                <text class="poi-distance">🚶 {{ poi.distance }}米</text>
              </view>
            </view>
            <view class="poi-arrow">
              <text class="arrow-icon">›</text>
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
          this.currentLocation = '学校附近';
          this.searchNearbyPOI(latitude, longitude);
        },
        fail: () => {
          this.currentLocation = '无法获取位置';
          uni.showToast({
            title: '获取位置失败啦',
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
        uni.request({
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
          },
          success: (res) => {
            this.loading = false;
            console.log('API响应:', res);
            if (res.statusCode === 200 && res.data && res.data.pois) {
              this.poiList = res.data.pois;
            } else {
              this.poiList = this.getMockPOI();
            }
          },
          fail: (err) => {
            this.loading = false;
            console.error('网络错误:', err);
            this.poiList = this.getMockPOI();
          }
        });
      } catch (error) {
        this.loading = false;
        console.error('异常:', error);
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

.content {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.location-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
  margin-bottom: 20px;
}

.location-emoji {
  font-size: 24px;
  margin-right: 12px;
}

.location-text {
  flex: 1;
  font-size: 15px;
  color: #5A4B38;
}

.category-section {
  margin-bottom: 20px;
}

.section-title {
  font-size: 16px;
  font-weight: 500;
  color: #5A4B38;
  display: block;
  margin-bottom: 16px;
}

.category-grid {
  display: flex;
  gap: 12px;
}

.category-card {
  flex: 1;
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

.category-card:active {
  background-color: #F5F3ED;
}

.category-card.active {
  background-color: #7BAE7F;
}

.category-emoji {
  font-size: 32px;
  margin-bottom: 8px;
}

.category-name {
  font-size: 14px;
  color: #5A4B38;
}

.category-card.active .category-name {
  color: #FFFFFF;
  font-weight: 600;
}

.poi-section {
  flex: 1;
}

.loading-state {
  text-align: center;
  padding: 40px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.loading-emoji {
  font-size: 48px;
  margin-bottom: 12px;
}

.loading-text {
  font-size: 15px;
  color: #8B7355;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.empty-emoji {
  font-size: 48px;
  margin-bottom: 12px;
}

.empty-text {
  font-size: 15px;
  color: #8B7355;
}

.poi-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.poi-card {
  background-color: #FFFFFF;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 8px rgba(90, 75, 56, 0.08);
  transition: background-color 0.2s;
}

.poi-card:active {
  background-color: #F5F3ED;
}

.poi-icon-box {
  width: 48px;
  height: 48px;
  background-color: #F9F7F3;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
}

.poi-emoji {
  font-size: 24px;
}

.poi-info {
  flex: 1;
}

.poi-name {
  font-size: 16px;
  font-weight: 600;
  color: #5A4B38;
  display: block;
  margin-bottom: 4px;
}

.poi-address {
  font-size: 14px;
  color: #8B7355;
  display: block;
  margin-bottom: 8px;
}

.poi-meta {
  display: flex;
  align-items: center;
}

.poi-distance {
  font-size: 13px;
  color: #F5A65B;
}

.poi-arrow {
  margin-left: 12px;
}

.arrow-icon {
  font-size: 24px;
  color: #E8E5E0;
}
</style>
