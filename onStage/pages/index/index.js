//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    token:'',
    modalName:'',
    textareaAInput:'',
    loginData:{
        encryptedData: '',
        code: '',
        iv: '',
    },
    bkDateStr:{
      bkDateStr:'',
    },
    bkData:{
      monthIncomeMoney: "--",
      monthExpendMoney: "--",
      allIncomeMoney: "--",
      todayExpendMoney: "--",
      allExpendMoney: "--",
      todayIncomeMoney: "--"
    }

  },
  //页面准备好的时候自动调用
  onReady: function () {
    var that=this;
    //延迟，否则还未从后端获取到数据就已经写入
    setTimeout(function () {
      console.log(app.globalData.userInfo)
      if(app.globalData.hasUserInfo){
        that.getDate()
        app.getRequest("/bookkeeping/allIncomeExpendMoney",that.data.bkDateStr)
        .then((res)=>{
          that.setData({
            bkData:{
              monthIncomeMoney: res.data.data.monthIncomeMoney,
              monthExpendMoney: res.data.data.monthExpendMoney,
              allIncomeMoney: res.data.data.allIncomeMoney,
              todayExpendMoney: res.data.data.todayExpendMoney,
              allExpendMoney: res.data.data.allExpendMoney,
              todayIncomeMoney: res.data.data.todayIncomeMoney
            }
          })
        })
        that.setData({
          userInfo:app.globalData.userInfo,
          hasUserInfo:app.globalData.hasUserInfo,
        })
      }else{
        console.log("用户未登录！")
      }

    }, 2000) 
  },

  //页面显示时执行
  onShow: function () {
    if(this.data.hasUserInfo){
      this.getDate()
      app.getRequest("/bookkeeping/allIncomeExpendMoney",this.data.bkDateStr)
      .then((res)=>{
        console.log(res)
        if(res.data.code!=200){
          console.log("无法获取统计数据")
        }
        else{
          this.setData({
            bkData:{
              monthIncomeMoney: res.data.data.monthIncomeMoney,
              monthExpendMoney: res.data.data.monthExpendMoney,
              allIncomeMoney: res.data.data.allIncomeMoney,
              todayExpendMoney: res.data.data.todayExpendMoney,
              allExpendMoney: res.data.data.allExpendMoney,
              todayIncomeMoney: res.data.data.todayIncomeMoney
            }
          })
        }
      })
    }else{
      console.log("用户未登录！")
    }

  },


  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  //获取用户数据
  getUserInfo() {
    var that=this;
    return new Promise(function(resolve, reject){
      wx.getUserInfo({
        success: function(e) {
          app.globalData.userInfo = e.userInfo
          that.setData({
            loginData:{
              iv:e.iv,
              encryptedData:e.encryptedData,
              code:app.globalData.loginData.code
            } 
          })
          resolve();
        }
      })
        
    }) 
  },

  //用户登录操作
  userLogin(e){
    console.log(e);
    var that=this;
    this.getUserInfo()
    .then(()=>{
      return app.getRequest("api/getUserData",this.data.loginData)      
    })
    .then((res)=>{
      that.setData({
        userInfo:res.data.data,
        hasUserInfo:true
      })
    })  
  },

  //session测试拿数据
  getdata(){
    app.getRequest("api/getOpenId","test")
    .then((res)=>{
      if(res.code!=4003){
        console.log(res)
        this.data.motto=res.data.data
      }
    })
  },

  //测试状态转换
  switchTest(){
    this.setData({
      modalName:'DialogModal1'
    })
  },
  //隐藏模态窗
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  //显示图片
  showQrcode() {
    wx.previewImage({
      urls: ["/static/images/test.png"]
    })
    // wx.previewImage({
    //   urls: ['/static/images/test.png'],
    //   current: '/static/images/test.png' // 当前显示图片的http链接      
    // })
  },
  //获取当前时间
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
      bkDateStr:{
        bkDateStr:nowDate
      },
    })
    
  }

})
