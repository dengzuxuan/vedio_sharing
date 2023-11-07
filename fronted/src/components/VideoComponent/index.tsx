import React, { useState, useRef, useEffect, useCallback } from 'react'
import style from './index.module.scss'
import videojs from "video.js"
import "video.js/dist/video-js.css"
import { type IVideo } from '../../libs/model'
import type Player from 'video.js/dist/types/player'
import { judge } from '../../api/personal'

interface Props {
  propsOption: IVideo
  videoUrl: string
  hoverFunc?: boolean
  videoId?: number
}

export default function VideoComponent({ propsOption, videoUrl, hoverFunc, videoId }: Props) {
  const judgeClick = async (flag: boolean, flag2: boolean, flag4: boolean) => {
    await judge(videoId ?? 0, flag, flag2, flag4)
  }
  const videoRef = useRef(null)
  const playerRef = useRef<any>(null)
  let flag = false
  let flag2 = false
  let flag4 = false

  const onReadyPlay = (player: any) => {
    videoRef.current = player
    player.play()
  }

  useEffect(() => {
    let player = playerRef.current
    const videoElement = videoRef.current
    if (!videoElement) return
    videojs.addLanguage('zh-CN', require('video.js/dist/lang/zh-CN.json'))
    player = playerRef.current = videojs(videoElement, propsOption, () => { })
    if (hoverFunc) {
      hoverFunc && player.on('mouseover', () => player.play())
      hoverFunc && player.on('mouseout', () => player.pause())
    } else {
      onReadyPlay(player)
      if (videoId) {
        player.on('timeupdate', () => {
          // 获取视频当前时间和视频总时长
          const currentTime = player.currentTime()
          const duration = player.duration()
          if (!currentTime || !duration) return
          // 如果视频已经播放到四分之一
          if (currentTime >= duration / 4 && !flag4) {
            judgeClick(false, false, true)
            flag4 = true
          }
          // 如果视频已经播放到一半
          if (currentTime >= duration / 2 && !flag2) {
            judgeClick(false, true, true)
            flag2 = true
          }
          // 如果视频已经播放到全部
          if (currentTime >= duration * 0.98 && !flag) {
            judgeClick(true, true, true)
            flag = true
          }
        })
      }
    }

    return () => {
      if (player) {
        player.dispose()
        playerRef.current = null
      }
    }
  }, [videoRef])

  return (
    <div className={style.video_div}>
      <video style={{
        width: '100%',
        height: '100%'
      }}
        ref={videoRef}
        className="video-js vjs-big-play-centered"
        src={videoUrl}
      >
      </video>
    </div>
  )
}
