import React, { useState, useRef, useEffect } from 'react'
import style from './index.module.scss'
import videojs from "video.js"
import "video.js/dist/video-js.css"
import { basicVideoInitOption } from '../../libs/data'
import VideoComponent from '../../components/VideoComponent'
const videoUrl = 'https://live-s3m.mediav.com/nativevideo/2a80d171cc7ef2c764c9a83c06e0e4bc-bit_cloud768.mp4'

export default function recommend() {
  const propsOption = { ...basicVideoInitOption, loop: true }
  return (
    <div className={style.back}>
      <div className={style.video_play}>
        <VideoComponent propsOption={basicVideoInitOption} videoUrl={videoUrl}></VideoComponent>
      </div>
    </div>
  )
}
