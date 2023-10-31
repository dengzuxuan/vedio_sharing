import { type IGetVideo } from "../libs/model"
import request from "../utils/request"

export const getVideo = async () => {
  return await request<IGetVideo>({
    url: '/api/v1/video/getvideo',
    method: 'GET'
  })
}
