import React from 'react'
import BackgroundImg from '../../assets/imgs/background.png'
import style from './index.module.scss'
import { Button, Form, Input } from 'antd'
import { useNavigate } from 'react-router-dom'

export default function Register () {
  const [form] = Form.useForm()
  const navigator = useNavigate()

  // const onFinish = async (values: ILoginValues) => {
  //   const res = await login(values.username, values.password)
  //   if (res?.code === 200) {
  //     const { role, token } = res.data
  //     if (role !== values.role) {
  //       message.info('角色错误')
  //     } else {
  //       localStorage.setItem('token', token!)
  //       message.success('登录成功')
  //       if (role === 3) {
  //         localStorage.setItem('role', '3')
  //         navigator('/member')
  //       } else if (role === 2) {
  //         localStorage.setItem('role', '2')
  //         navigator('/leader')
  //       } else if (role === 1) {
  //         localStorage.setItem('role', '1')
  //         navigator('/manager')
  //       }
  //     }
  //   } else {
  //     message.info(res?.message)
  //   }
  // }

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo)
  }

  const onReset = () => {
    form.resetFields()
  }

  const gotoRegister = () => {
    navigator('/login')
  }
  return (
    <div className={style.back}>
      <img src={BackgroundImg} className={style.backGround}></img>
      <div className={style.centerDiv}>
        <div className={style.row1}>
          <div className={style.row1_left_text}>注册</div>
          <div>
            <span className={style.row1_right_text1}>已有账号?</span>
            <span className={style.row1_right_click_line} onClick={gotoRegister}>登录</span>
          </div>
        </div>
        <div className={style.input_box}>
          <Form
            className={style.from}
            form={form}
            name='login'
            // onFinish={onFinish}
            onFinishFailed={onFinishFailed}
          >
            <Form.Item
              name='username'
              rules={[
                { required: true, message: '用户名不为空' },
                { min: 2, message: '用户名不少于2位' },
                { max: 12, message: '用户名不多于12位' }
              ]}
            >
              <Input placeholder='请输入2~12位用户名'>
              </Input>
            </Form.Item>
            <Form.Item
              name='password'
              rules={[
                { required: true, message: '密码不为空' }
              ]}
            >
              <Input.Password placeholder='请输入密码'></Input.Password>
            </Form.Item>
            <Form.Item
              name='passwordConfirm'
              rules={[
                { required: true, message: '密码不为空' }
              ]}
            >
              <Input.Password placeholder='请再次输入密码'></Input.Password>
            </Form.Item>
            <Form.Item>
              <div className={style.buttons}>
                <Button className={style.button} type='primary' htmlType="submit">注册</Button>
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
