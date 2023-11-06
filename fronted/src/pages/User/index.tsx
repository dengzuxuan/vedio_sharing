import React, { useEffect, useState } from 'react'
import style from './index.module.scss'
import { Image } from 'antd'
import { getFrdInfo, getSendFrd, getothercollectvideos, getotherlikevideos, otheruserinfo, otheruservideo } from '../../api/personal'
import { type IVideoInfo, type IFrd, type IGetOtherInfo } from '../../libs/model'
import manIcon from '../../assets/imgs/man.png'
import womanIcon from '../../assets/imgs/woman.png'
import VideoComponent from '../../components/VideoComponent'
import { basicVideoInitOption } from '../../libs/data'
import { useLocation } from 'react-router-dom'
import lockIcon from '../../assets/imgs/lock.png'
import useJump from '../../hooks/useJump'
interface IFrdInfo {
  frds: IFrd[]
  tabs: string
}
export default function User() {
  const { jump } = useJump()
  const id = localStorage.getItem('id')
  const user_id = useLocation().pathname.split('/')[3]
  const [clickTabs, setClickTabs] = useState('work')
  const [Info, setInfo] = useState<IGetOtherInfo>()
  const [selfVideo, setSelfVideo] = useState<IVideoInfo[]>([])
  const [frd, setFrd] = useState<IFrdInfo>({ frds: [], tabs: '' })

  const getOtherInfo = async () => {
    const res = await otheruserinfo(parseInt(user_id))
    if (res?.code === 200) {
      setInfo(res.data)
    }
  }

  // 获取个人作品
  const getVideos = async (clickTabs: string) => {
    let res
    switch (clickTabs) {
      case 'work':
        res = await otheruservideo(parseInt(user_id))
        break
      case 'good':
        res = await getotherlikevideos(parseInt(user_id))
        break
      case 'collect':
        res = await getothercollectvideos(parseInt(user_id))
        break
      default:
        console.log('')
    }
    if (res?.code === 200) {
      setSelfVideo(res.data)
    }
  }

  const getFriendInfo = async () => {
    let res
    if (frd?.tabs === 'sendFrd') {
      res = await getSendFrd()
    } else if (frd?.tabs === 'frd') {
      res = await getFrdInfo()
    }
    if (res?.code) {
      frd && setFrd({ ...frd, frds: res.data })
    }
  }

  // 是否出现关注按钮
  const showBtn = () => {
    if (!id) return null
    if (parseInt(id) === Info?.user.id) return null
  }

  useEffect(() => {
    getFriendInfo()
  }, [frd?.tabs])

  useEffect(() => {
    getVideos(clickTabs)
  }, [clickTabs])

  useEffect(() => {
    getOtherInfo()
    getFriendInfo()
  }, [user_id])

  const option = {
    ...basicVideoInitOption,
    controls: true
  }
  return (
    <div className={style.back}>
      <div className={style.self_info}>
        <div className={style.img_box}>
          <Image
            width={120}
            src={Info?.user.photo}
          />
        </div>
        <div className={style.info_box}>
          <div className={style.name_text}>{Info?.user.nickname}</div>
          <div className={style.user_info}>
            <div className={style.item_info}>
              <span className={style.text}>关注</span>
              <span className={style.number}>{Info?.user.sendFriends}</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>粉丝</span>
              <span className={style.number}>{Info?.user.friends}</span>
            </div>
            <div className={style.item_info}>
              <span className={style.text}>获赞</span>
              <span className={style.number}>{Info?.user.likes}</span>
            </div>
          </div>
          <div className={style.username_info}>
            <div>
              <span>账号：</span>
              <span>{Info?.user.username}</span>
            </div>
            <div><img src={Info?.user.sexual === 0 ? manIcon : womanIcon} className={style.sexIcon}></img></div>
          </div>
        </div>
        <div>
          {
            showBtn()
          }
        </div>
      </div>
      <div className={style.tabs}>
        <div className={clickTabs === 'work' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('work')}>
          <span className={style.tabs_text}>作品</span>
          <span className={style.number}>{Info?.user.videos}</span>
        </div>
        {
          Info?.user.likeHidden === 0
            ? <div className={clickTabs === 'good' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('good')}>
              <span className={style.tabs_text}>喜爱</span>
              <span className={style.number}>{Info?.user.likes}</span>
            </div>
            : <div className={style.tabs_item}>
              <span className={style.tabs_text}>喜爱</span>
              <img className={style.lockIcon} src={lockIcon}></img>
            </div>
        }
        {
          Info?.user.collectHidden === 0
            ? <div className={clickTabs === 'collect' ? style.tabs_itemClick : style.tabs_item} onClick={() => setClickTabs('collect')}>
              <span className={style.tabs_text}>收藏</span>
              <span className={style.number}>{Info?.user.sendCollects}</span>
            </div>
            : <div className={style.tabs_item}>
              <span className={style.tabs_text}>收藏</span>
              <img className={style.lockIcon} src={lockIcon}></img>
            </div>
        }
      </div>
      <div className={style.video_box}>
        {
          selfVideo.length
            ? selfVideo.map(item =>
              <div key={item.id} className={style.video_item} onClick={() => jump(item.id)}>
                <div className={style.video_div}>
                  <VideoComponent propsOption={{ ...option, loop: true, poster: item.photoUrl }} hoverFunc={true} videoUrl={item.videoUrl}></VideoComponent>
                </div>
                <div className={style.title_description} title={item.description}>
                  <span className={style.title}>{item.title}</span>
                  <span className={style.description}>
                    {
                      item.description
                    }
                  </span>
                </div>
              </div>)
            : ''
        }
      </div>
      {selfVideo.length === 0 ? <div className={style.nomore}>没有更多了~</div> : ''}
    </div>
  )
}
