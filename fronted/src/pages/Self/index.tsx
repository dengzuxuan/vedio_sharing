import React, { useEffect, useState } from 'react'
import style from './index.module.scss'
import { Image, Modal, Spin, Upload, message, Form, Input, Radio, Button } from 'antd'
import { updateInfo, userInfo } from '../../api/personal'
import { type IGetInfo } from '../../libs/model'
import manIcon from '../../assets/imgs/man.png'
import womanIcon from '../../assets/imgs/woman.png'
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons'
import { type UploadProps, type RcFile, type UploadChangeParam, type UploadFile } from 'antd/lib/upload'
import { postPic } from '../../api/common'
import { useForm } from 'antd/lib/form/Form'
export default function Self() {
  const [form] = useForm()
  const [clickTabs, setClickTabs] = useState('work')
  const [selfInfo, setSelfInfo] = useState<IGetInfo>()
  const [cover, setCover] = useState<RcFile>()
  const [coverLoading, setCoverLoading] = useState(false)
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
    if (isPNG && isLt5M) {
      setCover(file)
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
    const res = await updateInfo(values.nickname, selfInfo?.photo ?? '', values.email, sex)
    if (res?.code === 200) {
      message.success('修改成功')
      getSelfInfo()
      setIsModal(false)
    } else {
      message.info(res?.message)
    }
  }
  useEffect(() => {
    getSelfInfo()
  }, [])
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
              <span className={style.text}>关注</span>
              <span className={style.number}>{ }</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>获赞</span>
              <span className={style.number}>22</span>
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
          <span className={style.number}>0</span>
        </div>
        <div className={clickTabs === 'good' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('good')}>
          <span className={style.tabs_text}>获赞</span>
          <span className={style.number}>0</span>
        </div>
        <div className={clickTabs === 'collect' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('collect')}>
          <span className={style.tabs_text}>收藏</span>
          <span className={style.number}>0</span>
        </div>
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
          <Form.Item>
              <div className={style.buttons}>
                <Button className={style.button} type='primary' htmlType="submit">修改</Button>
              </div>
            </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}
