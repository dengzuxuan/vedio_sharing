import React, { useEffect, useState } from 'react'
import style from './index.module.scss'
import { Image, Modal, Spin, Upload, message, Form, Input, Radio, Button } from 'antd'
import { getFrdInfo, getSendFrd, getcollectvideos, getlikevideos, updateInfo, userInfo, userVideo } from '../../api/personal'
import { type IVideoInfo, type IGetInfo, type IFrd } from '../../libs/model'
import manIcon from '../../assets/imgs/man.png'
import womanIcon from '../../assets/imgs/woman.png'
import { type UploadProps, type RcFile, type UploadChangeParam, type UploadFile } from 'antd/lib/upload'
import { postPic } from '../../api/common'
import { useForm } from 'antd/lib/form/Form'
import VideoComponent from '../../components/VideoComponent'
import { basicVideoInitOption } from '../../libs/data'

import FrdItem from './FrdItem'
interface IFrdInfo {
  frds: IFrd[]
  tabs: string
}
export default function Self() {
  const [form] = useForm()
  const [clickTabs, setClickTabs] = useState('work')
  const [selfInfo, setSelfInfo] = useState<IGetInfo>()
  const [coverLoading, setCoverLoading] = useState(false)
  const [selfVideo, setSelfVideo] = useState<IVideoInfo[]>()
  const [frd, setFrd] = useState<IFrdInfo>({ frds: [], tabs: '' })
  const [isFrdModal, setIsFrdModal] = useState(false)

  // 控制modal
  const [isModal, setIsModal] = useState(false)

  const handleCancel = () => {
    setIsModal(false)
  }

  const getSelfInfo = async () => {
    const res = await userInfo()
    if (res?.code === 200) {
      setSelfInfo(res.data)
    }
  }

  // 检查图片
  const beforeUpload = (file: RcFile) => {
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

  const clickChange = async (values: any) => {
    let sex = 0
    if (values.sexual === 1) {
      sex = 1
    }
    let collect = 0
    let like = 0
    if (values.collect === 1) {
      collect = 1
    }
    if (values.like === 1) {
      like = 1
    }
    console.log(values)
    const res = await updateInfo(values.nickname, selfInfo?.photo ?? '', values.email ?? '', sex, like, collect)
    if (res?.code === 200) {
      message.success('修改成功')
      getSelfInfo()
      setIsModal(false)
    } else {
      message.info(res?.message)
    }
  }

  // 获取个人作品
  const getVideos = async (clickTabs: string) => {
    let res
    switch (clickTabs) {
      case 'work':
        res = await userVideo()
        break
      case 'good':
        res = await getlikevideos()
        break
      case 'collect':
        res = await getcollectvideos()
        break
      default:
        console.log('')
    }
    if (res?.code === 200) {
      setSelfVideo(res.data)
    }
  }

  const getFriendInfo = async () => {
    let res
    if (frd?.tabs === 'sendFrd') {
      res = await getSendFrd()
    } else if (frd?.tabs === 'frd') {
      res = await getFrdInfo()
    }
    if (res?.code) {
      frd && setFrd({ ...frd, frds: res.data })
    }
  }

  const clickFrdClick = (tabs: string) => {
    setFrd({ ...frd, tabs })
    setIsFrdModal(true)
  }

  // 跳转到新页面
  const jump = (id: number) => {
    const w = window.open('_black')
    if (w) {
      w.location.href = `/video/${id}`
    }
  }

  useEffect(() => {
    getFriendInfo()
  }, [frd?.tabs])

  useEffect(() => {
    getVideos(clickTabs)
  }, [clickTabs])

  useEffect(() => {
    getSelfInfo()
  }, [])

  const option = {
    ...basicVideoInitOption,
    controls: true
  }
  return (
    <div className={style.back}>
      <div className={style.self_info}>
        <div className={style.img_box}>
          <Image
            width={120}
            src={selfInfo?.photo}
          />
        </div>
        <div className={style.info_box}>
          <div className={style.name_text}>{selfInfo?.nickname}</div>
          <div className={style.user_info}>
            <div className={style.item_info}>
              <span className={style.text} onClick={() => clickFrdClick('sendFrd')}>关注</span>
              <span className={style.number}>{selfInfo?.sendFriends}</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text} onClick={() => clickFrdClick('frd')}>粉丝</span>
              <span className={style.number}>{selfInfo?.friends}</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>获赞</span>
              <span className={style.number}>{selfInfo?.likes}</span>
            </div>
          </div>
          <div className={style.username_info}>
            <div>
              <span>账号：</span>
              <span>{selfInfo?.username}</span>
            </div>
            <div><img src={selfInfo?.sexual === 0 ? manIcon : womanIcon} className={style.sexIcon}></img></div>
          </div>
        </div>
        <div className={style.editInfo}>
          <div className={style.editBtn} onClick={() => setIsModal(true)}>编辑资料</div>
        </div>
      </div>
      <div className={style.tabs}>
        <div className={clickTabs === 'work' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('work')}>
          <span className={style.tabs_text}>作品</span>
          <span className={style.number}>{selfInfo?.videos}</span>
        </div>
        <div className={clickTabs === 'good' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('good')}>
          <span className={style.tabs_text}>喜爱</span>
          <span className={style.number}>{selfInfo?.likes}</span>
        </div>
        <div className={clickTabs === 'collect' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('collect')}>
          <span className={style.tabs_text}>收藏</span>
          <span className={style.number}>{selfInfo?.sendCollects}</span>
        </div>
      </div>
      <div className={style.video_box}>
        {
          selfVideo
            ? selfVideo.map(item =>
              <div key={item.id} className={style.video_item} onClick={() => jump(item.id)}>
                <div className={style.video_div}>
                  <VideoComponent propsOption={{ ...option, loop: true, poster: item.photoUrl }} hoverFunc={true} videoUrl={item.videoUrl}></VideoComponent>
                </div>
                <div className={style.title_description} title={item.description}>
                  <span className={style.title}>{item.title}</span>
                  <span className={style.description}>
                    {
                      item.description
                    }
                  </span>
                </div>
              </div>)
            : ''
        }
      </div>
      <Modal
        className={style.modal}
        open={isModal}
        okText='修改'
        cancelText='取消'
        title='编辑资料'
        footer={null}
        onCancel={handleCancel}
      >
        <div className={style.uploadDiv}>
          <div>
            <Upload
              name="avatar"
              accept='.png, .jpg, .webp'
              listType="picture-card"
              className="avatar-uploader"
              showUploadList={false}
              onChange={handleChange}
              customRequest={async (option) => {
                const before = beforeUpload(option.file as RcFile)
                if (before) {
                  const temp = new FormData()
                  temp.append('file', option.file)
                  const res = await postPic(temp)
                  if (res?.code === 200 && selfInfo) {
                    setSelfInfo({ ...selfInfo, photo: res.data.url })
                    setCoverLoading(false)
                  } else {
                    message.info(res?.message)
                  }
                } else {
                  setCoverLoading(false)
                }
              }}
            >
              <div className={style.cover_box}>
                <img src={selfInfo?.photo} style={{ width: '100px' }} />
                <Spin className={style.spin} spinning={coverLoading}></Spin>
              </div>
            </Upload>
          </div>
        </div>
        <Form
          form={form}
          layout='vertical'
          onFinish={clickChange}
        >
          <Form.Item
            label='昵称'
            name='nickname'
            initialValue={selfInfo?.nickname}
            rules={[
              { required: true, message: '昵称不为空' },
              { max: 12, message: '昵称不多于20位' }
            ]}
          >
            <Input showCount maxLength={20}></Input>
          </Form.Item>
          <Form.Item
            label='性别'
            name='sexual'
          >
            <Radio.Group defaultValue={selfInfo?.sexual}>
              <Radio value={0}>男</Radio>
              <Radio value={1}>女</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item
            label='邮箱'
            name='email'
          >
            <Input defaultValue={selfInfo?.email}></Input>
          </Form.Item>
          <Form.Item
            label='喜爱设置'
            name='like'
          >
            <Radio.Group defaultValue={selfInfo?.likeHidden}>
              <Radio value={0}>公开</Radio>
              <Radio value={1}>隐藏</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item
            label='收藏设置'
            name='collect'
          >
            <Radio.Group defaultValue={selfInfo?.collectHidden}>
              <Radio value={0}>公开</Radio>
              <Radio value={1}>隐藏</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item>
            <div className={style.buttons}>
              <Button className={style.button} type='primary' htmlType="submit">修改</Button>
            </div>
          </Form.Item>
        </Form>
      </Modal>
      <Modal
        title={frd?.tabs === 'sendFrd' ? '关注' : '粉丝'}
        open={isFrdModal}
        onCancel={() => { setIsFrdModal(false); setFrd({ ...frd, frds: [] }) }}
        footer={null}
        width={1100}
      >
        {
          frd.frds.map(item =>
            <FrdItem item={item} tabs={frd.tabs} getFriendInfo={getFriendInfo} key={item.id} />)
        }
      </Modal>
    </div>
  )
}
