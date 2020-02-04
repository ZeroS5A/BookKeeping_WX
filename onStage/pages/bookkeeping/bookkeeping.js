// pages/bookkeeping/bookkeeping.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: "2018-12"
  },

  onLoad: function (options) {
    this.getDate()
  },

  //跳转到账单编辑页面
  openBookkeepingEdit: function () {
    wx.navigateTo({
      url: '../bookkeepingEdit/bookkeepingEdit',
    })
  },

  // ListTouch触摸开始
  ListTouchStart(e) {
    this.setData({
      ListTouchStart: e.touches[0].pageX
    })
  },

  // ListTouch计算方向
  ListTouchMove(e) {
    this.setData({
      ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > 10 ? 'right' : 'left'
    })
  },

  // ListTouch计算滚动
  ListTouchEnd(e) {
    if (this.data.ListTouchDirection == 'left') {
      this.setData({
        modalName: e.currentTarget.dataset.target
      })
    } else {
      this.setData({
        modalName: null
      })
    }
    this.setData({
      ListTouchDirection: null
    })
  },
  getDate(){
    
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var nowDate = year + "-" + month + "-" + day;
    this.setData({
      date:nowDate
    })
    
  },
  DateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },
})