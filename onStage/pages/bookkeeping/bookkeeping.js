// pages/bookkeeping/bookkeeping.js
var  app  =  getApp();
var feedbackApi = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    month:'',
    day:'',
    date:'',
    bkData:{
      bookkeepingAllList: [],
      totalIncome: 0,
      totalExpend: 0,
      sumIncomeMoney: 0.00,
      sumExpendMoney: 0.00
    },
    bkDataPost:{
      bkDateStr:'',
      remarkText:''
    },
    appData:app,
    tryTimes:0,
    feedback:feedbackApi
  },
  
  

  onLoad: function (options) {
    this.getDate()
    //获取首页信息（循环多次检查）
    this.listALL() 

    var that=this
    wx.showLoading({
      title: '正在获取数据',
    })
    setTimeout(function () {
      if (that.data.tryTimes<15&&app.globalData.hasUserInfo) {
        console.log("access")
      } else {
        console.log("用户未登录！")
        wx.hideLoading()
        wx.showModal({
          title: '请先登录~',
          showCancel: false,
          success (res) {
            if (res.confirm) {
              console.log('用户点击确定')
            }
          }
        })
      }
    }, 3000)
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log("首页显示")
    //this.getDate()
    this.getBkData()
  },

  //跳转到账单编辑页面
  openBookkeepingEdit: function () {
    if(!app.globalData.hasUserInfo){
      wx.showModal({
        title: '你还未登录',
        showCancel: false,
        success (res) {
          if (res.confirm) {
            console.log('用户点击确定')
          }
        }
      })
    }else{
      wx.navigateTo({
        url: '../bookkeepingEdit/bookkeepingEdit',
      })      
    }

  },

  //跳转到账单编辑页面(带数据)
  openBookkeepingEditWithData: function (e) {
    wx.navigateTo({
      url: '../bookkeepingEdit/bookkeepingEdit',
      success: function(res) {
        // 通过eventChannel向被打开页面传送数据
        res.eventChannel.emit('acceptDataFromOpenerPage', { data: e.currentTarget.dataset.value,})
      }
    })
  },

  //删除
  deleteBookkeeping(event) {
    var that=this
    wx.showModal({
      title: '确认删除？',
      confirmColor:'#FF0000',
      success (res) {
        if (res.confirm) {
          console.log('确定删除')
          if (app.globalData.hasUserInfo) {
            app.getRequest("bookkeeping/deleteBookkeeping", event.currentTarget.dataset.value)
              .then((res) => {
                if (res.data.code != 200) {
                  console.log("删除失败")
                }
                else {
                  that.getBkData()
                  console.log("删除成功")
                }
              })
          } else {
            console.log("用户未登录！")
          }          
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
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
      ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > -50 ? 'right' : 'left'
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
      month:date.getMonth() + 1 + "月",
      date:nowDate,
      bkDataPost: {
        bkDateStr:nowDate,
        remarkText:''
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
      month: month + "月",
      bkDataPost: {
        bkDateStr:e.detail.value,
        remarkText: ''
      }
    })

    this.getBkData()
  },

  //查找
  searchInput: function(e) {
    var value = e.detail.value;
    if(value.split(" ").join("").length == 0) {
      this.getDate();
      this.getBkData();
      return;
    };
    this.setData({
      bkDataPost: {
        bkDateStr: null,
        remarkText: e.detail.value
      }
    });
    this.getBkData();
    this.setData({
      month: "全部",
      bkDataPost: {
        bkDateStr: "全部",
        remarkText: e.detail.value
      }
    });
  },
  
  //获取数据封装
  getBkData() {
    if (app.globalData.hasUserInfo) {
      app.getRequest("bookkeeping/listAll", this.data.bkDataPost)
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
  },

  //获取所有首页数据
  listALL(){
    var that = this;
    setTimeout(function () {
      if(app.globalData.hasUserInfo){
        app.getRequest("bookkeeping/listAll", that.data.bkDataPost)
          .then((res) => {
            if (res.data.code != 200) {
              console.log("无法获取数据")
              wx.hideLoading()
              wx.showModal({
                title: '无法获取数据',
                showCancel: false,
                success (res) {
                  if (res.confirm) {
                    console.log('用户点击确定')
                  }
                }
              })
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
              wx.hideLoading()
            }
          })
      }else{
        //console.log("ListALL-noUserInfo")
        if(that.data.tryTimes<15){
          that.listALL()
          that.data.tryTimes+=1
        }
        
      }
    },300)
  }
})