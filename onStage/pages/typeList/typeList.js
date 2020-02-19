// pages/typeList/typeList.js
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    postData:{
      type:"bkDate",
    },
    appData:app,
    bookkeepingList:"",
    isDateDESC:true,
    isMoneyDESC:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that=this
    //处理跳转的数据
    setTimeout(function () {
      const eventChannel = that.getOpenerEventChannel()
      eventChannel.on('acceptDataFromOpenerPage', function(data) {
        that.setData({
          postData:{
            type:"bkDate",
            bkType:data.type,
            dateStr:data.date,
            orderType:that.data.isDateDESC?'DESC':'ASC'
          },
        })
      })
      app.getRequest("bookkeeping/listExpendTypeList",that.data.postData)
      .then((res)=>{
        if(res.data.code==200){
          that.setData({
            bookkeepingList:res.data.data
          })
        }
      })
    },50)
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

  changType(e){
    var orderType
    if(e.currentTarget.dataset.value=="bkDate"){
      this.setData({
        isDateDESC:!this.data.isDateDESC
      })
      orderType=this.data.isDateDESC
    }else{
      this.setData({
        isMoneyDESC:!this.data.isMoneyDESC
      })
      orderType=this.data.isMoneyDESC
    }
    this.setData({
      postData:{
        type:e.currentTarget.dataset.value,
        bkType:this.data.postData.bkType,
        dateStr:this.data.postData.dateStr,
        orderType:orderType?'DESC':'ASC'
      }
    })
    app.getRequest("bookkeeping/listExpendTypeList",this.data.postData)
    .then((res)=>{
      if(res.data.code==200){
        this.setData({
          bookkeepingList:res.data.data
        })
      }
    })
  },
})