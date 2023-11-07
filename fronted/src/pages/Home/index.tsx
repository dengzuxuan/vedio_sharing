import React, { useContext, useEffect, useState } from 'react'
import style from './index.module.scss'
import { tabs_one, tabs_two } from '../../libs/data'
import chaIcon from '../../assets/imgs/cha.png'
import searchIcon from '../../assets/imgs/search.png'
import CallIcon from '../../assets/imgs/call.png'
import { Outlet, useNavigate } from 'react-router-dom'
import { context } from '../../hooks/store'
import { getmessage, getnotread, searchApi, userInfo } from '../../api/personal'
import { type IGetMsg, type IGetInfo, type ISearch } from '../../libs/model'
import { Popover, Select } from 'antd'
import dayjs from 'dayjs'
import useJump from '../../hooks/useJump'
import exitIcon from '../../assets/imgs/exit.png'

export default function Home() {
  const { jump } = useJump()
  const navigator = useNavigate()
  // 控制左tabs高亮
  const { clickItemValue, setClickItemValue } = useContext(context)

  // 保存搜索框内容
  const [searchValue, setSearchValue] = useState('')
  // 保存个人信息
  const [info, setInfo] = useState<IGetInfo>()
  // 保存未读数量
  const [noNum, setNoNum] = useState<number>()
  // 保存搜索type
  const [type, setType] = useState(0)
  // 保存通知信息
  const [msgs, setMsgs] = useState<IGetMsg[]>()
  // 保存搜索内容
  const [searchItem, setSearchItem] = useState<ISearch>()

  // 控制搜索框×
  const search = (value: string) => {
    setSearchValue(value)
  }

  // 获取本人信息
  const getSelfInfo = async () => {
    const res = await userInfo()
    if (res?.code === 200) {
      setInfo(res.data)
    }
  }

  // 获取未读数目
  const getNumber = async () => {
    const res = await getnotread()
    setNoNum(res?.data)
  }

  // 获得通知消息
  const getMsg = async () => {
    const res = await getmessage(type)
    if (res?.code === 200) {
      setMsgs(res.data)
    }
  }

  useEffect(() => {
    getMsg()
  }, [type])

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (!token) {
      navigator('/login')
    }
  }, [clickItemValue])

  const renderType = (item: IGetMsg) => {
    const type = item.message.type
    switch (type) {
      case 1:
        return '已关注你'
      case 2:
        return item.message.content
      case 3:
        return '点赞了你'
      case 4:
        return '收藏了你的视频'
    }
  }

  const content = (
    <div className={style.content}>
      <div>
        <Select
          style={{ width: '100px' }}
          defaultValue={type}
          onChange={(value) => setType(value)}
          optionFilterProp="children"
          options={[
            {
              value: 0,
              label: '全部消息'
            },
            {
              value: 1,
              label: '关注'
            },
            {
              value: 2,
              label: '评论'
            },
            {
              value: 3,
              label: '喜爱'
            },
            {
              value: 4,
              label: '收藏'
            }
          ]}
        ></Select>
      </div>
      <div className={style.hoverMain}>
        {
          msgs?.map(item => <div key={item.message.id} className={style.item}>
            <div className={style.img_box}>
              <img className={style.img} src={item.user.photo}></img>
            </div>
            <div className={style.info}>
              <div>{item.user.nickname}</div>
              <div className={style.contentMsg}>{renderType(item)}</div>
              <div>{'已回复你的评论' + item.message.pre + '  ' + dayjs(item.message.createTime).format('MM-DD')}</div>
            </div>
          </div>)
        }
      </div>
    </div>
  )

  const clickSearch = async () => {
    if (!searchValue) return
    const res = await searchApi(searchValue)
    if (res?.code === 200) {
      setSearchItem(res.data)
    }
  }

  const searchContent = (
    <div className={style.searchContent}>
      {
        searchItem?.videoDetail.length ? <div>视频</div> : ''
      }
      <div className={style.video_box}>
        {
          searchItem?.videoDetail.map(item => <div onClick={() => jump(item.video.id)} key={item.video.id} className={style.item}>
            <div className={style.img}>
              <img className={style.img_} src={item.video.photoUrl}></img>
            </div>
            <div className={style.content}>
              <div className={style.title} title={item.video.title}>{item.video.title}</div>
              <div className={style.userInfo}>
                <span className={style.time}>{dayjs(item.video.createTime).format('YY-DD')}</span>
                <span>{item.user.nickname}</span>
              </div>
            </div>
          </div>)
        }
      </div>
      {
        searchItem?.userList.length ? <div>用户</div> : ''
      }
      <div className={style.user_box}>
        {
          searchItem?.userList.map(item => <div onClick={() => navigator(`user/${item.id}`)} key={item.id} className={style.item}>
            <div className={style.img}>
              <img className={style.img_} src={item.photo}></img>
            </div>
            <div className={style.nickname} title={item.nickname}>{item.nickname}</div>
          </div>)
        }
      </div>
    </div>
  )

  const exitClick = () => {
    localStorage.clear()
    navigator('/login')
  }

  useEffect(() => {
    navigator(`/home/${clickItemValue}`)
  }, [clickItemValue])

  useEffect(() => {
    getSelfInfo()
    getNumber()
  }, [])

  return (
    <div className={style.back}>
      <header className={style.header}>
        <div className={style.icon_div}>
          <svg width="78" height="22" viewBox="0 0 78 22" fill="none">
            <mask id="logo_svg__a" style={{ maskType: 'alpha' }} maskUnits="userSpaceOnUse" x="0" y="0" width="31" height="21">
              <path d="M0 .08h30.52v20.33H0V.08z" fill="#fff"></path>
            </mask>
            <g mask="url(#logo_svg__a)">
              <path fillRule="evenodd" clipRule="evenodd" d="M30.52.316c-.513-.177-.938.038-1.182.221A18.392 18.392 0 0115.26 7.071c-1.94 0-3.809-.3-5.565-.855l-.78-2.837s-.38-1.156-1.665-.958l.37 2.995A18.495 18.495 0 011.182.536C.94.355.515.14 0 .317A16.343 16.343 0 008.092 9.24l1.013 8.202s.452 3.138 3.397 3.138H18.8c2.944 0 3.397-3.138 3.397-3.138l.712-5.878c-1.912-.155-3.115 1.177-3.496 2.492-.64 2.214-.64 2.356-.766 2.742-.258.79-1.107.885-1.107.885h-3.778s-.849-.094-1.107-.885c-.167-.51-.998-3.488-1.835-6.51 1.412.399 2.901.613 4.44.613 6.99 0 12.949-4.403 15.26-10.585z" fill="#fa7132"></path>
            </g>
            <path fillRule="evenodd" clipRule="evenodd" d="M77.835 12.624v-2.203H64.11v2.203h2.525l-1.952 4.908h-.034v2.203h11.743c.737-.053 1.4-.709 1.4-1.388 0-.338-.109-.723-.132-.8h.002l-.994-2.738h-2.43l.988 2.723h-8.16l1.952-4.908h8.816zm-21.374-3.3h4.893v-2.15h-4.893v-2.18h-2.203v2.18h-3.384l.283-1.616h-2.303l-1.176 6.729h.936c.312-.031 1.359-.23 1.624-1.476l.26-1.487h3.76v6.563h-6.57v2.203h6.57v2.167h2.203V18.09h5.449v-2.203h-5.449V9.324zM35.713 5.017H33.51v2.147H31.32v2.22h2.189v8.967c.062.74.656 1.33 1.398 1.384h8.653a1.518 1.518 0 001.447-1.428v-3.505H42.81v2.678h-7.096V9.385h9.826v-2.22h-9.827V5.016zm28.97 2.664h12.553v-2.15H64.684v2.15z" fill="#fa7132"></path>
          </svg>
        </div>
        <div className={style.input_box}>
          <div className={style.left_box}>
            <input value={searchValue} className={style.input} onChange={(e) => search(e.target.value)}></input>
            {
              searchValue.length
                ? <img onClick={() => setSearchValue('')} src={chaIcon} className={style.chaIcon}></img>
                : ''
            }
          </div>
          <Popover placement="bottomRight" trigger="click" content={searchContent}>
            <img src={searchIcon} onClick={() => clickSearch()} className={style.searchIcon}></img>
          </Popover>
        </div>
        <div className={style.right}>
          <Popover content={content} title="互动消息">
            <div className={style.callDiv}>
              {
                noNum ? <div className={style.callRed}>12</div> : ''
              }
              <img src={CallIcon} className={style.call}></img>
            </div>
          </Popover>
          <div className={style.personBox}>
            <img src={info?.photo} className={style.personImg} onClick={() => { setClickItemValue('my') }}></img>
          </div>
          <img src={exitIcon} className={style.exit}></img>
        </div>
      </header>
      <main className={style.main}>
        <div className={style.aside}>
          {
            tabs_one.map(item =>
              <div
                key={item.value}
                className={clickItemValue === item.value ? style.typeItemClick : style.typeItem}
                onClick={() => setClickItemValue(item.value)}
              >
                <img src={item.src} className={style.item_icon}></img>
                <div className={style.text}>{item.text}</div>
              </div>
            )
          }
          <div className={style.partition}></div>
          {
            tabs_two.map((item, index) =>
              <div
                key={index}
                className={clickItemValue === item.value ? style.typeItemClick : style.typeItem}
                onClick={() => setClickItemValue('channel/' + item.value)}
              >
                <img src={item.src} className={style.item_icon}></img>
                <div className={style.text} onClick={() => exitClick()}>{item.text}</div>
              </div>
            )
          }
        </div>
        <div className={style.main_info}>
          <Outlet></Outlet>
        </div>
      </main >
    </div >
  )
}
