import request from '@/utils/request'

export function Upload(data) {
  return request({
    url: '/user/updateAvatar',
    method: 'put',
    data
  })
}
