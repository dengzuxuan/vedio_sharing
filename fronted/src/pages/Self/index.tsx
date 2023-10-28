import React from 'react'
import style from './index.module.scss'
import selfTest from '../../assets/imgs/person.webp'
import { Image } from 'antd'
export default function Self() {
  return (
    <div>
      <div className={style.self_info}>
        <div className={style.img_box}>
          <Image
            width={100}
            src={selfTest}
          />
        </div>
      </div>
    </div>
  )
}
