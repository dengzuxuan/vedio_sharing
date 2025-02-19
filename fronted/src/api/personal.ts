import {
  type IGetInfo,
  type IFrd,
  type IVideoInfo,
  type IGetVideo,
  type IGetOtherInfo,
  type IGetTypeVideos,
  type IGetMsg,
  type ISearch
} from "../libs/model"
import request from "../utils/request"

// 收藏
export const addCollect = async (video_id: number) => {
  return await request({
    url: '/api/v1/optvideo/addcollect',
    method: 'POST',
    params: {
      video_id
    }
  })
}

// 取消收藏
export const delCollect = async (video_id: number) => {
  return await request({
    url: '/api/v1/optvideo/delcollect',
    method: 'POST',
    params: {
      video_id
    }
  })
}

// 点赞
export const addLike = async (video_id: number) => {
  return await request({
    url: '/api/v1/optvideo/addlike',
    method: 'POST',
    params: {
      video_id
    }
  })
}

// 取消点赞
export const delLike = async (video_id: number) => {
  return await request({
    url: '/api/v1/optvideo/dellike',
    method: 'POST',
    params: {
      video_id
    }
  })
}

// 关注
export const addFrd = async (userid: number) => {
  return await request({
    url: '/api/v1/user/addfriend',
    method: 'POST',
    params: {
      userid
    }
  })
}

// 取消关注
export const delFrd = async (userid: number) => {
  return await request({
    url: '/api/v1/user/delfriend',
    method: 'POST',
    params: {
      userid
    }
  })
}

// 获取个人信息
export const userInfo = async () => {
  return await request<IGetInfo>({
    url: '/api/v1/user/userinfo',
    method: 'GET'
  })
}

// 修改个人信息
export const updateInfo = async (nickname: string, photo: string, email: string, sexual: number, likehidden: number, collecthidden: number) => {
  return await request({
    url: '/api/v1/user/updateinfo',
    method: 'POST',
    data: {
      nickname,
      photo,
      email,
      sexual,
      likehidden,
      collecthidden
    }
  })
}

// 获取个人作品
export const userVideo = async () => {
  return await request<IVideoInfo[]>({
    url: '/api/v1/video/uservideo',
    method: 'GET'
  })
}

// 获取点赞视频
export const getlikevideos = async () => {
  return await request<IVideoInfo[]>({
    url: '/api/v1/optvideo/getlikevideos',
    method: 'GET'
  })
}

// 获取收藏视频
export const getcollectvideos = async () => {
  return await request<IVideoInfo[]>({
    url: '/api/v1/optvideo/getcollectvideos',
    method: 'GET'
  })
}

// 获取粉丝信息
export const getFrdInfo = async () => {
  return await request<IFrd[]>({
    url: '/api/v1/user/getfriend',
    method: 'GET'
  })
}

// 获得关注信息
export const getSendFrd = async () => {
  return await request<IFrd[]>({
    url: '/api/v1/user/getsendfriend',
    method: 'GET'
  })
}

// 取关
export const delfriend = async (userid: number) => {
  return await request({
    url: '/api/v1/user/delfriend',
    method: 'POST',
    params: {
      userid
    }
  })
}

// 获取其他人信息
export const otheruserinfo = async (userid: number) => {
  return await request<IGetOtherInfo>({
    url: '/api/v1/user/otheruserinfo',
    method: 'GET',
    params: {
      userid
    }
  })
}

// 获得某视频信息
export const getsinglevideo = async (videoid: number) => {
  return await request<IGetVideo>({
    url: '/api/v1/video/getsinglevideo',
    method: 'GET',
    params: {
      videoid
    }
  })
}

// 获取他人点赞视频
export const getotherlikevideos = async (user_id: number) => {
  return await request<IVideoInfo[]>({
    url: '/api/v1/optvideo/getotherlikevideos',
    method: 'GET',
    params: {
      user_id
    }
  })
}

// 获取他人收藏视频
export const getothercollectvideos = async (user_id: number) => {
  return await request<IVideoInfo[]>({
    url: '/api/v1/optvideo/getothercollectvideos',
    method: 'GET',
    params: {
      user_id
    }
  })
}

// 获取他人发布视频
export const otheruservideo = async (user_id: number) => {
  return await request<IVideoInfo[]>({
    url: '/api/v1/video/otheruservideo',
    method: 'GET',
    params: {
      user_id
    }
  })
}

// 获取类型视频
export const gettypevideo = async (type: number) => {
  return await request({
    url: '/api/v1/video/gettypevideo',
    method: 'GET',
    params: {
      type
    }
  })
}

// 获取类型下全部视频
export const gettypevideos = async (type: number) => {
  return await request<IGetTypeVideos[]>({
    url: '/api/v1/video/gettypevideos',
    method: 'GET',
    params: {
      type
    }
  })
}

// 获取日榜
export const gettypedayrank = async (type: number) => {
  return await request<IGetTypeVideos[]>({
    url: '/api/v1/video/gettypedayrank',
    method: 'GET',
    params: {
      type
    }
  })
}

// 获取周榜
export const gettypeweekrank = async (type: number) => {
  return await request<IGetTypeVideos[]>({
    url: '/api/v1/video/gettypeweekrank',
    method: 'GET',
    params: {
      type
    }
  })
}

// 获取月榜
export const gettypemonthrank = async (type: number) => {
  return await request<IGetTypeVideos[]>({
    url: '/api/v1/video/gettypemonthrank',
    method: 'GET',
    params: {
      type
    }
  })
}

// 获取未读消息数目
export const getnotread = async () => {
  return await request<number>({
    url: '/api/v1/optvideo/getnotread',
    method: 'GET'
  })
}

// 获得未读消息
export const getmessage = async (type: number) => {
  return await request<IGetMsg[]>({
    url: '/api/v1/optvideo/getmessage',
    method: 'GET',
    params: {
      type
    }
  })
}

// 模拟搜索
export const searchApi = async (content: string) => {
  return await request<ISearch>({
    url: '/api/v1/search/content',
    method: 'GET',
    params: {
      content
    }
  })
}

// 删除视频
export const delVideo = async (video_id: number) => {
  return await request({
    url: '/api/v1/video/delvideo',
    method: 'POST',
    params: {
      video_id
    }
  })
}

// 视频进度
export const judge = async (
  videoId: number,
  is1: boolean,
  is2: boolean,
  is4: boolean
) => {
  return await request({
    url: '/api/v1/video/judge',
    method: 'POST',
    data: {
      videoId,
      is1,
      is2,
      is4
    }
  })
}
