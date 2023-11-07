import React, { useState } from 'react'
import './App.css'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Home from './pages/Home'
import Self from './pages/Self'
import Recommend from './pages/Recommend'
import UploadView from './pages/UploadView'
import VideoItem from './pages/VideoItem'
import User from './pages/User'
import { StoreProvider } from './hooks/store'
import Channel from './pages/Channel'

function App() {
  // 控制左tabs高亮
  document.title = '短视频平台'
  const [clickItemValue, setClickItemValue] = useState('recommend')
  return (
    <div className="App">
      <StoreProvider value={{
        clickItemValue,
        setClickItemValue
      }}>
      <BrowserRouter>
        <Routes>
          <Route path='/login' element={<Login />}></Route>
          <Route path='/home' element={<Home/>}>
            <Route path='Recommend' element={<Recommend/>}></Route>
            <Route path='my' element={<Self/>}></Route>
            <Route path='upload' element={<UploadView/>}></Route>
            <Route path='user/:user_id' element={<User/>}></Route>
            <Route path='channel/:type' element={<Channel/>}></Route>
          </Route>
          <Route path='/video/:video_id' element={<VideoItem/>}></Route>
          <Route path='register' element={<Register/>}></Route>
          <Route path='*' element={<Navigate to='/login' />}></Route>
        </Routes>
      </BrowserRouter>
      </StoreProvider>
    </div>
  )
}

export default App
