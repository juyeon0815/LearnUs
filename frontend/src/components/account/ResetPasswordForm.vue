<template>
  <div class="other-forms">
    <!-- Title -->
    <div class="heading-box">
      <span class="heading">Reset<span class="colon">:</span>PW</span>
      <span class="heading spectrum">Reset:PW</span>
    </div>
    <span class="sub-heading">비밀번호를 재설정합니다.</span>
    <!-- Inputs -->
    <div class="account-inputs">
      <!-- 비밀번호 input -->
      <div class="account-input-box">
        <input 
          id="password"
          class="account-input"
          v-model="password"
          type="password"
          placeholder="새로운 비밀번호를 입력해 주세요."
          required
        />
        <label>Password</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>
      <!-- 비밀번호 확인 input -->
      <div class="account-input-box">
        <input 
          id="password-confirm"
          class="account-input"
          v-model="passwordConfirm"
          type="password"
          placeholder="비밀번호를 한 번 더 확인합니다."
          required
        />
        <label>Password Confirm</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
      <!-- 비밀번호 재설정 버튼 -->
      <button
        :class="[isSubmit ? 'btn-orange' : 'btn-disabled', 'btn-submit']"
        @click="onResetPassword(password)">
        Reset Password
      </button>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"
import { mapActions } from 'vuex'

export default {
  name: 'ResetPasswordForm',
  data: () => {
    return {
      password: '',
      passwordConfirm: '',
      passwordSchema: new PV(),
      error: {
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
    }
  },
  methods: {
    ...mapActions('account', ['onResetPassword']),
    // 형식 검증 method
    checkForm() {
      // 비밀번호 형식 검증
      if (this.password.length > 0 && 
      !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문, 숫자 포함 8자 이상이어야 합니다."
      } else {
        this.error.password = false
      }
      // 비밀번호 확인 형식 검증
      if (this.passwordConfirm.length > 0 && this.password !== this.passwordConfirm) {
        this.error.passwordConfirm = "입력하신 비밀번호와 일치하지 않습니다."
      } else {
        this.error.passwordConfirm = false;
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      })
      if (this.password === '' || this.passwordConfirm === '') {
        isSubmit = false;
      }
      this.isSubmit = isSubmit;
    },  
  },
  watch: {
    password: function() {
      this.checkForm();
    },
    passwordConfirm: function() {
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