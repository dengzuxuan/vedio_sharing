import React from 'react'
import style from './index.module.scss'
import { Spin } from 'antd'

export default function Mask () {
  return (
    <div className={style.back}>
      <Spin></Spin>
    </div>
  )
}
