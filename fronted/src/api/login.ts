import request from "../utils/request"

export const login = async (username: string, password: string) => {
  return await request({
    method: 'POST',
    url: '/api/v1/user/login/',
    data: {
      username,
      password
    }
  })
}
