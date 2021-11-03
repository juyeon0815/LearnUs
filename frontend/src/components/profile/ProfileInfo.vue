<template>
  <div class="profile-info">
    <div class="title-button-box">
      <div class="title">
        <span>My Info</span>
      </div>
      <button 
        :class="[isSubmit ? 'change-info-btn' : 'change-btn-disabled']"
        @click="onChangeUserPhone">
        정보수정
      </button>
    </div>
    <div class="info-inputs">
      <!-- 닉네임 input -->
      <div class="info-input-box">
        <input 
          type="text"
          class="info-input margin-between"
          :value="$store.state.account.userInfo.nickname"
          disabled
        />
        <label>Nickname</label>
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
export default {
  name: 'ProfileInfo',
  data: () => {
    return {
      phoneNumber: '',
      originalNumber: '',
      error: {
        phoneNumber: false
      },
      isSubmit: false,
    }
  },
  methods: {
    // 형식 검증 method
    checkForm() {
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
      if (this.phoneNumber === '' || this.phoneNumber === this.originalNumber) {
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
    async onChangeUserPhone() {
      await this.$store.dispatch('account/onChangeUserPhone', this.phoneNumber)
      this.originalNumber = this.$store.state.account.userInfo.phone
      this.checkForm()
    }
  },
  watch: {
    phoneNumber: function() {
      this.phoneNumber = this.autoHypenPhone(this.phoneNumber)
      this.checkForm();
    }
  },
  created() {
    this.originalNumber = this.$store.state.account.userInfo.phone
    this.phoneNumber = this.originalNumber
  },
}
</script>
