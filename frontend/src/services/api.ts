import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

export const getGithubTrending = async () => {
  try {
    const response = await api.get('/repos/trending')
    console.log('API Response:', response.data)  // 응답 데이터 확인
    return response.data
  } catch (error) {
    console.error('API Error:', error)
    throw error
  }
} 