import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import { otheruserinfo } from '../../api/personal'
import style from './index.module.scss'
import { type IGetInfo } from '../../libs/model'
import manIcon from '../../assets/imgs/man.png'
import womanIcon from '../../assets/imgs/woman.png'

export default function User() {
  const user_id = useLocation().pathname.split('/')[2]
  const [item, setItem] = useState<IGetInfo>()
  // 获得用户信息
  const getUserInfo = async () => {
    const res = await otheruserinfo(parseInt(user_id))
    if (res?.code === 200) {
      setItem(res.data)
    }
  }
  useEffect(() => {
    getUserInfo()
  }, [])
  return (
    <div className={style.back}>
      <div className={style.header}>
        <div className={style.img_box}>
          <img className={style.img} src={item?.photo}></img>
        </div>
        <div className={style.middle_box}>
          <div className={style.nickName}>{item?.nickname}</div>
          <div className={style.user_info}>
            <div className={style.item_info}>
              <span className={style.text} >关注</span>
              <span className={style.number}>{item?.sendFriends}</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>粉丝</span>
              <span className={style.number}>{item?.friends}</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>获赞</span>
              <span className={style.number}>{item?.likes}</span>
            </div>
          </div>
          <div className={style.username_info}>
            <div>
              <span>账号：</span>
              <span>{item?.username}</span>
            </div>
            <div><img src={item?.sexual === 0 ? manIcon : womanIcon} className={style.sexIcon}></img></div>
          </div>
        </div>
      </div>
      <div className={style.videos_box}></div>
    </div>
  )
}
