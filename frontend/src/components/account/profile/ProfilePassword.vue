<template>
  <div class="profile-info margin-end">
    <div class="title-button-box">
      <div class="title">
        <span>Password</span>
      </div>
      <button 
        :class="[isSubmit ? 'active-btn' : 'disabled-btn', 'btn change-info-btn']"
        @click="onChangePassword">
        비밀번호 변경
      </button>
    </div>
    <div class="info-inputs">
      <!-- 기존 비밀번호 input -->
      <div class="info-input-box">
        <input 
          type="password"
          class="info-input"
          placeholder="기존 비밀번호를 입력하세요"
          v-model="originPW"
          required
        />
        <label>Old Password</label>
        <div class="error-text" v-if="error.originPW">{{error.originPW}}</div>
      </div>
    </div>
    <div class="info-inputs">
      <!-- 새 비밀번호 input -->
      <div class="info-input-box">
        <input 
          type="password"
          class="info-input margin-between"
          placeholder="새로운 비밀번호를 입력하세요"
          v-model="newPW"
          maxlength="99"
          required
        />
        <label>New Password</label>
        <div class="error-text" v-if="error.newPW">{{error.newPW}}</div>
      </div>
      <!-- 새 비밀번호 확인 input -->
      <div class="info-input-box">
        <input 
          type="password"
          class="info-input"
          placeholder="비밀번호를 한 번 더 입력하세요"
          v-model="passwordConfirm"
          maxlength="99"
          required
        />
        <label>New Password Confirm</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"

export default {
  name: 'ProfilePassword',
  data: () => {
    return {
      originPW: '',
      newPW: '',
      passwordConfirm: '',
      passwordSchema: new PV(),
      error: {
        originPW: false,
        newPW: false,
        passwordConfirm: false,
      },
      isSubmit: false,
    }
  },
  methods: {
    // 형식 검증 method
    checkForm() {
      // 기존 비밀번호 형식 검증
      if (this.originPW.length > 0 && 
      !this.passwordSchema.validate(this.originPW)
      ) {
        this.error.originPW = "영문, 숫자 포함 8자 이상이어야 합니다."
      } else {
        this.error.originPW = false
      }
      // 새 비밀번호 형식 검증
      if (this.newPW.length > 0 && 
      !this.passwordSchema.validate(this.newPW)
      ) {
        this.error.newPW = "영문, 숫자 포함 8자 이상이어야 합니다."
      } else {
        this.error.newPW = false
      }
      // 비밀번호 확인 형식 검증
      if (this.passwordConfirm.length > 0 && this.newPW !== this.passwordConfirm) {
        this.error.passwordConfirm = "입력하신 비밀번호와 일치하지 않습니다."
      } else {
        this.error.passwordConfirm = false;
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      })
      if (this.originPW === '' || this.newPW === '' ||this.passwordConfirm === '') {
        isSubmit = false;
      }
      this.isSubmit = isSubmit;
    },
    async onChangePassword() {
      await this.$store.dispatch('account/onChangePassword', this.passwordData)
      const httpStatus = this.$store.state.account.httpStatus
      if (httpStatus !== null) {
        const alertInfo = {
          type: 'fail',
          message: '',
        }
        if (httpStatus === 200) {
          alertInfo.type = 'success'
          alertInfo.message = '비밀번호 변경이 완료되었습니다.'
          this.originPW = ''
          this.newPW = ''
          this.passwordConfirm = ''
        } else if (httpStatus === 400) {
          alertInfo.message = '기존 비밀번호를 다시 확인해주세요.'
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
    originPW: function() {
      this.checkForm()
    },
    newPW: function() {
      this.checkForm()
    },
    passwordConfirm() {
      this.checkForm()
    }
  },
  computed: {
    passwordData() {
      return {
        originPW: this.originPW,
        newPW: this.newPW,
      }
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
