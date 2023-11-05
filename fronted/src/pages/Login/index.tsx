import React, { useEffect } from 'react'
import BackgroundImg from '../../assets/imgs/background.png'
import style from './index.module.scss'
import { Button, Form, Input, message } from 'antd'
import { useNavigate } from 'react-router-dom'
import { login } from '../../api/login'

export default function Login() {
  const [form] = Form.useForm()
  const navigator = useNavigate()

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo)
  }

  const onReset = () => {
    form.resetFields()
  }

  const gotoRegister = () => {
    navigator('/register')
  }

  const onFinish = async (values: any) => {
    console.log(values.username, values.password)
    const res = await login(values.username, values.password)
    if (res?.code === 200) {
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('id', res.data.userid)
      message
        .success('登录成功, 正前往主页', 1)
        .then(() => navigator('/home'))
    } else {
      message.info(res?.message)
    }
  }

  return (
    <div className={style.back}>
      <img src={BackgroundImg} className={style.backGround}></img>
      <div className={style.centerDiv}>
        <div className={style.row1}>
          <div className={style.row1_left_text}>登录</div>
          <div>
            <span className={style.row1_right_text1}>没有账号?</span>
            <span className={style.row1_right_click_line} onClick={gotoRegister}>注册</span>
          </div>
        </div>
        <div className={style.input_box}>
          <Form
            className={style.from}
            form={form}
            name='login'
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
          >
            <Form.Item
              name='username'
              rules={[
                { required: true, message: '用户名不为空' },
                { min: 6, message: '用户名不少于6位' },
                { max: 14, message: '用户名不多于14位' }
              ]}
            >
              <Input placeholder='请输入6~14位用户名, 用户名由数字加字母组成'>
              </Input>
            </Form.Item>
            <Form.Item
              name='password'
              rules={[
                { required: true, message: '密码不为空' },
                { min: 6, message: '长度不小于6位' },
                { max: 20, message: '长度不大于20位' }
              ]}
            >
              <Input.Password placeholder='请输入密码,密码由数字加字母组成'></Input.Password>
            </Form.Item>
            <Form.Item>
              <div className={style.buttons}>
                <Button className={style.button} type='primary' htmlType="submit">登录</Button>
                <Button className={style.button} htmlType="button" onClick={onReset}>重置</Button>
              </div>
            </Form.Item>
            <Form.Item>
            </Form.Item>
          </Form>
        </div>
      </div>
    </div>
  )
}
