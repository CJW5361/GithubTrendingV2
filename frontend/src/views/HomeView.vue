<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ChatBot from '@/components/ChatBot.vue'
import { useGithubStore } from '@/stores/github'

const store = useGithubStore()
const selectedRepo = ref(null)

function selectRepo(repo) {
  selectedRepo.value = repo
}

onMounted(() => {
  store.fetchTrending()
})
</script>

<template>
  <div class="home" style="width: 195.3%;">
    <div class="content-wrapper" style="width: 100%;" >
      <div class="content">
        <!-- 왼쪽: 저장소 목록 -->
        <div class="card repositories-container" >
          <div v-if="store.loading" class="loading">
            <div class="loading-spinner"></div>
            Loading...
          </div>
          <div v-else-if="store.error" class="error">
            {{ store.error }}
          </div>
          <div v-else class="repositories">
            <div v-for="repo in store.repositories" 
                 :key="repo.id" 
                 class="repo-card"
                 @click="selectRepo(repo)"
                 :class="{ active: selectedRepo?.id === repo.id }">
              <h2>
                <a :href="repo.url" target="_blank" rel="noopener">{{ repo.name }}</a>
              </h2>
              <p class="description">{{ repo.description }}</p>
              <div class="meta">
                <span v-if="repo.language" class="language">
                  <span class="dot"></span>
                  {{ repo.language }}
                </span>
                <span class="stars">⭐ {{ repo.stars.toLocaleString() }}</span>
                <span class="forks">🔱 {{ repo.forks.toLocaleString() }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 오른쪽: 분석 결과 -->
        <div class="card analysis-section" style="width: 90%;">
          <div class="analysis-container">
            <ChatBot :selectedRepo="selectedRepo" />
          </div>
          <div class="analysis-footer"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home {
  min-height: 100vh;
  background: var(--bg-secondary);
  padding: 1rem;
  overflow-x: hidden;
}

.content-wrapper {
  max-width: 1800px;
  margin: 0 auto;
  height: calc(100vh - 80px);
}

.content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  height: 100%;
}

.card {
  background: var(--bg-primary);
  border-radius: 12px;
  border: 1px solid var(--border-color);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.repositories-container {
  overflow-y: auto;
  padding: 1rem;
  height: 100%;
}

.repositories {
  display: grid;
  grid-template-columns: 1fr;
  gap: 0.8rem;
}

.repo-card {
  border: 1px solid var(--border-color);
  border-radius: 8px;
  padding: 1rem;
  background: var(--bg-primary);
  transition: all 0.2s ease;
  cursor: pointer;
}

.repo-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.repo-card.active {
  border-color: var(--link-color);
  box-shadow: 0 0 0 2px var(--link-color);
}

.analysis-section {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.analysis-container {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
}

.analysis-footer {
  height: 4px;
  background: var(--border-color);
  margin: 0 1rem;
  border-radius: 2px;
}

.repo-card h2 {
  margin: 0 0 0.5rem;
  font-size: 1.2rem;
}

.repo-card a {
  color: var(--link-color);
  text-decoration: none;
}

.repo-card a:hover {
  text-decoration: underline;
}

.description {
  color: var(--text-secondary);
  font-size: 0.9rem;
  margin-bottom: 1rem;
  line-height: 1.5;
}

.meta {
  display: flex;
  gap: 1rem;
  font-size: 0.9rem;
  color: var(--text-secondary);
}

.language {
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: #f1e05a;
}

.loading {
  text-align: center;
  padding: 2rem;
  color: var(--text-secondary);
}

.loading-spinner {
  width: 40px;
  height: 40px;
  margin: 0 auto 1rem;
  border: 3px solid var(--border-color);
  border-top-color: var(--link-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error {
  text-align: center;
  padding: 2rem;
  color: #cb2431;
}

@media (max-width: 1200px) {
  .home {
    padding: 0.5rem;
  }
  
  .content {
    gap: 0.5rem;
  }
  
  .repositories-container,
  .analysis-container {
    padding: 0.8rem;
  }
}

@media (max-width: 768px) {
  .content {
    grid-template-columns: 1fr;
  }
}
</style>
