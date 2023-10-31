import { type IGetInfo } from "../libs/model"
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
