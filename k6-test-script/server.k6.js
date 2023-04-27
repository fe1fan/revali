import http from 'k6/http';

export let options = {
    vus: 100,
    duration: '10s',
};
export default function () {
    let params = {headers: {'Content-Type': 'application/json'}};
    let res = http.get("http://127.0.0.1:8888/hello", params)
    console.log(res.body);
}