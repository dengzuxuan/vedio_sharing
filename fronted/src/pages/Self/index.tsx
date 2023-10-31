import React, { useState } from 'react'
import style from './index.module.scss'
import selfTest from '../../assets/imgs/person.webp'
import { Image, Modal } from 'antd'
export default function Self() {
  const [clickTabs, setClickTabs] = useState('work')
  // 控制modal
  const [isModal, setIsModal] = useState(false)

  const handleCancel = () => {
    setIsModal(false)
  }
  return (
    <div className={style.back}>
      <div className={style.self_info}>
        <div className={style.img_box}>
          <Image
            width={120}
            src={selfTest}
          />
        </div>
        <div className={style.info_box}>
          <div className={style.name_text}>黑虎掏心</div>
          <div className={style.user_info}>
            <div className={style.item_info}>
              <span className={style.text}>好友</span>
              <span className={style.number}>11</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>获赞</span>
              <span className={style.number}>22</span>
            </div>
          </div>
          <div className={style.username_info}>
            <span>账号：</span>
            <span>dyg3w6iu88kp</span>
          </div>
        </div>
        <div className={style.editInfo}>
          <div className={style.editBtn} onClick={() => setIsModal(true)}>编辑资料</div>
        </div>
      </div>
      <div className={style.tabs}>
        <div className={clickTabs === 'work' ? style.tabs_itemClick : style.tabs_item } onClick={() => setClickTabs('work')}>
          <span className={style.tabs_text}>作品</span>
          <span className={style.number}>0</span>
        </div>
        <div className={clickTabs === 'good' ? style.tabs_itemClick : style.tabs_item } onClick={() => setClickTabs('good')}>
          <span className={style.tabs_text}>获赞</span>
          <span className={style.number}>0</span>
        </div>
        <div className={clickTabs === 'collect' ? style.tabs_itemClick : style.tabs_item } onClick={() => setClickTabs('collect')}>
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
      onCancel={handleCancel}
      >
        <input className={style.input}></input>
      </Modal>
    </div>
  )
}
