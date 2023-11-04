import { type IGetComments } from "../libs/model"
import request from "../utils/request"

// 评论视频&回复评论
// commentid为0代表回复的是整个视频，否则代表回复的这个commentid对应的评论 回复视频的时候flag=1，这是第一层评论；回复第一层评论的时候flag=2，这是第二层评论；第二层评论之间互相评论的flag=3，这是第三层评论
export const addcomment = async (videoid: number, commentid: number, content: string, flag: number) => {
  return await request({
    url: '/api/v1/optvideo/addcomment',
    method: 'POST',
    data: {
      videoid,
      commentid,
      content,
      flag
    }
  })
}

// 获取一级评论区 同二级
// flag为1代表是回复他人评论，为0代表回复的整个视频
// like为false代表当前用户没有点赞，为true代表点赞了这条评论
export const getfirstcomments = async (video_id: number) => {
  return await request<IGetComments[]>({
    url: '/api/v1/optvideo/getfirstcomments',
    method: 'GET',
    params: {
      video_id
    }
  })
}

// 获取二级评论区
export const getsecondcomments = async (comment_id: number) => {
  return await request<IGetComments[]>({
    url: '/api/v1/optvideo/getsecondcomments',
    method: 'GET',
    params: {
      comment_id
    }
  })
}

// 点赞评论
export const addcommentlikes = async (comment_id: number) => {
  return await request({
    url: '/api/v1/optvideo/addcommentlikes',
    method: 'POST',
    params: {
      comment_id
    }
  })
}

// 取消点赞评论
export const delcommentlikes = async (comment_id: number) => {
  return await request({
    url: '/api/v1/optvideo/delcommentlikes',
    method: 'POST',
    params: {
      comment_id
    }
  })
}

// 删除视频
export const delcomment = async (comment_id: number) => {
  return await request({
    url: '/api/v1/optvideo/delcomment',
    method: 'POST',
    params: {
      comment_id
    }
  })
}
