import Dragger from 'antd/lib/upload/Dragger'
import { InboxOutlined, LoadingOutlined, PlusOutlined } from '@ant-design/icons'
import { Form, Input, message, type UploadProps, Upload, Select, Button, Image, Spin } from 'antd'
import style from './index.module.scss'
import React, { useRef, useState } from 'react'
import { type RcFile, type UploadChangeParam, type UploadFile } from 'antd/lib/upload'
import { postPic, postVideo } from '../../api/common'
import { typeList, basicVideoInitOption } from '../../libs/data'
import { useForm } from 'antd/lib/form/Form'
import VideoComponent from '../../components/VideoComponent'
import { addVideo } from '../../api/uploadVideo'
import { useNavigate } from 'react-router-dom'
import { type IUploadVideo } from '../../libs/model'

export default function UploadView() {
  const [uploadVideoReturn, setUploadVideoReturn] = useState<IUploadVideo>()
  const navigator = useNavigate()
  // 视频
  // const [videoUrl, setVideoUrl] = useState<string>('')
  const [videoLoading, setVideoLoading] = useState(false)
  // 封面url
  const [coverUrl, setCoverUrl] = useState('')
  // 封面loading
  const [coverLoading, setCoverLoading] = useState(false)
  const [form] = useForm()

  // 检查上传文件
  const beforeUpload = (file: RcFile) => {
    const isTypeTrue = file.type === 'video/mp4'
    const isLt5M = file.size / 1024 / 1024 < 5
    if (!isTypeTrue) {
      message.error(`${file.name} 文件只能为mp4格式`)
    }
    if (!isLt5M) {
      message.error('图片要小于5MB!')
    }
    return isTypeTrue && isLt5M
  }

  // 检查封面
  const beforeUpload2 = (file: RcFile) => {
    const isPNG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/webp'
    if (!isPNG) {
      message.error(`${file.name} 图片只能位png、jpeg、jpg或webp格式`)
    }
    const isLt5M = file.size / 1024 / 1024 < 5
    if (!isLt5M) {
      message.error('图片要小于5MB!')
    }
    return isPNG && isLt5M
  }

  const handleChange: UploadProps['onChange'] = (info: UploadChangeParam<UploadFile>) => {
    if (info.file.status === 'uploading') {
      setCoverLoading(true)
    }
  }

  const handleChangeVideo: UploadProps['onChange'] = (info: UploadChangeParam<UploadFile>) => {
    if (info.file.status === 'uploading') {
      setVideoLoading(true)
    }
  }

  const resetForm = () => {
    setCoverUrl('')
    form.resetFields()
    setUploadVideoReturn(undefined)
  }

  const uploadVideoInfo = async (values: any) => {
    if (!uploadVideoReturn) {
      return
    }
    if (!coverUrl) {
      message.info('请上传封面')
    } else {
      const res = await addVideo(values.title, values.description, values.type, uploadVideoReturn.url, coverUrl)
      if (res?.code === 200) {
        message.success('上传成功', 1)
          .then(() => navigator('/home/my'))
      } else {
        message.info(res?.message)
      }
    }
  }
  return (
    <div className={style.back}>
      {
        !uploadVideoReturn?.url
          ? <Dragger
            showUploadList={false}
            beforeUpload={beforeUpload}
            accept='.mp4'
            onChange={handleChangeVideo}
            customRequest={async (option) => {
              const before = beforeUpload(option.file as RcFile)
              if (before) {
                const temp = new FormData()
                temp.append('file', option.file)
                const res = await postVideo(temp)
                if (res?.code === 200) {
                  setCoverUrl(res.data.photo_url)
                  setUploadVideoReturn(res.data)
                  setVideoLoading(false)
                } else {
                  message.info(res?.message)
                }
              }
            }}
            height={400}
          >
            {
              videoLoading
                ? <><LoadingOutlined />正在上传</>
                : <><p className="ant-upload-drag-icon">
                  <InboxOutlined />
                </p>
                  <p className="ant-upload-text">点击上传 或直接将视频文件拖至此区域</p>
                  <p className="ant-upload-hint">
                    支持mp4格式文件
                  </p></>
            }
          </Dragger>
          : <div className={style.uploadDiv}>
            <div className={style.title}>发布视频</div>
            <div className={style.uploadInfo}>
              <div className={style.info_left}>
                <Form
                  layout='vertical'
                  onFinish={uploadVideoInfo}
                  form={form}
                >
                  <Form.Item
                    label='作品标题'
                    name='title'
                    initialValue={uploadVideoReturn?.file_title}
                    rules={[
                      { required: true, message: '作品标题不为空' }
                    ]}
                  >
                    <Input showCount maxLength={30}></Input>
                  </Form.Item>
                  <Form.Item
                    label='作品简介'
                    name='description'
                    rules={[
                      { required: true, message: '作品简介不为空' }
                    ]}
                  >
                    <Input.TextArea showCount maxLength={1000}></Input.TextArea>
                  </Form.Item>
                  <Form.Item
                    label='上传封面'
                    name='uploadCover'
                  >
                    <div style={{ marginBottom: '10px' }}>
                      <Upload
                        name="avatar"
                        accept='.png, .jpg, .webp'
                        listType="picture-card"
                        className="avatar-uploader"
                        showUploadList={false}
                        onChange={handleChange}
                        customRequest={async (option) => {
                          const before = beforeUpload2(option.file as RcFile)
                          if (before) {
                            const temp = new FormData()
                            temp.append('file', option.file)
                            const res = await postPic(temp)
                            if (res?.code === 200) {
                              setCoverUrl(res.data.url)
                              setCoverLoading(false)
                            } else {
                              message.info(res?.message)
                            }
                          } else {
                            setCoverLoading(false)
                          }
                        }}
                      >
                        <div className={style.uploadImg}>
                          <img src={coverUrl || uploadVideoReturn?.photo_url} className={style.coverImg} />
                          <Spin className={style.spin} spinning={coverLoading}></Spin>
                        </div>
                      </Upload>
                    </div>
                  </Form.Item>
                  <Form.Item
                    label='视频分类'
                    name='type'
                    rules={[
                      { required: true, message: '视频分类不为空' }
                    ]}
                  >
                    <Select
                      showSearch
                      placeholder="请选择视频类别分类"
                      optionFilterProp="children"
                      filterOption={(input, option) =>
                        (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
                      }
                      options={typeList}
                    />
                  </Form.Item>
                  <Form.Item>
                    <div className={style.btn_box}>
                      <Button type="primary" htmlType="submit" className={style.left_btn}>
                        发布
                      </Button>
                      <Button htmlType="button" className={style.btn} onClick={() => resetForm()}>
                        取消
                      </Button>
                    </div>
                  </Form.Item>
                </Form>
              </div>
              <div className={style.view_video}>
                <div className={style.video_play}>
                  <VideoComponent propsOption={{ ...basicVideoInitOption, controls: false }} videoUrl={uploadVideoReturn.url}></VideoComponent>
                </div>
              </div>
            </div>
            <div></div>
          </div>
      }
    </div >
  )
}
