<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { useThemeStore } from '@/stores/theme'

const store = useThemeStore()
</script>

<template>
  <div :class="{ 'dark-mode': store.isDark }" >
    <header>
      <nav>
        <RouterLink to="/" class="logo">GitHub Trending</RouterLink>
        <button @click="store.toggleTheme" class="theme-toggle">
          {{ store.isDark ? '🌞' : '🌙' }}
        </button>
      </nav>
    </header>

    <RouterView />
  </div>
</template>

<style>
:root {
  --bg-primary: #ffffff;
  --bg-secondary: #f6f8fa;
  --text-primary: #24292e;
  --text-secondary: #586069;
  --border-color: #e1e4e8;
  --link-color: #0366d6;
  --header-bg: #24292e;
  --header-text: #ffffff;
}

.dark-mode {
  --bg-primary: #0d1117;
  --bg-secondary: #161b22;
  --text-primary: #c9d1d9;
  --text-secondary: #8b949e;
  --border-color: #30363d;
  --link-color: #58a6ff;
  --header-bg: #161b22;
  --header-text: #c9d1d9;
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
  width: 1400px;
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

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  background: var(--header-bg);
  box-shadow: 0 1px 3px rgba(0,0,0,0.12);
  width: 200%;
}

h1 {
  margin: 0;
  color: var(--text-primary);
}

nav {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  color: var(--header-text);
  text-decoration: none;
  font-size: 1.2rem;
  font-weight: 600;
}

.theme-toggle {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 50%;
  transition: background-color 0.3s ease;
}

.theme-toggle:hover {
  background: rgba(255,255,255,0.1);
}
</style>
