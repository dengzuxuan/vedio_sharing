import React, { useState } from 'react'
import style from './index.module.scss'
import { type IFrd } from '../../../libs/model'
import { delfriend } from '../../../api/personal'
import { message } from 'antd'
interface Props {
  item: IFrd
  getFriendInfo: () => void
  tabs: string
}

export default function FrdItem({ item, getFriendInfo, tabs }: Props) {
  const [isHover, setIsHover] = useState(false)
  const delFriend = async (user_id: number) => {
    const res = await delfriend(user_id)
    if (res?.code === 200) {
      message.success('取关成功')
      getFriendInfo()
    }
  }
  return (
    <div key={item.id} className={style.frd_div}>
      <div className={style.img_box}>
        <img className={style.img} src={item.photo}></img>
      </div>
      <div className={style.middle_box}>
        <div className={style.nickName}>{item.nickname}</div>
        <div className={style.frditem}>
          <div className={style.item}>关注：{item.sendFriends}</div>
          <div className={style.item}>粉丝数：{item.friends}</div>
        </div>
        <div className={style.email}>{item.email}</div>
      </div>
      {
        tabs === 'sendFrd'
          ? <div
          onClick={() => delFriend(item.id)}
            className={isHover ? style.info_btn_move : style.info_btn}
            onMouseEnter={() => setIsHover(true)}
            onMouseLeave={() => setIsHover(false)}
          >{isHover ? '取消关注' : '已关注'}</div>
          : ''
      }
    </div>
  )
}
