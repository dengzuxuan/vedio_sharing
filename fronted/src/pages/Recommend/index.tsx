import React, { useState, useEffect, useContext } from 'react'
import style from './index.module.scss'
import "video.js/dist/video-js.css"
import { basicVideoInitOption, typeList } from '../../libs/data'
import VideoComponent from '../../components/VideoComponent'
import { Input, Tag, message } from 'antd'
import { clearprevideo, getVideo, getprevideo } from '../../api/recommend'
import { type IGetComments, type IGetVideo } from '../../libs/model'
import likeIcon from '../../assets/imgs/likepoints.png'
import likeIconClick from '../../assets/imgs/like_click.png'
import collectIcon from '../../assets/imgs/collect.png'
import collectClickIcon from '../../assets/imgs/collect_click.png'
import viewIcon from '../../assets/imgs/viewPoints.png'
import viewIconClick from '../../assets/imgs/view_click.png'
import topIcon from '../../assets/imgs/top.png'
import bottomIcon from '../../assets/imgs/bottom.png'
import rightIcon from '../../assets/imgs/left.png'
import Mask from '../../components/Mask'
import { addCollect, addFrd, addLike, delCollect, delFrd, delLike } from '../../api/personal'
import { context } from '../../hooks/store'
import { addcomment, getfirstcomments, getsecondcomments } from '../../api/comment'
import Comment from './Comment'
import useJump from '../../hooks/useJump'

export default function recommend() {
  const { setClickItemValue } = useContext(context)
  const [videoInfo, setVideoInfo] = useState<IGetVideo | null>()
  const [hoverValue, setHoverValue] = useState('')
  const { otherJump } = useJump()
  // 控制左拉
  const [leftClick, setLeftClick] = useState(false)
  // 发布评论内容
  const [messageInfo, setMessageInfo] = useState('')
  // 保存评论
  const [comments, setComments] = useState<IGetComments[]>()
  // 保存对一级的评论
  const [firstComment, setFirstComment] = useState('')
  // 保存回复comment
  const [returnComment, setReturnComment] = useState<IGetComments>()
  // 更新flag
  const [updateFlag, setUpdateFlag] = useState(false)

  // 获得初始视频&&下一个
  const getInitVideo = async () => {
    const res = await getVideo()
    if (res?.code === 200) {
      if (res.data) {
        setVideoInfo(res.data)
        const res2 = await getfirstcomments(res.data.video.id)
        if (res2?.code === 200) {
          setComments(res2.data)
        }
      }
    } else {
      message.info(res?.message)
    }
  }

  // 获取上一个视频
  const getFormer = async () => {
    const res = await getprevideo()
    if (res?.code === 200) {
      setVideoInfo(res.data)
      const res2 = await getfirstcomments(res.data.video.id)
      if (res2?.code === 200) {
        setComments(res2.data)
      }
    } else {
      message.info(res?.message)
    }
  }
  // 清空视频
  const clear = async () => {
    await clearprevideo()
  }

  // 键盘事件函数
  const PopupKeyUp = (e: KeyboardEvent) => {
    console.log(e.code)
    if (e.code === 'ArrowUp') {
      getFormer()
    } else if (e.code === 'ArrowDown') {
      getInitVideo()
    }
    if (e.code === 'ArrowLeft' && !leftClick) {
      setLeftClick(true)
      getMesages()
    }
    console.log(e.code, leftClick)
    if (e.code === 'ArrowRight' && leftClick) {
      setLeftClick(false)
      getMesages()
    }
  }

  const getType = (type: number) => {
    return typeList.filter(item => item.value === type)[0].label
  }

  const changeLike = async () => {
    const id = videoInfo?.video.id
    if (id) {
      if (videoInfo?.is_like) {
        const res = await delLike(id)
        if (res?.code === 200) {
          setVideoInfo({ ...videoInfo, is_like: false, video: { ...videoInfo.video, likePoints: videoInfo.video.likePoints - 1 } })
        } else {
          message.info(res?.message)
        }
      } else {
        const res = await addLike(id)
        if (res?.code === 200) {
          setVideoInfo({ ...videoInfo, is_like: true, video: { ...videoInfo.video, likePoints: videoInfo.video.likePoints + 1 } })
        } else {
          message.info(res?.message)
        }
      }
    }
  }

  const changeCollect = async () => {
    const id = videoInfo?.video.id
    if (id) {
      if (videoInfo?.is_collect) {
        const res = await delCollect(id)
        if (res?.code === 200) {
          setVideoInfo({ ...videoInfo, is_collect: false, video: { ...videoInfo.video, collectPoints: videoInfo.video.collectPoints - 1 } })
        } else {
          message.info(res?.message)
        }
      } else {
        const res = await addCollect(id)
        if (res?.code === 200) {
          setVideoInfo({ ...videoInfo, is_collect: true, video: { ...videoInfo.video, collectPoints: videoInfo.video.collectPoints + 1 } })
        } else {
          message.info(res?.message)
        }
      }
    }
  }

  const changeFrd = async () => {
    const user_id = videoInfo?.user.id
    const frd = videoInfo?.is_friend
    if (user_id) {
      if (frd) {
        const res = await delFrd(user_id)
        if (res?.code === 200) {
          setVideoInfo({ ...videoInfo, is_friend: false })
        } else {
          message.info(res?.message)
        }
      } else {
        const res = await addFrd(user_id)
        if (res?.code === 200) {
          setVideoInfo({ ...videoInfo, is_friend: true })
        } else {
          message.info(res?.message)
        }
      }
    }
  }

  // 发布评论(对视频)
  const publishMessage = async () => {
    const data = messageInfo.trim()
    if (!data && !(videoInfo?.video.id)) return
    const res = await addcomment(videoInfo?.video.id as number, 0, data, 1)
    if (res?.code === 200) {
      message.success('发布成功')
      setMessageInfo('')
      getMesages()
    } else {
      message.info(res?.message)
    }
  }
  // 发布评论（对一级）
  const publishFirstMessage = async () => {
    const data = firstComment.trim()
    if (!data) return
    const res = await addcomment(videoInfo?.video.id as number, returnComment?.comment.id as number, data, 2)
    if (res?.code === 200) {
      message.success('发布成功')
      setFirstComment('')
      setUpdateFlag(true)
    } else {
      message.info(res?.message)
    }
  }

  // 获取一级评论
  const getMesages = async () => {
    if (!(videoInfo?.video.id)) return
    const res = await getfirstcomments(videoInfo?.video.id)
    if (res?.code === 200) {
      setComments(res.data)
    }
  }

  // 获得二级评论
  const getFirstComment = async (id: number) => {
    const res = await getsecondcomments(id)
    if (res?.code === 200) {
      return res.data
    }
  }

  useEffect(() => {
    // 监听键盘事件
    document.addEventListener("keyup", PopupKeyUp, false)
    return () => {
      // 销毁键盘事件
      document.removeEventListener("keyup", PopupKeyUp, false)
    }
  }, [leftClick])

  useEffect(() => {
    if (!leftClick) {
      setComments(undefined)
      setFirstComment('')
      setReturnComment(undefined)
      setMessageInfo('')
    }
  }, [leftClick])

  useEffect(() => {
    getInitVideo()
    return () => {
      clear()
    }
  }, [])

  const propsOption = { ...basicVideoInitOption, loop: true, autoplay: true, poster: videoInfo?.video.photoUrl ? videoInfo?.video.photoUrl : '' }
  return (
    <div className={style.back}>
      <div className={leftClick ? style.video_play : style.video_play_no_click}>
        <div className={style.video_header}>
          <Tag className={style.tag} color="#fa7132">{getType(videoInfo ? videoInfo?.video.type : 1)}</Tag>
          <span>{videoInfo?.video.title}</span>
        </div>
        <div className={style.video_box}>
          {
            videoInfo ? <VideoComponent videoId={videoInfo.video.id} propsOption={propsOption} videoUrl={videoInfo?.video.videoUrl}></VideoComponent> : <Mask />
          }
        </div>
        <div className={style.bottom_div}>
          <div className={style.right_div}>
            <div className={style.userInfo}>
              <div className={style.img_box} onClick={() => otherJump(videoInfo?.user.id)}>
                <img className={style.img} src={videoInfo?.user.photo}></img>
              </div>
              <div className={style.user_info} onClick={() => otherJump(videoInfo?.user.id)}>
                <div>{videoInfo?.user.nickname}</div>
                <div>{videoInfo?.user.email.length ? videoInfo?.user.email : ''}</div>
                <div>{videoInfo?.user.username}</div>
              </div>
              {
                videoInfo?.is_friend
                  ? <div className={style.delFrd_box} onClick={() => changeFrd()}>已关注</div>
                  : <div className={style.addFrd_box} onClick={() => changeFrd()}>点个关注</div>
              }
            </div>
            <div className={style.middle_div} title={videoInfo?.video.description}>
              {videoInfo?.video.description}
            </div>
          </div>
          <div className={style.left_div}>
            <div className={style.item} onClick={() => changeLike()} onMouseEnter={() => setHoverValue('like')} onMouseLeave={() => setHoverValue('')}>
              <img src={hoverValue === 'like' || videoInfo?.is_like ? likeIconClick : likeIcon} className={style.icon}></img>
              <span className={videoInfo?.is_like ? style.number_text_click : style.number_text}>{videoInfo?.video.likePoints}</span>
            </div>
            <div className={style.item} onClick={() => changeCollect()} onMouseEnter={() => setHoverValue('collect')} onMouseLeave={() => setHoverValue('')}>
              <img src={hoverValue === 'collect' || videoInfo?.is_collect ? collectClickIcon : collectIcon} className={style.icon}></img>
              <span className={videoInfo?.is_collect ? style.number_text_click : style.number_text}>{videoInfo?.video.collectPoints}</span>
            </div>
            <div className={style.item} onMouseEnter={() => setHoverValue('view')} onMouseLeave={() => setHoverValue('')}>
              <img src={hoverValue === 'view' ? viewIconClick : viewIcon} className={style.icon}></img>
              <span className={style.number_text}>{videoInfo?.video.viewsPoints}</span>
            </div>
          </div>
        </div>
        <div className={style.btns}>
          <div className={style.btn_box}>
            <img src={topIcon} onClick={() => getFormer()} className={style.changeIcon} />
            <img src={bottomIcon} onClick={() => getInitVideo()} className={style.changeIcon} />
          </div>
        </div>
        <div className={style.right_click_btn} onClick={() => { setLeftClick(!leftClick); getMesages() }}>
          <img src={rightIcon} className={!leftClick ? style.rightIcon : style.rightIcon_click}></img>
        </div>
      </div>
      {
        leftClick
          ? <div className={style.right_box}>
            <div className={style.video_right}>
              <Input.TextArea value={messageInfo} className={style.input_style} onChange={(e) => setMessageInfo(e.target.value)} showCount maxLength={1000} placeholder='发条评论吧'></Input.TextArea>
              <div className={style.comment_div} onClick={() => publishMessage()}>发布</div>
            </div>
            <div className={style.commentDiv}>
              <div className={style.comment_auto}>
                {
                  comments?.length
                    ? <>
                      {
                        comments.map(item => <Comment getMesages={getMesages} videoId={videoInfo?.video.id} updateFlag={updateFlag} setUpdateFlag={setUpdateFlag} getFirstComment={getFirstComment} setReturnComment={setReturnComment} key={item.comment.commentId} item={item} />)
                      }
                    </>
                    : <div className={style.no_comment}>还没有任何评论~</div>}
              </div>
            </div>
            {
              returnComment
                ? <div className={style.input2_box}>
                  <Input.TextArea value={firstComment} onChange={(e) => setFirstComment(e.target.value)} className={style.input_style} showCount maxLength={1000} placeholder={`回复${returnComment.user.nickname}`}></Input.TextArea>
                  <div className={style.comment_div} onClick={() => publishFirstMessage()}>发布</div>
                </div>
                : ''
            }
          </div>
          : ''
      }
    </div >
  )
}
