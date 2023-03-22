import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询记录列表
export function listproduction(query) {
    return request({
      url: '/drug/production/list',
      method: 'get',
      params: query
    })
  }

  // 新增用户
export function addDrug(data) {
    return request({
      url: '/drug/production',
      method: 'post',
      data: data
    })
  }

  // 删除用户
export function delDrug(numbers) {
    return request({
      url: '/drug/production/' + numbers,
      method: 'delete'
    })
  }

  // 查询用户个人信息
export function getDruginfo(number) {
    return request({
      url: '/drug/production/'+parseStrEmpty(number),
      method: 'get'
    })
  }