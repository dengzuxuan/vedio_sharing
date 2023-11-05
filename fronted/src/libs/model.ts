export interface IVideo {
  language: string
  controls?: boolean
  autoplay?: boolean // 加载完成是否自动播放
  loop: boolean // 视频播放结束后，是否循环播放
  notSupportedMessage: string
  poster: string // 视频封面
  playbackRates?: number[]
  controlBar?: {
    timeDivider?: boolean // 是否显示时间控制条，默认为true
    remainingTimeDisplay?: boolean // 是否显示剩余时间，默认为true
    fullscreenToggle?: boolean // 全屏按钮
    children: any
  }
}

export interface IUpdateUser {
  sendCollects: number
  bothfriend: boolean
  id: number
  username: string
  nickname: string
  password: string
  email: string
  sexual: number
  photo: string
  likes: number
  collects: number
  friends: number
  views: number
  passwordReal: string
  createTime: string
  updateTime: string
}

export interface IVideoInfo {
  id: number
  userId: number
  title: string
  description: string
  type: number
  videoUrl: string
  photoUrl: string
  hotPoints: number
  viewsPoints: number
  likePoints: number
  collectPoints: number
  commentPoints: number
  createTime: string
  updateTime: string
}

export interface IGetVideo {
  is_friend: boolean
  is_collect: boolean
  is_like: boolean
  user: IUpdateUser
  video: IVideoInfo
}

export interface IGetInfo {
  likeHidden: number
  collectHidden: number
  sendCollects: number
  bothfriend: boolean
  collects: number
  createTime: string
  email: string
  friends: number
  id: number
  likes: number
  nickname: string
  photo: string
  sexual: number
  updateTime: string
  username: string
  views: number
  sendFriends: number
  videos: number
}

export interface IGetOtherInfo {
  followmy: boolean
  myfollow: boolean
  user: IGetInfo
}

export interface IUploadVideo {
  photo_url: string
  file_title: string
  url: string
}

export interface IFrd {
  id: number
  username: string
  nickname: string
  email: string
  sexual: string
  photo: string
  likes: number
  collects: number
  friends: number
  sendFriends: number
  views: number
  createTime: string
  updateTime: string
}

export interface IComment {
  commentId: number
  content: string
  createTime: string
  flag: number
  id: number
  likes: number
  updateTime: string
  userId: number
  videoId: number
}

export interface IGetComments {
  comment: IComment
  like: boolean
  user: IGetInfo
}
