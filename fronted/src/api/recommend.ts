import { type IGetVideo } from "../libs/model"
import request from "../utils/request"

// 推荐页获得视频
export const getVideo = async () => {
  return await request<IGetVideo | null >({
    url: '/api/v1/video/getvideo',
    method: 'GET'
  })
}

// 获取上一个视频
export const getprevideo = async () => {
  return await request<IGetVideo>({
    url: '/api/v1/video/getprevideo',
    method: 'GET'
  })
}

// 清空视频
export const clearprevideo = async () => {
  return await request({
    url: '/api/v1/video/clearprevideo',
    method: 'GET'
  })
}
