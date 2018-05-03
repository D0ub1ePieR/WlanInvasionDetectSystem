import axios from 'axios';
let base = '';
export const getStatisticsInfo = params => { return axios.post("http://localhost:8080",  params)};
