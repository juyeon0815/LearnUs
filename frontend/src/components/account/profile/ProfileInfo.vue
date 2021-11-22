<template>
  <div class="profile-info">
    <div class="title-button-box">
      <div class="title">
        <span>My Info</span>
      </div>
      <button 
        :class="[isSubmit ? 'active-btn' : 'disabled-btn', 'btn change-info-btn']"
        @click="onChangeUserInfo">
        정보 수정
      </button>
    </div>
    <div class="info-inputs">
      <!-- 닉네임 input -->
      <div class="info-input-box">
        <input 
          @input="insertNickname"
          type="text"
          class="info-input margin-between"
          :value="nickname"
          :disabled="!isAdmin"
          maxlength="15"
        />
        <label>Nickname</label>
        <div class="error-text" v-if="error.nickname">{{error.nickname}}</div>
      </div>
      <!-- 전화번호 input -->
      <div class="info-input-box">
        <input 
          type="text"
          class="info-input"
          v-model="phoneNumber"
          maxlength="13"
        />
        <label>Phone Number</label>
        <div class="error-text" v-if="error.phoneNumber">{{error.phoneNumber}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'ProfileInfo',
  data: () => {
    return {
      phoneNumber: '',
      originalNumber: '',
      nickname: '',
      originalNickname: '',
      error: {
        nickname: false,
        phoneNumber: false,
      },
      isSubmit: false,
    }
  },
  methods: {
    // 형식 검증 method
    insertNickname (event) {
      this.nickname = event.target.value
    },
    checkForm() {
      // 닉네임 검증
      if (this.nickname.length < 3) {
        this.error.nickname = '3글자 이상의 닉네임을 설정해주세요.'
      } else {
        this.error.nickname = false
      }
      // 전화번호 형식 검증
      if (this.phoneNumber.length >= 0 && this.phoneNumber.length != 13) {
        this.error.phoneNumber = "'-'를 제외한 휴대폰 번호를 입력해 주세요."
      } else {
        this.error.phoneNumber = false
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      })
      if ((this.phoneNumber === '' || this.phoneNumber === this.originalNumber) &&
      (this.nickname === '' || this.nickname === this.originalNickname)) {
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
    // 휴대전화 정보 수정
    async onChangeUserInfo() {
      const userInfo = {
        phone: this.phoneNumber,
        nickname: this.nickname,
      }
      await this.$store.dispatch('account/onChangeUserInfo', userInfo)
      this.originalNumber = this.$store.state.account.userInfo.phone
      this.originalNickname = this.$store.state.account.userInfo.nickname
      this.checkForm()
      const httpStatus = this.$store.state.account.httpStatus
      if (httpStatus !== null) {
        const alertInfo = {
          type: 'fail',
          message: '',
        }
        if (httpStatus === 200) {
          alertInfo.type = 'success'
          alertInfo.message = '회원 정보가 변경되었습니다.'
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
    ...mapGetters('account', ['isAdmin'])
  },
  watch: {
    phoneNumber: function() {
      this.phoneNumber = this.autoHypenPhone(this.phoneNumber)
      this.checkForm();
    },
    nickname: function() {
      this.checkForm();
    }
  },
  created() {
    this.originalNumber = this.$store.state.account.userInfo.phone
    this.phoneNumber = this.originalNumber
    this.originalNickname = this.$store.state.account.userInfo.nickname
    this.nickname = this.originalNickname
  },
}
</script>
