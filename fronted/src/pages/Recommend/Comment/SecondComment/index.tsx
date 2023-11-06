import React, { useEffect, useState } from 'react'
import style from './index.module.scss'
import { type IGetComments } from '../../../../libs/model'
import likeIcon from '../../../../assets/imgs/commentLike.png'
import like_clickIcon from '../../../../assets/imgs/commentLike_click.png'
import { addcomment, addcommentlikes, delcomment, delcommentlikes, getsecondcomments } from '../../../../api/comment'
import { Input, message } from 'antd'
interface Props {
  item2: IGetComments
  clickSecond: number | undefined
  setClickSecond: (data: number | undefined) => void
  lastComment: string | undefined
  setLastComment: (data: string | undefined) => void
  videoId: number | undefined
  func: () => void
}
export default function SecondComment({ item2, clickSecond, setClickSecond, lastComment, setLastComment, videoId, func }: Props) {
  const id = localStorage.getItem('id')
  const [itemCopy, setItemCopy] = useState(item2)
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

  // 发布评论（对二级）
  const publishFirstMessage = async () => {
    const data = lastComment?.trim()
    if (!data || !videoId) return
    const res = await addcomment(videoId, clickSecond as number, data, 3)
    if (res?.code === 200) {
      message.success('发布成功')
      setClickSecond(undefined)
      setLastComment(undefined)
      func()
    } else {
      message.info(res?.message)
    }
  }

  // 删除评论
  const del = async () => {
    const res = await delcomment(item2.comment.id)
    if (res?.code === 200) {
      message.success('删除成功')
      func()
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
        <div className={style.func_text} onClick={() => setClickSecond(item2.comment.id)}>回复</div>
        <div className={style.like_text} onClick={() => clickLike()}>
          <img src={itemCopy.like ? like_clickIcon : likeIcon} className={style.likeIcon}></img>
          {itemCopy.comment.likes}
        </div>
        {
          parseInt(id ?? '0') === item2.user.id ? <div className={style.del} onClick={() => del()}>删除</div> : ''
        }
      </div>
      {
        clickSecond === item2.comment.id
          ? <div className={style.input_box}>
            <Input className={style.input} value={lastComment} onChange={(e) => setLastComment(e.target.value)}></Input>
            <span className={style.publish} onClick={() => publishFirstMessage()}>发布</span>
          </div>
          : ''
      }
    </div>
  )
}
