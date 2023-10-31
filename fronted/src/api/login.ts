import request from "../utils/request"

interface ILogin {
  token: string
}
export const login = async (username: string, password: string) => {
  return await request<ILogin>({
    method: 'POST',
    url: '/api/v1/user/login/',
    data: {
      username,
      password
    }
  })
}
