export default function () {
  // 跳转到新页面
  const jump = (id: number) => {
    const w = window.open('_black')
    if (w) {
      w.location.href = `/video/${id}`
    }
  }
  return {
    jump
  }
}
