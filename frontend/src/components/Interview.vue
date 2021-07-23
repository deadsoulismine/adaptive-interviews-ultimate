<template>
  <div className="container">
    <div className="card">
      <div className="card-header">
        <h4>Дата: {{ formatDate(interview.date) }} </h4>
        <h4>Сотрудник: {{ interview.employee.firstName }} {{ interview.employee.lastName }}</h4>
      </div>
      <div className="card-body">
        <form v-on:submit.prevent="updateInterview">
          <div className="form-group">
            <label>
              <textarea v-model="interview.description" className="form-control" cols="60"
                        placeholder="Напишите ваш отзыв"
                        rows="30"></textarea>
            </label>
          </div>
          <div className="form-group">
            <input className="btn btn-primary" type="submit" value="Ок"/>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import moment from 'moment';

export default {
  data() {
    return {
      interview: {}
    }
  },
  created: function () {
    this.getInterview();
  },
  methods: {
    getInterview() {
      axios.get('/api/interviews/find/' + this.$route.params.id)
          .then(response => {
            this.interview = response.data
          })
    },

    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    updateInterview() {
      let uri = '/api/interviews/update/' + this.$route.params.id;
      axios.put(uri, this.interview).then((response) => {
        this.$router.push({name: 'Interviews'});
        console.log(response)
      });
    }
  }
}
</script>
