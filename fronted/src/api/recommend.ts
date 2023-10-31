import request from "../utils/request"

export const getVideo = async () => {
  return await request({
    url: '/api/v1/video/getvideo',
    method: 'GET'
  })
}
