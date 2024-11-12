import { defineStore } from 'pinia'
import { getGithubTrending } from '@/services/api'
import { ref } from 'vue'

interface GithubRepo {
  id: number
  name: string
  url: string
  description: string
  language: string
  stars: number
  forks: number
  crawledAt: string
}

export const useGithubStore = defineStore('github', () => {
  const repositories = ref<GithubRepo[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  async function fetchTrending() {
    loading.value = true
    try {
      const data = await getGithubTrending()
      console.log('Fetched data:', data)  // 데이터 확인용
      repositories.value = data
      error.value = null
    } catch (err) {
      console.error('Error fetching data:', err)
      error.value = '데이터를 불러오는데 실패했습니다.'
    } finally {
      loading.value = false
    }
  }

  return { repositories, loading, error, fetchTrending }
}) 