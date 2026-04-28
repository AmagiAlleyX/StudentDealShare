<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <!-- 左侧图片区域 -->
        <div class="auth-image">
          <div class="image-content">
            <div class="image-icon">📚</div>
            <h2>欢迎回来</h2>
            <p>登录账号，探索更多精彩内容</p>
          </div>
        </div>

        <!-- 右侧表单区域 -->
        <div class="auth-form-container">
          <div class="form-header">
            <h1>用户登录</h1>
            <p>输入您的账号信息以继续</p>
          </div>

          <el-form ref="formRef" :model="loginForm" :rules="rules" class="auth-form" label-width="0">
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名/手机号/邮箱"
                size="large"
                :prefix-icon="User"
                clearable
              />
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                :prefix-icon="Lock"
                show-password
                @keyup.enter="handleLogin"
              />
            </el-form-item>

            <div class="form-options">
              <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
              <el-link type="primary" :underline="false">忘记密码？</el-link>
            </div>

            <el-form-item>
              <el-button
                type="primary"
                size="large"
                class="login-button"
                :loading="loading"
                @click="handleLogin"
              >
                {{ loading ? '登录中...' : '登录' }}
              </el-button>
            </el-form-item>

            <div class="form-footer">
              <span>还没有账号？</span>
              <router-link to="/register" class="register-link">立即注册</router-link>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: '',
  remember: false,
})

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3-20 个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' },
  ],
}

const handleLogin = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      // TODO: 调用后端登录接口
      setTimeout(() => {
        loading.value = false
        console.log('登录信息', loginForm)
        // 登录成功后跳转到首页
        router.push('/')
      }, 1500)
    }
  })
}
</script>

<style scoped>
.auth-page {
  min-height: calc(100vh - 64px);
  margin-top: 64px;
  background-color: var(--bg-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
}

.auth-container {
  width: 100%;
  max-width: 900px;
}

.auth-card {
  display: grid;
  grid-template-columns: 1fr 1fr;
  background-color: var(--card-bg);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--shadow-lg);
  border: 1px solid var(--border-color);
}

/* 左侧图片区域 */
.auth-image {
  background: linear-gradient(135deg, var(--accent-color) 0%, var(--accent-hover) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px 40px;
  position: relative;
  overflow: hidden;
}

.auth-image::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
  animation: rotate 20s linear infinite;
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.image-content {
  text-align: center;
  color: #ffffff;
  position: relative;
  z-index: 1;
}

.image-icon {
  font-size: 80px;
  margin-bottom: 24px;
  animation: bounce 2s ease-in-out infinite;
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.image-content h2 {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 12px;
}

.image-content p {
  font-size: 16px;
  opacity: 0.9;
}

/* 右侧表单区域 */
.auth-form-container {
  padding: 60px 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-header {
  margin-bottom: 32px;
}

.form-header h1 {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.form-header p {
  font-size: 14px;
  color: var(--text-secondary);
}

.auth-form {
  width: 100%;
}

.auth-form :deep(.el-form-item) {
  margin-bottom: 20px;
}

.auth-form :deep(.el-input__wrapper) {
  padding: 12px 16px;
  border-radius: 8px;
  background-color: var(--input-bg);
  border: 1px solid var(--border-color);
  transition: all 0.3s ease;
}

.auth-form :deep(.el-input__wrapper:hover) {
  border-color: var(--accent-color);
}

.auth-form :deep(.el-input__wrapper.is-focus) {
  border-color: var(--accent-color);
  box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.1);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  font-size: 14px;
}

.login-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  background-color: var(--accent-color);
  border: none;
  transition: all 0.3s ease;
}

.login-button:hover {
  background-color: var(--accent-hover);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.form-footer {
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: var(--text-secondary);
}

.register-link {
  color: var(--accent-color);
  font-weight: 600;
  margin-left: 4px;
  transition: color 0.2s ease;
}

.register-link:hover {
  color: var(--accent-hover);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .auth-card {
    grid-template-columns: 1fr;
  }

  .auth-image {
    display: none;
  }

  .auth-form-container {
    padding: 40px 24px;
  }

  .form-header h1 {
    font-size: 24px;
  }
}
</style>
