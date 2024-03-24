import request from '@/utils/request'

export function ChartGen(data) {
  return request({
    url: '/chart/gen',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export function ChartPage(data) {
  return request({
    url: '/chart/page',
    method: 'post',
    data
  })
}
