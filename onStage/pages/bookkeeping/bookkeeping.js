// pages/bookkeeping/bookkeeping.js
var  app  =  getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    month:'',
    date:'',
    bkData:{
      bookkeepingAllList: [],
      totalIncome: 0,
      totalExpend: 0,
      sumIncomeMoney: 0.00,
      sumExpendMoney: 0.00
    },
    bkDateStr:{
      bkDateStr:''
    },
    appData:app
  },

  onLoad: function (options) {
    this.getDate()
    var that = this;
    setTimeout(function () {
      if (app.globalData.hasUserInfo) {
        app.getRequest("bookkeeping/listAll", that.data.bkDateStr)
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
    }, 2000)     
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log("首页显示")
    this.getBkData(this.data.bkDateStr)
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

  //删除
  deleteBookkeeping(event) {
    if (app.globalData.hasUserInfo) {
      app.getRequest("bookkeeping/deleteBookkeeping", event.currentTarget.dataset.value)
        .then((res) => {
          if (res.data.code != 200) {
            console.log("删除失败")
          }
          else {
            this.getBkData(this.data.bkDateStr)
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
  //获取日期
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
    var nowDate = year + "-" + month;
    this.setData({
      month:date.getMonth() + 1,
      date:nowDate,
      bkDateStr: {
        bkDateStr:nowDate,
      }      
    })
    
  },
  //选择月份触发
  DateChange(e) {
    var month
    if(e.detail.value.slice(5)>10){
      month=e.detail.value.slice(5)
    }else{
      month=e.detail.value.slice(6)
    }
    this.setData({
      month:month,
      bkDateStr: {
        bkDateStr:e.detail.value,
      }
    })
    this.getBkData(this.data.bkDateStr)
  },

  //获取数据封装
  getBkData(postData){
    if (app.globalData.hasUserInfo) {
      app.getRequest("bookkeeping/listAll", postData)
      .then((res) => {
        if (res.data.code != 200) {
          console.log("无法获取数据")
        }
        else {
          this.setData({
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
  }
})