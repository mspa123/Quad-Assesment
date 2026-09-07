import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/questions': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/checkanswers': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})