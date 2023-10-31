import request from "../utils/request"

export const addVideo = async (
  title: string,
  description: string,
  type: number,
  videourl: string,
  photourl: string
) => {
  return await request({
    url: '/api/v1/video/addvideo',
    method: 'POST',
    data: {
      title,
      description,
      type,
      videourl,
      photourl
    }
  })
}
