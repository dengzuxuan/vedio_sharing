import React, { useEffect, useState } from 'react'
import style from './index.module.scss'
import { type IGetComments } from '../../../../libs/model'
import likeIcon from '../../../../assets/imgs/commentLike.png'
import like_clickIcon from '../../../../assets/imgs/commentLike_click.png'
import { addcommentlikes, delcommentlikes, getsecondcomments } from '../../../../api/comment'
interface Props {
  item2: IGetComments
}
export default function SecondComment({ item2 }: Props) {
  const [itemCopy, setItemCopy] = useState(item2)
  // 记录是否点击回复
  const [isClickPublish, setClickPublish] = useState(false)
  // 保存二级评论
  const [secondComment, setSecondComment] = useState<IGetComments[]>()
  const clickLike = async () => {
    if (!itemCopy.like) {
      const res = await addcommentlikes(item2.comment.id)
      if (res?.code === 200) {
        setItemCopy({ ...itemCopy, like: true, comment: { ...itemCopy.comment, likes: itemCopy.comment.likes + 1 } })
      }
    } else {
      const res = await delcommentlikes(item2.comment.id)
      if (res?.code === 200) {
        setItemCopy({ ...itemCopy, like: false, comment: { ...itemCopy.comment, likes: itemCopy.comment.likes - 1 } })
      }
    }
  }
  return (
    <div className={style.back}>
      <div className={style.img_name}>
        <div className={style.img_box}>
          <img className={style.img} src={item2.user.photo}></img>
        </div>
        <div className={style.text}>{item2.user.nickname}</div>
        <div className={style.comment}>{item2.comment.content}</div>
      </div>
      <div className={style.func}>
      <div className={style.time}>{item2.comment.createTime}</div>
        <div className={style.func_text} onClick={() => setClickPublish(true)}>回复</div>
        <div className={style.like_text} onClick={() => clickLike()}>
          <img src={itemCopy.like ? like_clickIcon : likeIcon} className={style.likeIcon}></img>
          {itemCopy.comment.likes}
        </div>
      </div>
    </div>
  )
}
