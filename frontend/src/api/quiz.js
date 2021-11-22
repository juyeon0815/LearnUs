import _axios from "./interceptor"

export default {
  getQuizList (id) {
    return _axios({
      url: `quiz/all/${id}`,
      method: 'get'
    })
  },
  addQuiz (data) {
    return _axios({
      url: 'quiz',
      method: 'post',
      data: data
    })
  },
  editQuiz (data) {
    return _axios({
      url: 'quiz',
      method: 'patch',
      data: data
    })
  },
  deleteQuiz (id) {
    return _axios({
      url: `quiz/${id}`,
      method: 'delete'
    })
  },
  getQuizResult (id) {
    return _axios({
      url: `quiz/all/after/${id}`,
      method: 'get'
    })
  }
}