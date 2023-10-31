export interface IVideo {
  controls?: boolean
  autoplay: boolean // 加载完成是否自动播放
  loop: boolean // 视频播放结束后，是否循环播放
  notSupportedMessage: string
  poster: string // 视频封面
  controlBar?: {
    timeDivider?: boolean // 是否显示时间控制条，默认为true
    remainingTimeDisplay?: boolean // 是否显示剩余时间，默认为true
    fullscreenToggle?: boolean // 全屏按钮
    children: [
      { name: 'playToggle' }, // 播放按钮
      {
        name: 'volumePanel'// 音量控制
        inline: false// 不使用水平方式
      },
      { name: 'currentTimeDisplay' }, // 当前已播放时间
      { name: 'durationDisplay' }, // 总时间
      { name: 'progressControl' }, // 播放进度条
      {
        name: 'pictureInPictureToggle'// 支持画中画
      },
      {
        name: 'FullscreenToggle'// 支持全屏
      }
    ]
  }
}
