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
