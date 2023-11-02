import { type ISelfVideo, type IGetInfo, type IFrd } from "../libs/model"
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
export const updateInfo = async (nickname: string, photo: string, email: string, sexual: number) => {
  return await request({
    url: '/api/v1/user/updateinfo',
    method: 'POST',
    data: {
      nickname,
      photo,
      email,
      sexual
    }
  })
}

// 获取个人作品
export const userVideo = async () => {
  return await request<ISelfVideo[]>({
    url: '/api/v1/video/uservideo',
    method: 'GET'
  })
}

// 获取点赞视频
export const getlikevideos = async () => {
  return await request<ISelfVideo[]>({
    url: '/api/v1/optvideo/getlikevideos',
    method: 'GET'
  })
}

// 获取收藏视频
export const getcollectvideos = async () => {
  return await request<ISelfVideo[]>({
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
  return await request<IGetInfo>({
    url: '/api/v1/user/otheruserinfo',
    method: 'GET',
    params: {
      userid
    }
  })
}
