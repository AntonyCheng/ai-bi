import request from '@/utils/request'

export function pageFile(data) {
  return request({
    url: '/file/page',
    method: 'get',
    params: data
  })
}

export function exportExcel() {
  return request({
    url: '/file/export',
    method: 'get',
    // 后端传来二进制流是需要修改为blob类型
    responseType: 'blob'
  })
}
