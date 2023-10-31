import React, { useState, useRef, useEffect, useCallback } from 'react'
import style from './index.module.scss'
import videojs from "video.js"
import "video.js/dist/video-js.css"
import { basicVideoInitOption } from '../../libs/data'
import { type IVideo } from '../../libs/model'

interface Props {
  propsOption: IVideo
  videoUrl: string
}

export default function VideoComponent({ propsOption, videoUrl }: Props) {
  const videoRef = useRef(null)
  const playerRef = useRef<any>(null)
  const [option, setOption] = useState(propsOption)

  const onReadyPlay = (palyer: any) => {
    videoRef.current = palyer
    palyer.play()
  }

  const handleUpdate = useCallback(() => {
    const player = playerRef.current
    // window.document.fullscreenElement检测视频是否正在全屏
    // console.log('播放中，当前时间是', player.currentTime())
    if (player.currentTime() > 10) {
      if (window.document.fullscreenElement) {
        // 如果是全屏 退出全屏
        window.document.exitFullscreen()
      }
      player.currentTime(10)
      player.pause()
    }
  }, [])

  useEffect(() => {
    let player: any
    if (!playerRef.current) {
      const videoElement = videoRef.current
      if (!videoElement) return
      player = playerRef.current = videojs(videoElement, option, () => {
      })
      onReadyPlay(player)
    }

    return () => {
      player.on('timeupdate', handleUpdate)
    }
  }, [])
  return (
    <video style={{
      width: '100p%',
      height: '100%'
    }} ref={videoRef}
      className="video-js vjs-big-play-centered">
      <source src={videoUrl} type="video/mp4" />
    </video>
  )
}
