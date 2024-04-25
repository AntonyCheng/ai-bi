import request from '@/utils/request'

export function captcha() {
  return request({
    url: '/captcha',
    method: 'get'
  })
}
