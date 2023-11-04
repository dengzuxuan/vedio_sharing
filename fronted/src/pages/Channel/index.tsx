import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import style from './index.module.scss'
import { basicVideoInitOption } from '../../libs/data'
import VideoComponent from '../../components/VideoComponent'
import { message } from 'antd'
import { getVideo } from '../../api/recommend'
import { type IGetVideo } from '../../libs/model'

export default function Channel() {
  const [videoInfo, setVideoInfo] = useState<IGetVideo>()
  const type = useLocation().pathname.split('/')[3]
  const option = {
    ...basicVideoInitOption,
    controls: true
  }

  // 获得初始视频
  const getInitVideo = async () => {
    const res = await getVideo()
    if (res?.code === 200) {
      setVideoInfo(res.data)
    } else {
      message.info(res?.message)
    }
  }

  useEffect(() => {
    getInitVideo()
  }, [])
  return (
    <div className={style.back}>
      <div className={style.videos_box}>
        <div className={style.video_item}>
          <div className={style.video_div}>
            <VideoComponent propsOption={{ ...option, loop: true, poster: videoInfo?.video.photoUrl ?? '' }} hoverFunc={true} videoUrl={videoInfo?.video.videoUrl ?? ''}></VideoComponent>
          </div>
          <div className={style.title_description} title={videoInfo?.video.description}>
            <span className={style.title}>{videoInfo?.video.title}</span>
            <span className={style.description}>
              {
                videoInfo?.video.description
              }
            </span>
          </div>
        </div>
        <div className={style.video_item}></div>
        <div className={style.video_item}></div>
        <div className={style.video_item}></div>
        <div className={style.video_item}></div>
      </div>
    </div>
  )
}
