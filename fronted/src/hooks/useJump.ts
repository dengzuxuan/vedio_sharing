import { useContext } from "react"
import { context } from "./store"

export default function () {
  const { setClickItemValue } = useContext(context)
  // 跳转到新页面
  const jump = (id: number) => {
    const w = window.open('_black')
    if (w) {
      w.location.href = `/video/${id}`
    }
  }

  // 评论区跳转他人页面
  const otherJump = (userid: number | undefined) => {
    const id = localStorage.getItem('id')
    if (!id) return
    if (parseInt(id) === userid) {
      setClickItemValue('my')
    } else {
      setClickItemValue(`user/${userid}`)
    }
  }
  return {
    jump,
    otherJump
  }
}
