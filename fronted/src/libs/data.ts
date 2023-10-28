interface ITabs {
  src: string
  text: string
  value: string
}
export const tabs_one: ITabs[] = [
  {
    src: require("../assets/imgs/recomened.png"),
    text: '推荐',
    value: 'recommend'
  },
  {
    src: require("../assets/imgs/my.png"),
    text: '我的',
    value: 'my'
  }
]

export const tabs_two: ITabs[] = [
  {
    src: require("../assets/imgs/hot.png"),
    text: '热点',
    value: 'hot'
  },
  {
    src: require("../assets/imgs/game.png"),
    text: '游戏',
    value: 'game'
  },
  {
    src: require("../assets/imgs/Iconfont.png"),
    text: '二次元',
    value: '2D'
  },
  {
    src: require("../assets/imgs/music.png"),
    text: '音乐',
    value: 'music'
  },
  {
    src: require("../assets/imgs/food.png"),
    text: '美食',
    value: 'food'
  },
  {
    src: require("../assets/imgs/sport.png"),
    text: '体育',
    value: 'sport'
  }
]
