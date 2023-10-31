import { type IUploadVideo } from "../libs/model"
import request from "../utils/request"

interface IUpload {
  url: string
}
// 上传图片
export const postPic = async (file: FormData) => {
  return await request<IUpload>({
    url: '/api/v1/resource/upload/photo',
    method: 'POST',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: file
  })
}
// 上传视频
export const postVideo = async (file: FormData) => {
  return await request<IUploadVideo>({
    url: '/api/v1/resource/upload/video',
    method: 'POST',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: file
  })
}
