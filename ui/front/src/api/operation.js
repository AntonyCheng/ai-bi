import request from '@/utils/request'

export function pageOperation(data) {
  return request({
    url: '/operation/page',
    method: 'get',
    params: data
  })
}

export function clearOperation() {
  return request({
    url: '/operation/clear',
    method: 'delete'
  })
}

export function exportExcel() {
  return request({
    url: '/operation/export',
    method: 'get',
    // 后端传来二进制流是需要修改为blob类型
    responseType: 'blob'
  })
}
