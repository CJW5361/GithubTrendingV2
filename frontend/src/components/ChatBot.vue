<template>
  <div class="chatbot">
    <div class="chat-header">
      <h3>Repository Analysis</h3>
      <div class="repo-info" v-if="selectedRepo">
        <a :href="selectedRepo.url" target="_blank" rel="noopener">
          {{ selectedRepo.name }}
        </a>
      </div>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div v-if="loading" class="loading-message">
        <div class="loading-spinner"></div>
        <p>분석 중...</p>
      </div>
      <div v-else-if="messages.length === 0" class="empty-state">
        저장소를 선택하면 AI가 분석 결과를 보여드립니다.
      </div>
      <div v-for="(message, index) in messages" 
           :key="index" 
           class="message"
           :class="message.type">
        <div class="message-content" v-html="formatMessage(message.content)"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import axios from 'axios'

const props = defineProps<{
  selectedRepo: any | null
}>()

const messages = ref<Array<{type: string, content: string}>>([])
const loading = ref(false)
const messagesContainer = ref<HTMLElement | null>(null)

watch(() => props.selectedRepo, async (newRepo) => {
  if (!newRepo) return
  
  loading.value = true
  messages.value = []
  
  try {
    const response = await axios.post('http://localhost:8080/api/chat/analyze', {
      repoName: newRepo.name,
      readme: await fetchReadme(newRepo.name)
    })
    
    messages.value.push({
      type: 'bot',
      content: response.data
    })
  } catch (error) {
    messages.value.push({
      type: 'error',
      content: '분석 중 오류가 발생했습니다.'
    })
  } finally {
    loading.value = false
  }
})

async function fetchReadme(repoName: string) {
  const [owner, repo] = repoName.split('/')
  const response = await axios.get(
    `https://api.github.com/repos/${owner}/${repo}/readme`,
    { headers: { Accept: 'application/vnd.github.v3.raw' } }
  )
  return response.data
}

const formatMessage = (content: string) => {
  return content
    .replace(/📌|💡|🛠|✨/g, '<span class="emoji">$&</span>')
    .replace(/\n/g, '<br>')
    .replace(/^- (.+)$/gm, '<li>$1</li>')
    .replace(/(?:^|\n)([^-\n].+?)(?=\n|$)/g, '<p>$1</p>');
};
</script>

<style scoped>
.chatbot {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.chat-header {
  padding-bottom: 1rem;
  border-bottom: 1px solid var(--border-color);
  margin-bottom: 1rem;
}

.chat-header h3 {
  margin: 0;
  color: var(--text-primary);
  font-size: 1.2rem;
}

.repo-info {
  margin-top: 0.5rem;
  font-size: 0.9rem;
}

.repo-info a {
  color: var(--link-color);
  text-decoration: none;
}

.repo-info a:hover {
  text-decoration: underline;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding-right: 0.5rem;
}

.message {
  background: var(--bg-secondary);
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  line-height: 1.6;
}

.message.bot {
  background: var(--bg-secondary);
  color: var(--text-primary);
}

.message.error {
  background: #ffebe9;
  color: #cf222e;
}

.loading-message {
  text-align: center;
  padding: 2rem;
  color: var(--text-secondary);
}

.loading-spinner {
  width: 30px;
  height: 30px;
  margin: 0 auto 1rem;
  border: 3px solid var(--border-color);
  border-top-color: var(--link-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.empty-state {
  text-align: center;
  padding: 2rem;
  color: var(--text-secondary);
  font-size: 0.9rem;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.message-content {
  white-space: pre-wrap;
}

.message-content :deep(.emoji) {
  font-size: 1.2em;
  margin-right: 0.5rem;
}

.message-content :deep(p) {
  margin: 0.5rem 0;
}

.message-content :deep(li) {
  margin: 0.3rem 0;
  margin-left: 1.5rem;
}
</style> 