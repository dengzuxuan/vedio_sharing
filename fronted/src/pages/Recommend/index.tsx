import React, { useState, useEffect } from 'react'
import style from './index.module.scss'
import "video.js/dist/video-js.css"
import { basicVideoInitOption, typeList } from '../../libs/data'
import VideoComponent from '../../components/VideoComponent'
import { Input, Tag, message } from 'antd'
import { getVideo } from '../../api/recommend'
import { type IGetVideo } from '../../libs/model'
import likeIcon from '../../assets/imgs/likepoints.png'
import likeIconClick from '../../assets/imgs/like_click.png'
import collectIcon from '../../assets/imgs/collect.png'
import collectClickIcon from '../../assets/imgs/collect_click.png'
import viewIcon from '../../assets/imgs/viewPoints.png'
import viewIconClick from '../../assets/imgs/view_click.png'
import commentIcon from '../../assets/imgs/comment.png'
import topIcon from '../../assets/imgs/top.png'
import bottomIcon from '../../assets/imgs/bottom.png'
import rightIcon from '../../assets/imgs/left.png'
import Mask from '../../components/Mask'

export default function recommend() {
  const [videoInfo, setVideoInfo] = useState<IGetVideo>()
  const [hoverValue, setHoverValue] = useState('')
  // 控制左拉
  const [leftClick, setLeftClick] = useState(false)
  // 获得初始视频
  const getInitVideo = async () => {
    const res = await getVideo()
    if (res?.code === 200) {
      setVideoInfo(res.data)
    } else {
      message.info(res?.message)
    }
  }

  const getType = (type: number) => {
    return typeList.filter(item => item.value === type)[0].label
  }

  useEffect(() => {
    getInitVideo()
  }, [])
  const propsOption = { ...basicVideoInitOption, loop: true, poster: videoInfo?.video.photoUrl ? videoInfo?.video.photoUrl : '' }
  return (
    <div className={style.back}>
      <div className={leftClick ? style.video_play : style.video_play_no_click}>
        <div className={style.video_header}>
          <Tag className={style.tag} color="#fa7132">{getType(videoInfo ? videoInfo?.video.type : 1)}</Tag>
          <span>{videoInfo?.video.title}</span>
        </div>
        <div className={style.video_box}>
        {
          videoInfo ? <VideoComponent propsOption={propsOption} videoUrl={videoInfo?.video.videoUrl}></VideoComponent> : <Mask/>
        }
        </div>
        <div className={style.bottom_div}>
          <div className={style.right_div}>
            <div className={style.userInfo}>
              <div className={style.img_box}>
                <img className={style.img} src={videoInfo?.user.photo}></img>
              </div>
              <div className={style.user_info}>
                <div>{videoInfo?.user.nickname}</div>
                <div>{videoInfo?.user.email.length ? videoInfo?.user.email : ''}</div>
                <div>{videoInfo?.user.username}</div>
              </div>
              <div className={style.addFrd_box}>点个关注</div>
            </div>
            <div className={style.middle_div} title={videoInfo?.video.description}>
              {videoInfo?.video.description}
            </div>
          </div>
          <div className={style.left_div}>
            <div className={style.item} onMouseEnter={() => setHoverValue('like')} onMouseLeave={() => setHoverValue('')}>
              <img src={hoverValue === 'like' || videoInfo?.is_like ? likeIconClick : likeIcon} className={style.icon}></img>
              <span className={style.number_text}>{videoInfo?.video.likePoints}</span>
            </div>
            <div className={style.item} onMouseEnter={() => setHoverValue('collect')} onMouseLeave={() => setHoverValue('')}>
              <img src={hoverValue === 'collect' || videoInfo?.is_collect ? collectClickIcon : collectIcon} className={style.icon}></img>
              <span className={style.number_text}>{videoInfo?.video.collectPoints}</span>
            </div>
            <div className={style.item} onMouseEnter={() => setHoverValue('view')} onMouseLeave={() => setHoverValue('')}>
              <img src={hoverValue === 'view' ? viewIconClick : viewIcon} className={style.icon}></img>
              <span className={style.number_text}>{videoInfo?.video.viewsPoints}</span>
            </div>
          </div>
        </div>
        <div className={style.btns}>
          <div className={style.btn_box}>
            <img src={topIcon} className={style.changeIcon} />
            <img src={bottomIcon} className={style.changeIcon} />
          </div>
        </div>
        <div className={style.right_click_btn} onClick={() => setLeftClick(!leftClick)}>
          <img src={rightIcon} className={!leftClick ? style.rightIcon : style.rightIcon_click}></img>
        </div>
      </div>
      {
        leftClick
          ? <div className={style.video_right}>
            {/* <div className={style.video_block}>
              <div className={style.comment_block}>
                <img src={commentIcon} className={style.commentIcon}></img>
                <span>{videoInfo?.video.commentPoints}</span>
              </div>
            </div> */}
            <Input.TextArea className={style.input_style} showCount maxLength={1000} placeholder='发条评论吧'></Input.TextArea>
            <div className={style.comment_div}>发布</div>
          </div>
          : ''
      }
    </div >
  )
}
