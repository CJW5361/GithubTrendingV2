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
      <div v-if="initialLoading" class="loading-message">
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
      <div v-if="chatLoading" class="message bot typing-indicator">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>

    <div class="chat-input" v-if="selectedRepo">
      <input 
        type="text" 
        v-model="userInput"
        @keyup.enter="sendMessage"
        placeholder="저장소에 대해 궁금한 점을 물어보세요..."
        :disabled="chatLoading"
      />
      <button @click="sendMessage" :disabled="chatLoading">
        전송
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, nextTick } from 'vue';
import type { Ref } from 'vue';

const props = defineProps<{
  selectedRepo: any
}>();

const messages: Ref<Array<{type: string, content: string}>> = ref([]);
const initialLoading = ref(false);
const chatLoading = ref(false);
const userInput = ref('');
const messagesContainer = ref<HTMLElement | null>(null);

// 메시지 추가될 때마다 자동 스크롤
watch(() => messages.value.length, () => {
  scrollToBottom();
});

const scrollToBottom = async () => {
  await nextTick();
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
  }
};

const sendMessage = async () => {
  if (!userInput.value.trim() || chatLoading.value) return;

  const question = userInput.value;
  messages.value.push({ type: 'user', content: question });
  userInput.value = '';
  chatLoading.value = true;

  try {
    const response = await fetch('/api/chat/question', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        repoName: props.selectedRepo.name,
        question: question,
      }),
    });

    const data = await response.json();
    messages.value.push({ type: 'bot', content: data.response });
  } catch (error) {
    messages.value.push({ 
      type: 'error', 
      content: '죄송합니다. 응답을 받아오는 중 오류가 발생했습니다.' 
    });
  } finally {
    chatLoading.value = false;
    scrollToBottom();
  }
};

watch(() => props.selectedRepo, async (newRepo) => {
  if (!newRepo) return;
  
  messages.value = [];
  initialLoading.value = true;

  try {
    const response = await fetch('/api/chat/analyze', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        repoName: newRepo.name,
        readme: newRepo.readme || '',
      }),
    });

    const data = await response.json();
    messages.value.push({ type: 'bot', content: data.analysis });
  } catch (error) {
    messages.value.push({ 
      type: 'error', 
      content: '저장소 분석 중 오류가 발생했습니다.' 
    });
  } finally {
    initialLoading.value = false;
    scrollToBottom();
  }
}, { immediate: true });

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
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
}

.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #4A72FF;
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #3558CC;
}

.chat-input {
  display: flex;
  gap: 0.8rem;
  padding: 1.2rem;
  border-top: 1px solid var(--border-color);
  background: var(--bg-primary);
}

.chat-input input {
  flex: 1;
  padding: 1rem;
  border: 2px solid #2D3748;
  border-radius: 8px;
  background: var(--bg-primary);
  color: var(--text-primary);
  font-size: 0.95rem;
  transition: all 0.2s;
}

.chat-input input:focus {
  outline: none;
  border-color: #4A72FF;
  box-shadow: 0 0 0 2px rgba(74, 114, 255, 0.2);
}

.chat-input button {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 8px;
  background: #4A72FF;
  color: white;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.chat-input button:hover {
  background: #3558CC;
}

.chat-input button:disabled {
  background: #A0AEC0;
  cursor: not-allowed;
}

.message {
  margin-bottom: 1rem;
  padding: 1.2rem;
  border-radius: 12px;
  line-height: 1.6;
  max-width: 90%;
}

.message.user {
  background: #4A72FF;
  color: white;
  margin-left: auto;
  margin-right: 1rem;
  box-shadow: 0 2px 4px rgba(74, 114, 255, 0.2);
}

.message.bot {
  background: #2D3748;
  color: #E2E8F0;
  margin-left: 1rem;
  margin-right: auto;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.message.error {
  background: #FED7D7;
  color: #C53030;
  margin: 1rem auto;
  text-align: center;
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
  display: inline-block;
}

.message-content :deep(p) {
  margin: 0.5rem 0;
}

.message-content :deep(li) {
  margin: 0.3rem 0 0.3rem 1.2rem;
}

.typing-indicator {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 1rem !important;
  min-width: 100px;
  max-width: 120px !important;
}

.typing-indicator span {
  width: 8px;
  height: 8px;
  background: #E2E8F0;
  border-radius: 50%;
  animation: typing 1.4s infinite;
  display: inline-block;
}

.typing-indicator span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-indicator span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.chat-input input:disabled {
  background: var(--bg-secondary);
  cursor: not-allowed;
  opacity: 0.7;
}

.chat-header {
  padding: 1rem;
  border-bottom: 1px solid var(--border-color);
}

.chat-header h3 {
  color: var(--text-primary);
  margin: 0;
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
</style> 