<script setup lang="ts">
import { ref, onMounted } from 'vue';

const isDark = ref(true);

const toggleTheme = () => {
  isDark.value = !isDark.value;
  if (isDark.value) {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
  localStorage.setItem('theme', isDark.value ? 'dark' : 'light');
};

onMounted(() => {
  // 저장된 테마 설정 불러오기
  const savedTheme = localStorage.getItem('theme') || 'dark';
  isDark.value = savedTheme === 'dark';
  if (isDark.value) {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
});
</script>

<template>
  <div class="app">
    <header class="header">
      <div class="header-content">
        <h1 class="title">
          <span class="github">GitHub</span>
          <span class="trending">Trending</span>
        </h1>
        <div class="theme-toggle" @click="toggleTheme">
          {{ isDark ? '🌙' : '☀️' }}
        </div>
      </div>
    </header>
    <RouterView />
  </div>
</template>

<style>
:root {
  --bg-primary: #ffffff;
  --bg-secondary: #f6f8fa;
  --text-primary: #24292f;
  --text-secondary: #57606a;
  --border-color: #d0d7de;
  --link-color: #0969da;
}

:root.dark {
  --bg-primary: #0d1117;
  --bg-secondary: #161b22;
  --text-primary: #c9d1d9;
  --text-secondary: #8b949e;
  --border-color: #30363d;
  --link-color: #58a6ff;
}

body {
  margin: 0;
  min-height: 100vh;
  background: var(--bg-secondary);
  color: var(--text-primary);
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Helvetica, Arial, sans-serif;
  transition: background-color 0.3s ease;
}

.app {
  width: 1350px;
  margin: 0 auto;
  min-height: 100vh;
}

/* 브라우저가 1400px보다 작을 때만 반응형으로 동작 */
@media (max-width: 1400px) {
  .app {
    width: 100%;
    padding: 0 1rem;
  }
}

.header {
  padding: 1.5rem 0;
  border-bottom: 1px solid var(--border-color);
  background: var(--bg-primary);
}

.header-content {
  max-width: 1800px;
  margin: 0 auto;
  padding: 0 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  margin: 0;
  font-size: 2.5rem;
  font-weight: 700;
  letter-spacing: -0.5px;
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.github {
  color: var(--text-primary);
  position: relative;
}

.trending {
  color: #4A72FF;
  background: linear-gradient(135deg, #4A72FF 0%, #6D8DFF 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  position: relative;
}

.theme-toggle {
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 50%;
  transition: background-color 0.2s;
}

.theme-toggle:hover {
  background: var(--bg-secondary);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }
  
  .header-content {
    padding: 0 1rem;
  }
}

/* 전역 스크롤바 스타일 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: #4A72FF;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: #3558CC;
}

/* Firefox를 위한 스크롤바 스타일 */
* {
  scrollbar-width: thin;
  scrollbar-color: #4A72FF transparent;
}
</style>
