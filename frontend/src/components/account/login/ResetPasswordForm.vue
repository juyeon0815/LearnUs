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
          maxlength="99"
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
          maxlength="99"
          required
        />
        <label>Password Confirm</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
      <!-- 비밀번호 재설정 버튼 -->
      <button
        :class="[isSubmit ? 'btn-orange' : 'btn-disabled', 'btn-submit']"
        @click="onResetPassword">
        Reset Password
      </button>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import PV from "password-validator"

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
        ...mapMutations('account', ['SET_HTTP_STATUS']),
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
    async onResetPassword() {
      await this.$store.dispatch('account/onResetPassword', this.password)
      const httpStatus = this.$store.state.account.httpStatus
      if (httpStatus !== null) {
        const alertInfo = {
          type: 'fail',
          message: '',
        }
        if (httpStatus === 200) {
          alertInfo.type = 'success'
          alertInfo.message = '비밀번호 설정이 완료되었습니다.'
        } else if (httpStatus === 400) {
          alertInfo.message = '잘못된 비밀번호입니다.'
        } else if (httpStatus === 500) {
          alertInfo.message = '서버 오류입니다.'
        } else {
          alertInfo.message = `${httpStatus} 오류입니다.`
        }
        this.$emit('alert', alertInfo)
      }
    }
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
    this.SET_HTTP_STATUS(null)
  }
}
</script>

<style>

</style>