<template>
  <div class="other-forms">
    <!-- Title -->
    <div class="heading-box">
      <span class="heading">Find<span class="colon">:</span>PW</span>
      <span class="heading spectrum">Find:PW</span>
    </div>
    <span class="sub-heading">정보 입력을 통해 비밀번호를 재설정할 수 있습니다.</span>
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
          placeholder="등록된 이메일을 입력해 주세요."
          required
        />
        <label>E-mail</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>
      <!-- 싸피학번 input -->
      <div class="account-input-box">
        <input 
          id="student-id"
          class="account-input"
          v-model="studentId"
          type="text"
          maxlength="7"
          placeholder="SSAFY 학번을 입력해 주세요."
          required
        />
        <label>student ID</label>
        <div class="error-text" v-if="error.studentId">{{error.studentId}}</div>
      </div>
      <!-- 전화번호 input -->
      <div class="account-input-box">
        <input 
          id="phone-number"
          class="account-input"
          v-model="phoneNumber"
          type="text"
          maxlength="13"
          placeholder="등록된 전화번호를 입력해 주세요."
          required
        />
        <label>Phone Number</label>
        <div class="error-text" v-if="error.phoneNumber">{{error.phoneNumber}}</div>
      </div>
      <!-- 제출 버튼 -->
      <button
        :class="[isSubmit ? 'btn-orange' : 'btn-disabled', 'btn-submit']"
        @click="onFindPassword">
        Check Account
      </button>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import * as EmailValidator from "email-validator"

export default {
  name: 'FindPasswordForm',
  data: () => {
    return {
      email: '',
      studentId: '',
      phoneNumber: '',
      error: {
        email: false,
        studentId: false,
        phoneNumber: false,
      },
      isSubmit: false,
    }
  },
  methods: {
    ...mapMutations('account', ['SET_HTTP_STATUS']),
    // 형식 검증 method
    checkForm() {
      // 이메일 형식 검증
      if (this.email.length > 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다."
      } else {
        this.error.email = false
      }
      // 학번 형식 검증
      if (this.studentId.length > 0 && this.studentId.length < 7) {
        this.error.studentId = "학번은 7자리 숫자이어야 합니다."
      } else {
        this.error.studentId = false
      }
      // 전화번호 형식 검증
      if (this.phoneNumber.length > 0 && this.phoneNumber.length != 13) {
        this.error.phoneNumber = "'-'를 제외한 휴대폰 번호를 입력해 주세요."
      } else {
        this.error.phoneNumber = false
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      })
      if (this.email === '' || this.studentId === '' || this.phoneNumber === '') {
        isSubmit = false;
      }
      this.isSubmit = isSubmit;
    },
    autoHypenPhone (str) {
      str = str.replace(/[^0-9]/g, '');
      var tmp = '';
      if (str.length < 4) {
        return str;
      } else if (str.length < 7) {
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3);
        return tmp;
      } else if(str.length < 11){
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 3);
        tmp += '-';
        tmp += str.substr(6);
        return tmp;
      } else{              
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 4);
        tmp += '-';
        tmp += str.substr(7);
        return tmp;
      }
    },
    async onFindPassword() {
      await this.$store.dispatch('account/onFindPassword', this.userData)
      const httpStatus = this.$store.state.account.httpStatus
      if (httpStatus !== null) {
        const alertInfo = {
          type: 'fail',
          message: '',
        }
        if (httpStatus === 200) {
          alertInfo.type = 'success'
          alertInfo.message = '회원정보 확인에 성공했습니다.'
        } else if (httpStatus === 400) {
          alertInfo.message = '잘못된 이메일, 학번 또는 전화번호입니다.'
        } else if (httpStatus === 500) {
          alertInfo.message = '서버 오류입니다.'
        } else {
          alertInfo.message = `${httpStatus} 오류입니다.`
        }
        this.$emit('alert', alertInfo)
      }
    }
  },
  computed: {
    userData() {
      return {
        email: this.email,
        userId: this.studentId,
        phoneNumber: this.phoneNumber
      }
    }
  },
  watch: {
    email: function() {
      this.checkForm();
    },
    studentId: function() {
      this.checkForm();
    },
    phoneNumber: function() {
      this.phoneNumber = this.autoHypenPhone(this.phoneNumber)
      this.checkForm();
    },
  },
  created() {
    this.SET_HTTP_STATUS(null)
  }
}
</script>

<style>

</style>