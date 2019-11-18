const authentication = require('../service/authentication.js');

// login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    login: {
      loading: false,
      login: false
    },
    logout: {
      loading: false
    },
    userInfo: {
      nickname: 'test'
    },
    userInfoNodes: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      userInfo: {
        avatarUrl: '../images/default-avatar.png'
      },
      userInfoNodes: []
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  clickLogin() {
    console.log('login');
    const page = this;
    page.setData({
      login: {
        loading: true
      }
    })
    authentication.login(function(userInfo) {
        page.setUserInfo(userInfo);
        page.setData({
          login: {
            loading: false,
            login: true
          }
        })
        wx.setNavigationBarTitle({
          title: 'Logged',
        })
      },
      function(e) {
        console.error(e);
        wx.showModal({
          title: 'error',
          content: e['errMsg'],
          success(res) {}
        });
        page.setData({
          login: {
            loading: false
          }
        })
      });
  },
  clickLogout() {
    console.log('logout');
    const page = this;
    page.setData({
      logout: {
        loading: true
      }
    })
    authentication.logout(function() {
      page.onLoad();
      page.setData({
        logout: {
          loading: false
        },
        login: {
          login: false
        }
      })
      wx.setNavigationBarTitle({
        title: 'Login',
      })
    }, function(error) {
      page.setData({
        logout: {
          loading: false
        }
      })
    })
  },
  setUserInfo(userInfo) {
    const page = this;
    const userInfoJson = JSON.stringify(userInfo, null, 2);
    const userInfoNodes = [{
      name: 'pre',
      attrs: {
        class: 'userInfo',
        style: ''
      },
      children: [{
        type: 'text',
        text: userInfoJson
      }]
    }]
    page.setData({
      userInfo: userInfo,
      userInfoNodes: userInfoNodes
    })
  }
})