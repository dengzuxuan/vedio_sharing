import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import style from './index.module.scss'
import { basicVideoInitOption } from '../../libs/data'
import VideoComponent from '../../components/VideoComponent'
import { Carousel, message } from 'antd'
import { getVideo } from '../../api/recommend'
import { type IGetVideo } from '../../libs/model'
import casual2 from '../../assets/imgs/casual2.webp'
import casual3 from '../../assets/imgs/casual3.webp'
import casual4 from '../../assets/imgs/casual4.webp'
import casual5 from '../../assets/imgs/casual5.webp'
import { gettypevideo } from '../../api/personal'

export default function Channel() {
  const type = useLocation().pathname.split('/')[3]
  const option = {
    ...basicVideoInitOption,
    controls: true
  }

  // 获得视频
  const getVideos = async () => {
    const res = await gettypevideo(parseInt(type))
    if (res?.code === 200) {
      console.log(res.data)
    }
  }

  useEffect(() => {
    getVideos()
  }, [])
  return (
    <div className={style.back}>
      <div className={style.carousel_div}>
        <Carousel autoplay className={style.box}>
          <div className={style.img_box}>
            <img src={casual2} className={style.carousel}></img>
          </div>
          <div className={style.img_box}>
            <img src={casual3} className={style.carousel}></img>
          </div>
          <div className={style.img_box}>
            <img src={casual4} className={style.carousel}></img>
          </div>
          <div className={style.img_box}>
            <img src={casual5} className={style.carousel}></img>
          </div>
        </Carousel>
      </div>
      <div className={style.main}>
        <div className={style.videos_box}>
          <div className={style.video_item}>
            {/* <div className={style.video_div}>
              <VideoComponent propsOption={{ ...option, loop: true, poster: videoInfo?.video.photoUrl ?? '' }} hoverFunc={true} videoUrl={videoInfo?.video.videoUrl ?? ''}></VideoComponent>
            </div>
            <div className={style.title_description} title={videoInfo?.video.description}>
              <span className={style.title}>{videoInfo?.video.title}</span>
              <span className={style.description}>
                {
                  videoInfo?.video.description
                }
              </span>
            </div> */}
          </div>
          <div className={style.video_item}></div>
          <div className={style.video_item}></div>
          <div className={style.video_item}></div>
          <div className={style.video_item}></div>
        </div>
        <div className={style.aside}>
          <div className={style.head}>榜单</div>
          <div className={style.rank_div}>
            <div className={style.rank_item}>
              <span className={style.number}>1</span>
              <span className={style.text} title='大仙体验游戏“完蛋，我被美女包围了”！！'>大仙体验游戏“完蛋，我被美女包围了”！！</span>
            </div>
          </div>
          <div className={style.rank_div}>
            <div className={style.rank_item}>
              <span className={style.number}>2</span>
              <span className={style.text} title='大仙体验游戏“完蛋，我被美女包围了”！！'>大仙体验游戏“完蛋，我被美女包围了”！！</span>
            </div>
          </div>
          <div className={style.rank_div}>
            <div className={style.rank_item}>
              <span className={style.number}>3</span>
              <span className={style.text} title='大仙体验游戏“完蛋，我被美女包围了”！！'>大仙体验游戏“完蛋，我被美女包围了”！！</span>
            </div>
          </div>
          <div className={style.rank_div}>
            <div className={style.rank_item}>
              <span className={style.number}>4</span>
              <span className={style.text} title='大仙体验游戏“完蛋，我被美女包围了”！！'>大仙体验游戏“完蛋，我被美女包围了”！！</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
