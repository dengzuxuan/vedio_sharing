import React, { useEffect, useState } from 'react'
import style from './index.module.scss'
import likeIcon from '../../../assets/imgs/commentLike.png'
import like_clickIcon from '../../../assets/imgs/commentLike_click.png'
import { type IComment, type IGetComments } from '../../../libs/model'
import { addcommentlikes, delcomment, delcommentlikes, getsecondcomments } from '../../../api/comment'
import SecondComment from './SecondComment'
import { message } from 'antd'
interface Props {
  item: IGetComments
  setReturnComment: (data: IGetComments | undefined) => void
  getFirstComment: (id: number) => Promise<IGetComments[] | undefined>
  updateFlag: boolean
  setUpdateFlag: (data: boolean) => void
  videoId: number | undefined
  getMesages: () => void
}
export default function Comment({ item, setReturnComment, getFirstComment, updateFlag, setUpdateFlag, videoId, getMesages }: Props) {
  const id = localStorage.getItem('id')
  const [itemCopy, setItemCopy] = useState(item)
  // 保存二级评论
  const [secondComment, setSecondComment] = useState<IGetComments[]>()
  // 记录点击回复二级评论
  const [clickSecond, setClickSecond] = useState<number>()
  // 保存回复内容
  const [lastComment, setLastComment] = useState<string>()
  const clickLike = async () => {
    if (!itemCopy.like) {
      const res = await addcommentlikes(item.comment.id)
      if (res?.code === 200) {
        setItemCopy({ ...itemCopy, like: true, comment: { ...itemCopy.comment, likes: itemCopy.comment.likes + 1 } })
      }
    } else {
      const res = await delcommentlikes(item.comment.id)
      if (res?.code === 200) {
        setItemCopy({ ...itemCopy, like: false, comment: { ...itemCopy.comment, likes: itemCopy.comment.likes - 1 } })
      }
    }
  }

  // 获得二级评论
  const func = async () => {
    const data = await getFirstComment(item.comment.id)
    setSecondComment(data)
  }

  // 删除评论
  const del = async () => {
    const res = await delcomment(item.comment.id)
    if (res?.code === 200) {
      message.success('删除成功')
      getMesages()
    }
  }

  useEffect(() => {
    func()
  }, [])

  useEffect(() => {
    if (updateFlag) {
      func()
      setUpdateFlag(false)
    }
  }, [updateFlag])
  return (
    <div className={style.back}>
      <div className={style.img_name}>
        <div className={style.img_box}>
          <img className={style.img} src={item.user.photo}></img>
        </div>
        <div className={style.text}>{item.user.nickname}</div>
        {
          parseInt(id ?? '0') === item.user.id ? <div className={style.del} onClick={() => del()}>删除</div> : ''
        }
      </div>
      <div className={style.comment}>{item.comment.content}</div>
      <div className={style.time}>{item.comment.createTime}</div>
      <div className={style.func}>
        <div className={style.func_text} onClick={() => setReturnComment(item)}>回复</div>
        <div className={style.like_text} onClick={() => clickLike()}>
          <img src={itemCopy.like ? like_clickIcon : likeIcon} className={style.likeIcon}></img>
          {itemCopy.comment.likes}
        </div>
      </div>
      <div className={style.secondComment}>
        {
          secondComment?.length
            ? <>
              {
                secondComment.map(item2 => <SecondComment func={func} videoId={videoId} setClickSecond={setClickSecond} clickSecond={clickSecond} setLastComment={setLastComment} lastComment={lastComment} key={item2.comment.commentId} item2={item2} />)
              }
            </>
            : ''
        }
      </div>
    </div>
  )
}
