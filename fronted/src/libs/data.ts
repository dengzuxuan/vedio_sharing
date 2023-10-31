import { type IVideo } from "./model"
interface ITabs {
  src: string
  text: string
  value: string
}

interface IType {
  value: number
  label: string
}
export const tabs_one: ITabs[] = [
  {
    src: require("../assets/imgs/recomened.png"),
    text: '推荐',
    value: 'recommend'
  },
  {
    src: require("../assets/imgs/upload.png"),
    text: '上传',
    value: 'upload'
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
    value: '0'
  },
  {
    src: require("../assets/imgs/sport.png"),
    text: '体育',
    value: '1'
  },
  {
    src: require("../assets/imgs/game.png"),
    text: '游戏',
    value: '2'
  },
  {
    src: require("../assets/imgs/food.png"),
    text: '美食',
    value: '3'
  },
  {
    src: require("../assets/imgs/music.png"),
    text: '音乐',
    value: '4'
  },
  {
    src: require("../assets/imgs/Iconfont.png"),
    text: '娱乐',
    value: '5'
  },
  {
    src: require("../assets/imgs/knowledge.png"),
    text: '知识',
    value: '6'
  },
  {
    src: require("../assets/imgs/animal.png"),
    text: '动物',
    value: '7'
  }
]

export const typeList: IType[] = [
  {
    value: 1,
    label: '体育'
  },
  {
    value: 2,
    label: '游戏'
  },
  {
    value: 3,
    label: '美食'
  },
  {
    value: 4,
    label: '音乐'
  },
  {
    value: 5,
    label: '娱乐'
  },
  {
    value: 6,
    label: '知识'
  },
  {
    value: 7,
    label: '动物'
  }
]

export const basicVideoInitOption: IVideo = {
  language: 'zh-CN',
  controls: true,
  autoplay: true, // 加载完成是否自动播放
  loop: false, // 视频播放结束后，是否循环播放
  notSupportedMessage: '此视频暂无法播放，请稍后再试',
  poster: '', // 视频封面
  playbackRates: [0.5, 1, 1.5, 2],
  controlBar: {
    timeDivider: true, // 是否显示时间控制条，默认为true
    remainingTimeDisplay: false, // 是否显示剩余时间，默认为true
    fullscreenToggle: true, // 全屏按钮
    children: [ // 自定义
      { name: 'playToggle' }, // 播放按钮
      {
        name: 'volumePanel', // 音量控制
        inline: false // 不使用水平方式
      },
      { name: 'currentTimeDisplay' }, // 当前已播放时间
      { name: 'durationDisplay' }, // 总时间
      { name: 'progressControl' }, // 播放进度条,
      { // 倍数播放，可以自己设置
        name: 'playbackRateMenuButton',
        playbackRates: [0.5, 1, 1.5, 2, 2.5]
      },
      {
        name: 'pictureInPictureToggle' // 支持画中画
      }
    ]
  }
}
