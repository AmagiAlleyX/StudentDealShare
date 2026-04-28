<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <!-- 左侧图片区域 -->
        <div class="auth-image">
          <div class="image-content">
            <div class="image-icon">🎉</div>
            <h2>加入我们</h2>
            <p>注册账号，开启精彩旅程</p>
          </div>
        </div>

        <!-- 右侧表单区域 -->
        <div class="auth-form-container">
          <div class="form-header">
            <h1>创建账号</h1>
            <p>填写以下信息完成注册</p>
          </div>

          <el-form ref="formRef" :model="registerForm" :rules="rules" class="auth-form" label-width="0">
            <el-form-item prop="username">
              <el-input
                v-model="registerForm.username"
                placeholder="请输入用户名"
                size="large"
                :prefix-icon="User"
                clearable
              />
            </el-form-item>

            <el-form-item prop="email">
              <el-input
                v-model="registerForm.email"
                placeholder="请输入邮箱"
                size="large"
                :prefix-icon="Message"
                clearable
              />
            </el-form-item>

            <el-form-item prop="phone">
              <el-input
                v-model="registerForm.phone"
                placeholder="请输入手机号（选填）"
                size="large"
                :prefix-icon="Phone"
                clearable
              />
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="registerForm.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                :prefix-icon="Lock"
                show-password
              />
            </el-form-item>

            <el-form-item prop="confirmPassword">
              <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                placeholder="请确认密码"
                size="large"
                :prefix-icon="Lock"
                show-password
                @keyup.enter="handleRegister"
              />
            </el-form-item>

            <el-form-item>
              <el-checkbox v-model="registerForm.agree">
                我已阅读并同意 <el-link type="primary" :underline="false">用户协议</el-link> 和 <el-link type="primary" :underline="false">隐私政策</el-link>
              </el-checkbox>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                size="large"
                class="register-button"
                :loading="loading"
                @click="handleRegister"
              >
                {{ loading ? '注册中...' : '立即注册' }}
              </el-button>
            </el-form-item>

            <div class="form-footer">
              <span>已有账号？</span>
              <router-link to="/login" class="login-link">立即登录</router-link>
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
import { User, Lock, Message, Phone } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { authService } from '@/api/auth'

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)

const registerForm = reactive({
  username: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  agree: false,
})

const validateConfirmPassword = (rule: any, value: string, callback: any) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3-20 个字符', trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' },
  ],
}

const handleRegister = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      if (!registerForm.agree) {
        ElMessage.warning('请先同意用户协议和隐私政策')
        return
      }
      
      loading.value = true
      try {
        await authService.register({
          username: registerForm.username,
          email: registerForm.email,
          phone: registerForm.phone,
          password: registerForm.password,
        })
        
        ElMessage.success('注册成功，请登录')
        
        // 注册成功后跳转到登录页
        setTimeout(() => {
          router.push('/login')
        }, 500)
      } catch (error: any) {
        console.error('注册失败:', error)
        // 错误消息已经在 request.ts 中处理
      } finally {
        loading.value = false
      }
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
  margin-bottom: 16px;
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

.register-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  background-color: var(--accent-color);
  border: none;
  transition: all 0.3s ease;
}

.register-button:hover {
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

.login-link {
  color: var(--accent-color);
  font-weight: 600;
  margin-left: 4px;
  transition: color 0.2s ease;
}

.login-link:hover {
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
