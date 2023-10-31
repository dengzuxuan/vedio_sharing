import request from "../utils/request"

export const register = async (username: string, password: string) => {
  return await request({
    method: 'POST',
    url: '/api/v1/user/register/',
    data: {
      username,
      password
    }
  })
}
