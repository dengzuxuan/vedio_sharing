import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import style from './index.module.scss'
import { basicVideoInitOption } from '../../libs/data'
import { Carousel } from 'antd'
import likeIcon from '../../assets/imgs/likepoints.png'
import { gettypedayrank, gettypemonthrank, gettypevideos, gettypeweekrank } from '../../api/personal'
import { type IGetTypeVideos } from '../../libs/model'
import VideoComponent from '../../components/VideoComponent'
import dayjs from 'dayjs'
import useJump from '../../hooks/useJump'

export default function Channel() {
  const { jump } = useJump()
  const type = useLocation().pathname.split('/')[3]
  // 保存视频
  const [videosInfo, setVideoInfos] = useState<IGetTypeVideos[]>()
  // 保存榜单
  const [rankInfo, setRankInfo] = useState<IGetTypeVideos[]>()
  // 保存casual
  const [casualInfo, setCasualInfo] = useState<IGetTypeVideos[][]>()
  // 保存点击榜单
  const [tabs, setTabs] = useState<string>()
  const option = {
    ...basicVideoInitOption,
    controls: true
  }

  // 获得视频
  const getVideos = async () => {
    const res = await gettypevideos(parseInt(type))
    if (res?.code === 200) {
      setVideoInfos(res.data)
    }
  }

  const getCasual = async () => {
    const res = await gettypedayrank(parseInt(type))
    if (res?.code === 200) {
      const data = res.data.slice(0, 9)
      const len = data.length
      const temp = []
      let arr: IGetTypeVideos[] = []
      for (let i = 1; i < len + 1; i++) {
        arr.push(data[i - 1])
        if (i % 3 === 0) {
          temp.push(arr)
          arr = []
        }
      }
      setCasualInfo(temp)
    }
  }

  const getRankInfo = async () => {
    let res
    if (tabs === 'day') {
      res = await gettypedayrank(parseInt(type))
    } else if (tabs === 'week') {
      res = await gettypeweekrank(parseInt(type))
    } else if (tabs === 'month') {
      res = await gettypemonthrank(parseInt(type))
    }
    if (res?.code === 200) {
      setRankInfo(res.data)
    }
  }

  useEffect(() => {
    getRankInfo()
  }, [tabs, type])

  useEffect(() => {
    getVideos()
    setTabs('day')
    getCasual()
  }, [type])
  return (
    <div className={style.back}>
      <div className={style.carousel_div}>
        <Carousel autoplay className={style.box}>
          {
            casualInfo?.map((item, index) => <div key={index} style={{ display: 'flex' }} className={style.img_box}>
              {
                item.map(item2 => <div key={item2.video.id} className={style.img3}>
                  <img onClick={() => jump(item2.video.id)} src={item2.video.photoUrl} className={style.carousel}></img>
                </div>)
              }
            </div>)
          }
        </Carousel>
      </div>
      <div className={style.main}>
        <div className={style.video_boxs}>
          <div className={style.change} onClick={() => getVideos()}>换一换</div>
          <div className={style.videos_box}>
            {
              videosInfo?.map(item =>
                <div key={item.video.id} className={style.video_item} onClick={() => jump(item.video.id)}>
                  <div className={style.video_div}>
                    <VideoComponent videoId={item.video.id} propsOption={{ ...option, loop: true, poster: item.video.photoUrl ?? '' }} hoverFunc={true} videoUrl={item.video.videoUrl ?? ''}></VideoComponent>
                  </div>
                  <div className={style.title_description} title={item.video.description}>
                    <span className={style.description}>
                      {
                        item.video.description
                      }
                    </span>
                  </div>
                  <div className={style.up_div}>
                    <div className={style.nickname}>{item.user.nickname}</div>
                    <div>{dayjs(item.video.createTime).format('MM-DD')}</div>
                    <div className={style.likes}>
                      <img src={likeIcon} className={style.likeIcon}></img>
                      <span>{item.video.likePoints}</span>
                    </div>
                  </div>
                </div>)
            }
            <div className={style.video_item}></div>
            <div className={style.video_item}></div>
            <div className={style.video_item}></div>
            <div className={style.video_item}></div>
          </div>
        </div>
        <div className={style.aside}>
          <div className={style.head}>
            <span className={tabs === 'day' ? style.span_click : style.span} onClick={() => setTabs('day')}>日榜</span>
            <span className={tabs === 'week' ? style.span_click : style.span} onClick={() => setTabs('week')}>周榜</span>
            <span className={tabs === 'month' ? style.span_click : style.span} onClick={() => setTabs('month')}>月榜</span>
          </div>
          {
            rankInfo?.map((item, index) => <div key={item.video.id} className={style.rank_div}>
              <div className={style.rank_item}>
                <span className={style.number}>{index + 1}</span>
                <span onClick={() => jump(item.video.id)} className={style.text} title={item.video.description}>{item.video.description}</span>
              </div>
            </div>)
          }
        </div>
      </div>
    </div>
  )
}
