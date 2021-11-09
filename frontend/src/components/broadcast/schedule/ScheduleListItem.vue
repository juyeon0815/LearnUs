<template>
  <div class="schedule-list-item">
    <div class="thumbnail-box">
      <img :src="thumbnailPath" alt="" class="thumbnail-image">
    </div>
    <div class="broadcast-info">
      <div class="info-first-box">
        <span class="info-title">{{ title }}</span>
        <span class="cancle-broadcast">방송 취소</span>
      </div>
      <div class="info-instructor">
        <span><strong>진행자</strong> | {{ instructor }}</span>
      </div>
      <div class="info-student">
        <span><strong>대상 교육생</strong> | {{ studentGroup }} Track</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ScheduleListItem',
  data() {
    return {
      thumbnailPath: require('@/assets/image/test/thumbnail.jpg'),
      title: '알고리즘 ① Sort(버블, 선택, 삽입)',
      instructor: '오창희',
      studentGroup: '5기 전공',
    }
  }
}
</script>
