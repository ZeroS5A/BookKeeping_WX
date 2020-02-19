//logs.js
const util = require('../../utils/util.js')
const CHARTS = require('../../utils/wxcharts.js');
var  app  =  getApp();

Page({
  data: {
    logs: [],
    appData:app,
    bkdateStr:'',
    colors: ['#7cb5ec', '#f7a35c', '#434348', '#90ed7d', '#f15c80', '#8085e9'],
    systemData:{},
    monthsData:[],
    ringData:[],
    lineDatax:[],
    lineDatay:[],
    isline:false,
    momthsSum:0
  },
  onLoad: function () {
    var that=this
    // this.setData({
    //   logs: (wx.getStorageSync('logs') || []).map(log => {
    //     return util.formatTime(new Date(log))
    //   })
    // })
    //获取屏幕和手机型号信息
    wx.getSystemInfo({
      success (res) {
        that.setData({
          systemData:{
            model:res.model,
            pixelRatio:res.pixelRatio,
            windowWidth:res.windowWidth,
            windowHeight:res.windowHeight
          },
        })
      }
    })
    this.getDate()
    setTimeout(() => {
      app.getRequest("bookkeeping/listMonthsIncomeExpend",null)
      .then((res)=>{
        that.setData({
          monthsData:res.data.data
        })
        app.getRequest("bookkeeping/MonthsExpendTypeStatisticData",this.data.bkDateStr)
        .then((res)=>{
          this.setData({
            ringData:res.data.data
          })
          //名称转换
          for(var i in this.data.ringData){
            var item = this.data.ringData[i];
            item.name=app.globalData.iconList[item.name];
            this.setData({
              momthsSum:this.data.momthsSum+=item.data
            })
          }          
          this.columnShow()
        })
      })      
    }, 100);

  },
  //画图函数
  columnShow: function() {
    this.setData({
      isline:false
    })

    var column = {
      canvasId: 'lineCanvas',
      type: 'ring',
      series: this.data.ringData,
      width: this.data.systemData.windowWidth,
      height: this.data.systemData.windowWidth/1.2,
      dataLabel: true
    }
    new CHARTS(column);

  },
  //画图函数2
  lineShow(){
    this.setData({
      isline:true
    })
    app.getRequest("bookkeeping/listAll", {bkDateStr:this.data.bkDateStr})
    .then((res)=>{
      var lineDatax=new Array();
      var lineDatay=new Array();
      for(var i in res.data.data.bookkeepingAllList){
        lineDatax.push(res.data.data.bookkeepingAllList[i].dayDate.substring(3,5))
        lineDatay.push(res.data.data.bookkeepingAllList[i].dayExpendMoney)
      }
      this.setData({
        lineDatax:lineDatax.reverse(),
        lineDatay:lineDatay.reverse()
      })
    })
    .then(()=>{
      var line ={
        canvasId: 'lineCanvas',
        type: 'line',
        categories: this.data.lineDatax,
        series: [{
            name: '支出',
            data: this.data.lineDatay,
            format: function (val) {
                return val.toFixed(2) + '元';
            }
        }],
        yAxis: {
            format: function (val) {
                return val.toFixed(2);
            },
            min: 0
        },
        extra: {
          lineStyle: 'curve'
        },
        width: this.data.systemData.windowWidth,
        height: this.data.systemData.windowWidth/1.2,
      }
      new CHARTS(line);      
    })

  },
  //条形图选择
  selectMomth(e){
    var year =e.currentTarget.dataset.year
    var month = e.currentTarget.dataset.month
    if (month < 10) {
      month = "0" + month;
    }
    this.setData({
      bkDateStr:year+'-'+month + "%",
      momthsSum:0,
      year:year,
      month:month
    })
    app.getRequest("bookkeeping/MonthsExpendTypeStatisticData",this.data.bkDateStr)
    .then((res)=>{
      this.setData({
        ringData:res.data.data
      })
      //名称转换
      for(var i in this.data.ringData){
        var item = this.data.ringData[i];
        item.name=app.globalData.iconList[item.name];
        this.setData({
          momthsSum:this.data.momthsSum+=item.data
        })
      }   
      this.columnShow()
    })
  },
  openTypeList: function (e) {
    var that=this
    wx.navigateTo({
      url: '../typeList/typeList',
      success: function(res) {
        // 通过eventChannel向被打开页面传送数据
        res.eventChannel.emit('acceptDataFromOpenerPage', { type: e.currentTarget.dataset.value,date: that.data.bkDateStr})
      }
    })
  },
  //时间获取
  getDate(){
    
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    if (month < 10) {
        month = "0" + month;
    }
    var nowDate = year + "-" + month + "%";
    this.setData({
      bkDateStr:nowDate,
      year:year,
      month:date.getMonth() + 1
    })
    
  },
})

