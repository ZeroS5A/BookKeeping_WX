// pages/bookkeeping/bookkeeping.js
var  app  =  getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    dateShow:'今日',
    date:'',
    bkData:{
      bookkeepingAllList: [],
      totalIncome: 0,
      totalExpend: 0,
      sumIncomeMoney: 0.00,
      sumExpendMoney: 0.00
    }
  },

  onLoad: function (options) {
    this.getDate()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that = this;
    setTimeout(function () {
      if (app.globalData) {
        app.getRequest("bookkeeping/listAll", {})
          .then((res) => {
            console.log(res)
            if (res.data.code != 200) {
              console.log("无法获取数据")
            }
            else {
              that.setData({
                bkData: {
                  bookkeepingAllList: res.data.data.bookkeepingAllList,
                  totalIncome: res.data.data.totalIncome,
                  totalExpend: res.data.data.totalExpend,
                  sumIncomeMoney: res.data.data.sumIncomeMoney,
                  sumExpendMoney: res.data.data.sumExpendMoney
                }
              })
            }
          })
      } else {
        console.log("用户未登录！")
      }
    }, 1000) 
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (app.globalData) {
      app.getRequest("bookkeeping/listAll", {})
        .then((res) => {
          console.log(res)
          if (res.data.code != 200) {
            console.log("无法获取数据")
          }
          else {
            this.setData({
              bkData: {
                bookkeepingList: res.data.data.bookkeepingList,
                totalIncome: res.data.data.totalIncome,
                totalExpend: res.data.data.totalExpend,
                sumIncomeMoney: res.data.data.sumIncomeMoney,
                sumExpendMoney: res.data.data.sumExpendMoney
              }
            })
          }
        })
    } else {
      console.log("用户未登录！")
    }
  },

  //跳转到账单编辑页面
  openBookkeepingEdit: function () {
    wx.navigateTo({
      url: '../bookkeepingEdit/bookkeepingEdit',
    })
  },

  //跳转到账单编辑页面(带数据)
  openBookkeepingEditWithData: function (e) {
    wx.navigateTo({
      url: '../bookkeepingEdit/bookkeepingEdit',
      success: function(res) {
        // 通过eventChannel向被打开页面传送数据
        res.eventChannel.emit('acceptDataFromOpenerPage', { data: e.currentTarget.dataset.value })
      }
    })
  },

  deleteBookkeeping(event) {
    if (app.globalData) {
      app.getRequest("bookkeeping/deleteBookkeeping", this.data.bkData.bookkeepingList[event.currentTarget.dataset.value])
        .then((res) => {
          if (res.data.code != 200) {
            console.log("删除失败")
          }
          else {
            // console.log(this.data.bkData.bookkeepingList)
            // this.data.bkData.bookkeepingList.splice([event.currentTarget.dataset.value], 1);//删是删了数组里的数据，但是不会刷新列表，没鬼用
            // console.log(this.data.bkData.bookkeepingList)
            console.log("删除成功")
          }
        })
    } else {
      console.log("用户未登录！")
    }
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
      ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > -70 ? 'right' : 'left'
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
      date: e.detail.value,
      dateShow:e.detail.value
    })
  },
})