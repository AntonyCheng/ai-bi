import request from '@/utils/request'

export function Upload(data) {
  return request({
    url: '/auth/upload',
    method: 'post',
    data
  })
}
