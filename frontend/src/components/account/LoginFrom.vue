<template>
  <div class="login-form">
    <!-- Title -->
    <div class="heading-box">
      <span class="heading">LOGIN</span>
      <span class="heading spectrum-left">LOGIN</span>
      <span class="heading spectrum-right">LOGIN</span>
    </div>
    <span class="sub-heading">Let's Play with SSAFY!</span>
    <!-- Inputs -->
    <div class="account-inputs">
      <!-- 이메일 input -->
      <div class="account-input-box">
        <input 
          id="email"
          class="account-input"
          v-model="email"
          type="text"
          autocapitalize="off"
          placeholder="이메일을 입력해 주세요."
          required
        />
        <label>E-mail</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>
      <!-- 비밀번호 input -->
      <div class="account-input-box">
        <input 
          id="password"
          class="account-input"
          v-model="password"
          type="password"
          placeholder="비밀번호를 입력해 주세요."
          required
        />
        <label>Password</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>
      <!-- 로그인 버튼 -->
      <button
        :class="[isSubmit ? 'btn-orange' : 'btn-disabled', 'btn-submit']">
        LOGIN
      </button>
      <!-- 페이지 이동 텍스트 -->
      <div class="direct-box">
        <span 
          @click="$router.push({ name: 'Account', params: {page: 'find-email'}})"
          class="direct-text"
        >계정 찾기</span>
        <span class="dot-space">⬝</span>
        <span 
          @click="$router.push({ name: 'Account', params: {page: 'find-password'}})"
          class="direct-text"
        >비밀번호 찾기</span>
      </div>
    </div>
  </div>
</template>

<script>
import * as EmailValidator from "email-validator"
import PV from "password-validator"

export default {
  name: 'LoginForm',
  data: () => {
    return {
      email: '',
      password: '',
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
      },
      isSubmit: false,
    }
  },
  methods: {
    // 형식 검증 method
    checkForm() {
      // 이메일 형식 검증
      if (this.email.length > 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다."
      } else {
        this.error.email = false
      }
      // 비밀번호 형식 검증
      if (this.password.length > 0 && 
      !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문, 숫자 포함 8자 이상이어야 합니다."
      } else {
        this.error.password = false
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      })
      if (this.email === '' || this.password === '') {
        isSubmit = false;
      }
      this.isSubmit = isSubmit;
    }
  },
  watch: {
    email: function() {
      this.checkForm();
    },
    password: function() {
      this.checkForm();
    }
  },
  created() {
    // 비밀번호 규칙 생성
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters()
  }
}
</script>

<style>

</style>